package com.automation.core.exceptions;

import org.openqa.selenium.TimeoutException;

/**
 * Exception to be thrown when {@link org.openqa.selenium.WebElement} is Not Present
 */
public class ElementNotPresentException extends TimeoutException {

    public ElementNotPresentException(final String error) {
        super(error);
    }

    public ElementNotPresentException(final String error, final Throwable cause) {
        super(error, cause);
    }


}
