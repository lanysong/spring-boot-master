package com.vanc.spring.boot.learning;

import java.util.concurrent.TimeUnit;

public class ShutdownHookDemo {
    public static void main(String[] args) {
       /* Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            while (true) {
                System.out.println("closing...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
            }
        }));*/

        System.out.println("before closing...");

        try {
            System.exit(1);
        } finally {
            System.out.println("finally");
        }

        System.out.println("after closing...");

    }

}
