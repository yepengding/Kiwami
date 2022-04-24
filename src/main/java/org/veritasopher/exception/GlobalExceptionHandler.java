package org.veritasopher.exception;

/**
 * Global Exception Handler
 *
 * @author Yepeng Ding
 */
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println("Unhandled exception.");
        System.exit(1);
    }
}
