package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown when {@link org.openqa.selenium.WebElement} is Not Present
 */
public class ElementVisibleException extends TimeoutException {

    public ElementVisibleException(final String error) {
        super(error);
    }

    public ElementVisibleException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
