package com.automation.core.interfaces.impl;

import com.automation.core.base.ExtWebElementImpl;
import com.automation.core.interfaces.CheckBox;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElementImpl
 * @see CheckBox
 */
public class CheckBoxImpl extends ExtWebElementImpl implements CheckBox {
    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  webdriver
     * @param element Webelement to wrap up
     * @param by      By locator
     * @param locator element locator
     */
    public CheckBoxImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        super(driver, element, by, locator);
    }

    @Override
    public void toggle() {
        super.click();
    }

    @Override
    public void jsToggle() {
        super.jsClick();
    }

    @Override
    public void check() {
        if (!isChecked())
            toggle();
    }

    @Override
    public void uncheck() {
        if (isChecked())
            toggle();
    }

    @Override
    public boolean isChecked() {
        return super.isSelected();
    }
}
