package com.vanc.javabasic.design.proxy.cglib;

public class CglibTarget {
    public String execute() {
        String message = "-----------test cglib------------";
        System.out.println(message);
        return message;
    }
}
