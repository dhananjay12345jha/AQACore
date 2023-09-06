package com.automation.core.interfaces.impl;

import com.automation.core.base.ExtWebElementImpl;
import com.automation.core.interfaces.Radio;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElementImpl
 * @see Radio
 */
public class RadioImpl extends ExtWebElementImpl implements Radio {

    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  webdriver
     * @param element Webelement to wrap up
     * @param by      By locator
     * @param locator element locator
     */
    public RadioImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        super(driver, element, by, locator);
    }


    @Override
    public void select() {
        super.click();
    }

    @Override
    public void jsSelect() {
        super.jsClick();
    }

}
