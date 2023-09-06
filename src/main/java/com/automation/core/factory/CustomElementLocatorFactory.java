package com.automation.core.factory;

import com.automation.core.exceptions.CoreException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import java.lang.reflect.Field;

/**
 * Custom Element Locator Factory
 *
 * @see ElementLocatorFactory
 */
public class CustomElementLocatorFactory implements ElementLocatorFactory {
    private final WebDriver driver;

    /**
     * Constructor
     *
     * @param driver {@link WebDriver}
     */
    public CustomElementLocatorFactory(final WebDriver driver) {
        if (driver == null)
            throw new CoreException("WebDriver instance is null");
        this.driver = driver;
    }

    @Override
    public ElementLocator createLocator(final Field field) {
        return new DefaultElementLocator(driver, field);
    }
}