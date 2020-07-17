package com.vanc.spring.boot.learning;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeoutCircuitBreaker{
	long timeout();
}
