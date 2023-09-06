package com.automation.core.interfaces.impl;

import com.automation.core.base.ExtWebElementImpl;
import com.automation.core.interfaces.Button;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElementImpl
 * @see Button
 */
public class ButtonImpl extends ExtWebElementImpl implements Button {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  webdriver
     * @param element Webelement to wrap up
     * @param by      By locator
     * @param locator element locator
     */
    public ButtonImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        super(driver, element, by, locator);
    }

    @Override
    public void click() {
        super.scrollIntoView();
        super.jsClick();
    }

    @Override
    public void jsClick() {
        super.scrollIntoView();
        super.jsClick();
    }
}
