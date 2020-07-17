package com.vanc.spring.boot.learning.controller;

import com.vanc.spring.boot.learning.TimeoutCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-17 10:37
 */
@RestController
@RequestMapping
public class DemoController {
	private final ExecutorService executorService = Executors.newCachedThreadPool();
	private final Random random = new Random();
	
	
	@GetMapping(value = "/createOrder3")
	public String createOrder3(String message) throws Exception {
		Future<String> future = executorService.submit(() -> {
			//如果随机时间大于100，那么出发容错
			int value = random.nextInt(200);
			System.out.println("createOrder2 cost" + value + "ms");
			
			Thread.sleep(value);
			String returnValue = "createOrder2:" + message;
			return returnValue;
		});
		//future.get 方法，强制执行，如果超过100毫秒，就报TimeoutException
		String retureValue = "";
		try {
			//容错，如果超时异常，执行planB
			retureValue = future.get(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			retureValue = errorContent(message);
		}
		return retureValue;
	}
	
	//planB，降级方法
	public String errorContent(String message) {
		return "Falut";
	}
	
	@GetMapping(value = "/createOrder4")
	public String createOrder4(String message) throws Exception {
		Future<String> future = executorService.submit(() -> {
			//如果随机时间大于100，那么出发容错
			int value = random.nextInt(200);
			System.out.println("createOrder2 cost" + value + "ms");
			
			Thread.sleep(value);
			String returnValue = "createOrder2:" + message;
			return returnValue;
		});
		return future.get(100, TimeUnit.MILLISECONDS);
	}
	
	@TimeoutCircuitBreaker(timeout = 100)
	@GetMapping(value = "/createOrder6")
	public String createOrder6(String message) throws Exception {
		//如果随机时间大于100，那么出发容错
		int value = random.nextInt(200);
		System.out.println("createOrder2 cost" + value + "ms");
		
		Thread.sleep(value);
		String returnValue = "createOrder2:" + message;
		return returnValue;
	}
}
