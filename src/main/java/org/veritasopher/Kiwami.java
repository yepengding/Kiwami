package org.veritasopher;

import org.veritasopher.exception.GlobalExceptionHandler;

/**
 * Entry Point
 *
 * @author Yepeng Ding
 */
public class Kiwami {
    public static void main(String[] args) {
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(globalExceptionHandler);

        System.out.println("Hello Kiwami!");
    }
}
