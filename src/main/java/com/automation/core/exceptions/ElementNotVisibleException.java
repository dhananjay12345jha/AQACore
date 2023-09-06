package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown when {@link org.openqa.selenium.WebElement} is Not Present
 */
public class ElementNotVisibleException extends TimeoutException {

    public ElementNotVisibleException(final String error) {
        super(error);
    }


    public ElementNotVisibleException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
