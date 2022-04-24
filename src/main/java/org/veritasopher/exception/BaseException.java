package org.veritasopher.exception;

/**
 * Base Exception
 *
 * @author Yepeng Ding
 */
public abstract class BaseException extends RuntimeException {

    public BaseException(String message) {
        super(message);
        System.err.println(this.getMessage());
    }

}
