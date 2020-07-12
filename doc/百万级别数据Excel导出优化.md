# 百万级别数据easyExcel导出优化

## 前提
笔者负责维护的一个数据查询和数据导出服务是一个相对远古的单点应用，在上一次云迁移之后扩展为双节点部署，但是发现了服务经常因为大数据量的数据导出频繁Full GC，导致应用假死无法响应外部的请求。因为某些原因，该服务只能够「分配2GB的最大堆内存」，下面的优化都是以这个堆内存极限为前提。通过查看服务配置、日志和APM定位到两个问题：

- 启动脚本中添加了CMS参数，采用了CMS收集器，该收集算法对内存的敏感度比较高，大批量数据导出容易瞬间打满老年代导致Full GC频繁发生。
- 数据导出的时候采用了一次性把目标数据全部查询出来再写到流中的方式，大量被查询的对象驻留在堆内存中，直接打满整个堆。

对于问题1咨询过身边的大牛朋友，直接把所有CMS相关的所有参数去掉，由于生产环境使用了**JDK1.8**，相当于直接使用默认的GC收集器参数-XX:+UseParallelGC，也就是Parallel Scavenge + Parallel Old的组合然后重启服务。观察APM工具发现Full GC的频率是有所下降，但是一旦某个时刻导出的数据量十分巨大（例如查询的结果超过一百万个对象，超越可用的最大堆内存），还是会陷入无尽的Full GC，也就是修改了JVM参数只起到了治标不治本的作用。所以下文会针对这个问题（也就是问题2），通过一个仿真案例来分析一下如何进行优化。

## 基本原理
使用Java（或者说依赖于JVM的语言）开发数据导出的模块，下面的伪代码是通用的：
```java
数据导出方法(参数,输出流[OutputStream]){
    1. 通过参数查询需要导出的结果集
    2. 把结果集序列化为字节序列
    3. 通过输出流写入结果集字节序列
    4. 关闭输出流
}
```
下面举个栗子
```java
@Data
public static class Parameter{
    
    private OffsetDateTime paymentDateTimeStart;
    
    private OffsetDateTime paymentDateTimeEnd;
}

public void export(Parameter parameter, OutputStream os) throws IOException {
    List<OrderDTO> result = 
            orderDao.query(parameter.getPaymentDateTimeStart(), parameter.getPaymentDateTimeEnd()).stream()
                    .map(order -> {
                        OrderDTO dto = new OrderDTO();
                            ......
                        return dto;
                    }).collect(Collectors.toList());
    byte[] bytes = toBytes(result);
    os.write(bytes);
    os.close();
}
```

针对不同的OutputStream实现，最终可以把数据导出到不同类型的目标中，例如对于FileOutputStream而言相当于把数据导出到文件中，而对于SocketOutputStream而言相当于把数据导出到网络流中（客户端可以读取该流实现文件下载）。目前B端应用比较常见的文件导出都是使用后一种实现，基本的交互流程如下:
![](https://imgkr.cn-bj.ufileos.com/c130affd-1496-4c3c-b497-98bb3eb0de5f.png)

为了节省服务器的内存，这里的返回数据和数据传输部分可以设计为分段处理，也就是查询的时候考虑把查询全量的结果这个思路改变为每次只查询部分数据，直到得到全量的数据，每批次查询的结果数据都写进去OutputStream中。

这里以MySQL为例，可以使用类似于分页查询的思路，但是鉴于LIMIT offset,size的效率太低，结合之前的一些实践，采用了一种「改良的"滚动翻页"的实现方式」（这个方式是前公司的某个架构小组给出来的思路，后面广泛应用于各种批量查询、数据同步、数据导出以及数据迁移等等场景，这个思路肯定不是首创的，但是实用性十分高），注意这个方案要求表中包含一个有自增趋势的主键，单条查询SQL如下:
```mysql
SELECT * FROM tableX WHERE id > #{lastBatchMaxId} [其他条件] ORDER BY id [ASC|DESC](这里一般选用ASC排序) LIMIT ${size};
```
把上面的SQL放进去前一个例子中，并且假设订单表使用了自增长整型主键id，那么上面的代码改造如下：
```java
public void export(Parameter parameter, OutputStream os) throws IOException {
    long lastBatchMaxId = 0L;
    for (;;){
        List<Order> orders =  orderDao.query([SELECT * FROM t_order WHERE id > #{lastBatchMaxId} 
        AND payment_time >= #{parameter.paymentDateTimeStart} AND payment_time <= #{parameter.paymentDateTimeEnd} ORDER BY id ASC LIMIT ${LIMIT}]);
        if (orders.isEmpty()){
            break;
        }
        List<OrderDTO> result =
                orderDao.query([SELECT * FROM t_order]).stream()
                .map(order -> {
                    OrderDTO dto = new OrderDTO();
                    ......
                    return dto;
                }).collect(Collectors.toList());
        byte[] bytes = toBytes(result);
        os.write(bytes);
        os.flush();
        lastBatchMaxId = orders.stream().map(Order::getId).max(Long::compareTo).orElse(Long.MAX_VALUE);
    }
    os.close();
}
```
**「上面这个示例就是百万级别数据Excel导出优化的核心思路」** 查询和写入输出流的逻辑编写在一个死循环中，因为查询结果是使用了自增主键排序的，而属性lastBatchMaxId则存放了本次查询结果集中的最大id，同时它也是下一批查询的起始id，这样相当于基于id和查询条件向前滚动，直到查询条件不命中任何记录返回了空列表就会退出死循环。而limit字段则用于控制每批查询的记录数，可以按照应用实际分配的内存和每批次查询的数据量考量设计一个合理的值，这样就能让单个请求下常驻内存的对象数量控制在limit个从而使应用的内存使用更加可控，避免因为并发导出导致堆内存瞬间被打满。

> 这里的滚动翻页方案远比LIMIT offset,size效率高，因为此方案每次查询都是最终的结果集，而一般的分页方案使用的LIMIT offset,size需要先查询，后截断。

## 小结
这篇文章详细地分析大数据量导出的性能优化，最要侧重于内存优化。该方案实现了在尽可能少占用内存的前提下，在效率可以接受的范围内进行大批量的数据导出。这是一个可复用的方案，类似的设计思路也可以应用于其他领域或者场景，不局限于数据导出。
