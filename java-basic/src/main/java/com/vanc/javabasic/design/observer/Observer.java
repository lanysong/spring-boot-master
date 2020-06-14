package com.vanc.javabasic.design.observer;

public abstract class Observer {
    protected Subject subject;

    /**
     * 更新状态
     */
    public abstract void update();


    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}

