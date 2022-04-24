package org.veritasopher.exception;

/**
 * System Exception
 *
 * @author Yepeng Ding
 */
public class SystemException extends RuntimeException {

    public SystemException(String message) {
        super(message);
        System.err.println(this.getMessage());
    }
}
