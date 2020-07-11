package com.vanc.javabasic.design.strategy;

import org.springframework.stereotype.Service;

/**
 * 具体的业务逻辑
 * @author : vanc
 * @date: 2020-07-08 16:37
 */
@Service
public class BizUnitService {
	public String bizOne(String order) {
		return order + "各种花式操作1";
	}
	public String bizTwo(String order) {
		return order + "各种花式操作2";
	}
	public String bizThree(String order) {
		return order + "各种花式操作3";
	}
}
