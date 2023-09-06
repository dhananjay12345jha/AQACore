package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown on driver instantiation
 */
public class CoreDriverException extends TimeoutException {

    /**
     * Exception
     *
     * @param error to be throw
     */
    public CoreDriverException(final String error) {
        super(error);
    }

    /**
     * Exception
     *
     * @param error to be throw
     * @param cause {@link Throwable} cause
     */
    public CoreDriverException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
