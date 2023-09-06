package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown when {@link org.openqa.selenium.WebElement} is Not Enabled
 */
public class ElementNotEnabledException extends TimeoutException {

    /**
     * Exception
     *
     * @param error to be throw
     */
    public ElementNotEnabledException(final String error) {
        super(error);
    }

    /**
     * Exception
     *
     * @param error to be throw
     * @param cause {@link Throwable} cause
     */
    public ElementNotEnabledException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
