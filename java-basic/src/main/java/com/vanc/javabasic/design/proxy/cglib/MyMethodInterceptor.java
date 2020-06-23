package com.vanc.javabasic.design.proxy.cglib;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println(">>>>MethodInterceptor start...");
        Object result = methodProxy.invokeSuper(object, args);
        System.out.println(">>>>MethodInterceptor ending...");
        return result;
    }
}
