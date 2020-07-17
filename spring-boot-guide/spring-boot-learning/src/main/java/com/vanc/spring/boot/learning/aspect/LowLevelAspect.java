package com.vanc.spring.boot.learning.aspect;

import com.vanc.spring.boot.learning.TimeoutCircuitBreaker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.concurrent.*;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-17 10:55
 */
@Aspect
public class LowLevelAspect {
	private final ExecutorService executorService = Executors.newCachedThreadPool();
	//切超时注解
	/**
	 * 切注解--超时
	 * @param point
	 * @param message
	 * @param cricuitBreaker
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(* com.vanc.spring.boot.learning.controller.DemoController.createOrder6(..)) && args(message) " +
			"&& @annotation" +
			"(cricuitBreaker)")
	public Object CreateOrder6Timeout(ProceedingJoinPoint point, String message, TimeoutCircuitBreaker cricuitBreaker) throws Throwable{
		long timeout = cricuitBreaker.timeout();
		return doInvoke(point,message,timeout);
	}
	
	private Object doInvoke(ProceedingJoinPoint point, String message, long timeout) throws Throwable{
		Future<Object> future = executorService.submit(() -> {
			Object returnValue = null;
			try {
				returnValue = point.proceed(new Object[]{message});
			} catch (Throwable throwable) {
			}
			return returnValue;
		});
		Object returnValue = null;
		//future.get 方法，强制执行，如果超过100毫秒，就报TimeoutException
		try {
			returnValue = future.get(100, TimeUnit.MILLISECONDS);
		} catch (TimeoutException e) {
			future.cancel(true);
			returnValue = errorContent("");
		}
		return returnValue;
	}
	
	private String errorContent(String s) {
		return "fail";
	}
}
