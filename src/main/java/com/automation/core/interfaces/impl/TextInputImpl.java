package com.automation.core.interfaces.impl;

import com.automation.core.base.ExtWebElementImpl;
import com.automation.core.driver.CoreIOSDriver;
import com.automation.core.interfaces.TextInput;
import com.automation.core.utilities.Sleeper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElementImpl
 * @see TextInput
 */
public class TextInputImpl extends ExtWebElementImpl implements TextInput {

    WebElement element;
    WebDriver driver;

    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  webdriver
     * @param element Webelement to wrap up
     * @param by      By locator
     * @param locator element locator
     */
    public TextInputImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        super(driver, element, by, locator);
        this.element = element;
        this.driver = driver;
    }

    @Override
    public void set(final String text) {
        super.sendKeys(text);
        if (driver instanceof CoreIOSDriver)
            super.refresh();
    }

    @Override
    public void setKeybyKey(final String textToSet) {
        Arrays.stream(textToSet.split("")).forEach(str -> {
            super.sendKeys(str);
            Sleeper.sleep(200);
        });
        if (driver instanceof CoreIOSDriver) {
            super.refresh();
            Sleeper.sleep(3000);
            if (!super.getValue().equalsIgnoreCase(textToSet)) {
                super.clear();
                Sleeper.sleep(1000);
                super.sendKeys(textToSet);
                Sleeper.sleep(1000);
                super.refresh();
            }
        }
    }

    @Override
    public void setandRefresh(final String textToSet) {
        final String[] text = textToSet.split("");
        final int waitLength = text.length - 3;
        IntStream.range(0, text.length).forEach(index -> {
            super.sendKeys(text[index]);
            if (index >= waitLength) {
                Sleeper.sleep(200);
            }
            if (driver instanceof CoreIOSDriver)
                super.refresh();
        });
    }

    @Override
    public void setKeybyKey(final long timegap, final String textToSet) {
        Arrays.stream(textToSet.split("")).forEach(str -> {
            super.sendKeys(str);
            Sleeper.sleep(timegap);
            if (driver instanceof CoreIOSDriver)
                super.refresh();
        });
    }

    @Override
    public void scrollAndSet(final String text) {
        super.scrollIntoView();
        super.sendKeys(text);
        super.refresh();
    }
}
