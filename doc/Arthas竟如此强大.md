# Arthas竟如此强大？

## 背景
上周生产出了个内存溢出的bug，我们一般的思路嘛就是
- 下载堆栈日志
- MAT工具分析定位到具体的代码


因为这个类的方法是基础类，挺多业务类都有调用，最终的原因是
1. 业务类死循环，疯狂调用，但是业务还执行报错了
2. 基础类没有移除任务，这是个无界队列，导致队列过长内存溢出。


官方文档是这么介绍的：
Arthas 是Alibaba开源的Java诊断工具，深受开发者喜爱。
当你遇到以下类似问题而束手无策时，Arthas可以帮助你解决：

- 这个类从哪个 jar 包加载的？为什么会报各种类相关的 Exception？
- 我我改的代码为什么没有执行到？难道是我没 commit？分支搞错了？
- 我遇到问题无法在线上 debug，难道只能通过加日志再重新发布吗？
- 线上遇到某个用户的数据处理有问题，但线上同样无法 debug，线下无法重现！
- 是否有一个全局视角来查看系统的运行状况？
- 啊有什么办法可以监控到JVM的实时运行状态？
- 是怎么快速定位应用的热点，生成火焰图？

Arthas支持JDK 6+，支持Linux/Mac/Windows，采用命令行交互模式，同时提供丰富的 Tab 自动补全功能，进一步方便进行问题的定位和诊断。

## 常用命令
**对类进行反编译,定位运行中的代码是怎样的**
```linux
jad 具体类全限类名
```

**查看x方法对应的堆栈信息**

```linux
stack xxx.xxxx.X类 x方法
```


重点的两个命令，可以看线上环境具体入参，出参

**你能方便的观察到指定方法的调用情况。能观察到的范围为：返回值、抛出异常、入参，通过编写 OGNL 表达式进行对应变量的查看。**

```linux
观察方法出参和返回值
$ watch demo.MathGame primeFactors "{params,returnObj}" -x 2
Press Ctrl+C to abort.
Affect(class-cnt:1 , method-cnt:1) cost in 44 ms.
ts=2018-12-03 19:16:51; [cost=1.280502ms] result=@ArrayList[
    @Object[][
        @Integer[535629513],
    ],
    @ArrayList[
        @Integer[3],
        @Integer[19],
        @Integer[191],
        @Integer[49199],
    ],
]
```


```linux
观察方法入参
$ watch demo.MathGame primeFactors "{params,returnObj}" -x 2 -b
Press Ctrl+C to abort.
Affect(class-cnt:1 , method-cnt:1) cost in 50 ms.
ts=2018-12-03 19:23:23; [cost=0.0353ms] result=@ArrayList[
    @Object[][
        @Integer[-1077465243],
    ],
    null,
]
```

更多可以看

[官方文档]: https://alibaba.github.io/arthas/watch



**监控某个特殊方法的调用统计数据，包括总调用次数，平均rt，成功率等信息，每隔5秒输出一次。**

```linux
monitor -c 5 xxx.xxxx.X类 x方法
```

## 小结
最后定位到接口调用失败X方法没有移除任务队列导致无界队列一直增加，内存溢出。



**从官方找到的一个知识图谱**

![img](https://user-images.githubusercontent.com/1683936/71873638-a697b800-315a-11ea-9862-fbfa8e470064.png)


