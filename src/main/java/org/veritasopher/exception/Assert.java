package org.veritasopher.exception;

import javax.annotation.Nullable;

/**
 * Assertion Util
 *
 * @author Yepeng Ding
 */
public class Assert {

    /**
     * Assert an object is null
     *
     * @param object    object
     * @param exception exception
     */
    public static void isNull(@Nullable Object object, BaseException exception) {
        if (object != null) {
            throw exception;
        }
    }

    /**
     * Assert an object is not null
     *
     * @param object    object
     * @param exception exception
     */
    public static void notNull(@Nullable Object object, BaseException exception) {
        if (object == null) {
            throw exception;
        }
    }

    /**
     * Assert an expression is true
     *
     * @param expression expression
     * @param exception  exception
     */
    public static void isTrue(boolean expression, BaseException exception) {
        if (!expression) {
            throw exception;
        }
    }

}
