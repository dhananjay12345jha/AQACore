package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown when {@link org.openqa.selenium.WebElement} is Not Present
 */
public class ElementNotSelectedException extends TimeoutException {

    /**
     * Exception
     *
     * @param error to be throw
     */
    public ElementNotSelectedException(final String error) {
        super(error);
    }

    /**
     * Exception
     *
     * @param error to be throw
     * @param cause {@link Throwable} cause
     */
    public ElementNotSelectedException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
