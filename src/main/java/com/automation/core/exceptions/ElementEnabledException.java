package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown when {@link org.openqa.selenium.WebElement} is Enabled
 */
public class ElementEnabledException extends TimeoutException {

    /**
     * Exception
     *
     * @param error to be thrown
     */
    public ElementEnabledException(final String error) {
        super(error);
    }

    /**
     * Exception
     *
     * @param error to be throw
     * @param cause {@link Throwable} cause
     */
    public ElementEnabledException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
