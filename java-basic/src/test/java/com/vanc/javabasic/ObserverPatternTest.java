package com.vanc.javabasic;

import com.vanc.javabasic.design.observer.BinaryObserver;
import com.vanc.javabasic.design.observer.OctalObserver;
import com.vanc.javabasic.design.observer.Subject;

public class ObserverPatternTest {

    public static void main(String[] args) {
        Subject subject = new Subject();

        new BinaryObserver(subject);
        new OctalObserver(subject);

        System.out.println("First state change: 15");
        subject.setState(15);
        subject.notifyAllObservers();
        System.out.println();

        System.out.println("Second state change: 10");
        subject.setState(10);
        subject.notifyAllObservers();
    }

}

