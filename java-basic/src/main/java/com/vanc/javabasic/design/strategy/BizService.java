package com.vanc.javabasic.design.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 策略模式的 lambda逻辑分派
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-08 16:37
 */
@Service
public class BizService {
	@Autowired
	private BizUnitService bizUnitService;
	
	private Map<String, Function<String, String>> checkResultDispatcherComX = new HashMap<>();
	
	/**
	 * 初始化 业务逻辑分派Map 其中value 存放的是 lambda表达式
	 */
	@PostConstruct
	public void checkResultDispatcherComXInit() {
		checkResultDispatcherComX.put("key_订单1", order -> bizUnitService.bizOne(order));
		checkResultDispatcherComX.put("key_订单1_订单2", order -> bizUnitService.bizTwo(order));
		checkResultDispatcherComX.put("key_订单1_订单2_订单3", order -> bizUnitService.bizThree(order));
	}
	
	/**
	 * 具体的业务调用
	 */
	public String getCheckResultComX(String order, int level) {
		//写一段生成key的逻辑：
		String ley = getDispatcherComXKey(order, level);
		
		Function<String, String> result = checkResultDispatcherComX.get(ley);
		if (result != null) {
			//执行这段表达式获得String类型的结果
			return result.apply(order);
		}
		return "不在处理的逻辑中返回业务错误";
	}
	
	/**
	 * 判断条件方法
	 */
	private String getDispatcherComXKey(String order, int level) {
		StringBuilder key = new StringBuilder("key");
		for (int i = 1; i <= level; i++) {
			key.append("_").append(order).append(i);
		}
		return key.toString();
	}
}
