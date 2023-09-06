package com.automation.core.interfaces.impl;

import com.automation.core.base.ExtWebElement;
import com.automation.core.base.ExtWebElementImpl;
import com.automation.core.interfaces.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.stream.Collectors;

public class SelectImpl extends ExtWebElementImpl implements Select {

    private final org.openqa.selenium.support.ui.Select select;
    private final WebDriver driver;
    private final ElementLocator locator;
    private final By by;

    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  webdriver
     * @param element Webelement to wrap up
     * @param by      By locator
     * @param locator element locator
     */
    public SelectImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        super(driver, element, by, locator);
        this.driver = driver;
        this.by = by;
        this.locator = locator;
        select = new org.openqa.selenium.support.ui.Select(element);
    }

    @Override
    public boolean isMultiple() {
        return select.isMultiple();
    }

    @Override
    public void deselectByIndex(final int index) {
        select.deselectByIndex(index);
    }

    @Override
    public void selectByValue(final String value) {
        select.selectByValue(value);
    }

    @Override
    public ExtWebElement getFirstSelectedOption() {
        return new ExtWebElementImpl(driver, select.getFirstSelectedOption(), by, locator);
    }

    @Override
    public void selectByVisibleText(final String text) {
        select.selectByVisibleText(text);
    }

    @Override
    public void deselectByValue(final String value) {
        select.deselectByValue(value);
    }

    @Override
    public void deselectAll() {
        select.deselectAll();
    }

    @Override
    public List<ExtWebElement> getAllSelectedOptions() {
        return select.getAllSelectedOptions().stream()
                .map(ele -> new ExtWebElementImpl(driver, ele, by, locator))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExtWebElement> getOptions() {
        return select.getOptions().stream()
                .map(ele -> new ExtWebElementImpl(driver, ele, by, locator))
                .collect(Collectors.toList());
    }

    @Override
    public void deselectByVisibleText(final String text) {
        select.deselectByVisibleText(text);
    }

    @Override
    public void selectByIndex(final int index) {
        select.selectByIndex(index);
    }
}
