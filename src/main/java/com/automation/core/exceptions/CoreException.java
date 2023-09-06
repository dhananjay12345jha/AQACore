package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown on common exceptions
 */
public class CoreException extends TimeoutException {

    /**
     * Exception
     *
     * @param error to be throw
     */
    public CoreException(final String error) {
        super(error);
    }

    /**
     * Exception
     *
     * @param error to be throw
     * @param cause {@link Throwable} cause
     */
    public CoreException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
