package com.vanc.javabasic;

import com.vanc.javabasic.proxy.cglib.CglibTarget;
import com.vanc.javabasic.proxy.cglib.MyMethodInterceptor;
import org.springframework.cglib.proxy.Enhancer;

class ProxyTest {
    public static void main(String[] args) {
        System.out.println("***************");
        ProxyTest test = new ProxyTest();
        CglibTarget proxyTarget = (CglibTarget) test.createProxy(CglibTarget.class);
        String res = proxyTarget.execute();
        System.out.println(res);

    }

    /**
     * 代理对象的生成过程由Enhancer类实现，大概步骤如下：
     *
     * 生成代理类Class的二进制字节码；
     *
     * 通过Class.forName加载二进制字节码，生成Class对象；
     *
     * 通过反射机制获取实例构造，并初始化代理类对象。
     * @param targetClass
     * @return
     */
    public Object createProxy(Class targetClass) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new MyMethodInterceptor());
        return enhancer.create();
    }

}
