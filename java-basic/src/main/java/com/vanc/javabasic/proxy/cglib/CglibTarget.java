package com.vanc.javabasic.proxy.cglib;

public class CglibTarget {
    public String execute() {
        String message = "-----------test cglib------------";
        System.out.println(message);
        return message;
    }
}
