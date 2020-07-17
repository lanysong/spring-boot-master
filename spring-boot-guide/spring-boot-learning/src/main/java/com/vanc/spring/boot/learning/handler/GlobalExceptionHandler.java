package com.vanc.spring.boot.learning.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.TimeoutException;

/**
 * @author : vanc.song@wetax.com.cn
 * @date: 2020-07-17 10:46
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	public void onTimeoutException(TimeoutException timeoutException, Writer writer) throws IOException {
		writer.write(errorContent(""));
	}
	//planB，降级方法
	public String errorContent(String message){
		return "Fault";
	}
}
