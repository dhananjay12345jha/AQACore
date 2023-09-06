package com.automation.core.factory;

import com.automation.core.base.ExtWebElement;
import com.automation.core.exceptions.CoreException;
import com.automation.core.exceptions.ElementNotPresentException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;

import static com.automation.core.factory.ImplementedByProcessor.getWrapperClass;

/**
 * Replaces DefaultLocatingElementHandler. Simply opens it up to descendants of the WebElement interface, and other
 * mix-ins of WebElement and Locatable, etc. Saves the wrapping type for calling the constructor of the wrapped classes.
 */
public class ElementHandler implements InvocationHandler {
    private final ElementLocator locator;
    private final Class<?> wrappingType;
    private WebDriver driver;

    /**
     * Generates a handler to retrieve the WebElement from a locator for a given WebElement interface descendant.
     *
     * @param interfaceType Interface wrapping this class. It contains a reference the the implementation.
     * @param locator       Element locator that finds the element on a page.
     * @param <T>           type of the interface
     */
    public <T> ElementHandler(final Class<T> interfaceType, final ElementLocator locator) {
        this.locator = locator;
        if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }

        wrappingType = getWrapperClass(interfaceType);
    }

    public <T> ElementHandler(final Class<T> interfaceType, final ElementLocator locator, final WebDriver driver) {
        this.locator = locator;
        this.driver = driver;
        if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to Element.");
        }

        wrappingType = getWrapperClass(interfaceType);
    }

    @Override
    public Object invoke(final Object object, final Method method, final Object[] objects) throws Throwable {

        final By by;
        final Field elementField;
        try {
            elementField = locator.getClass().getDeclaredField("by");
            elementField.setAccessible(true);
            by = (By) elementField.get(locator);
        } catch (final Exception e) {
            throw new CoreException("Failed to obtain element locator - " + locator.toString());
        }

        final WebElement element;
        try {
            element = locator.findElement();
        } catch (final NoSuchElementException e) {
            if ("toString".equals(method.getName())) {
                return "Proxy element for: " + locator;
            }
            throw new ElementNotPresentException("Element not found - " + locator);
        }

        if ("getWrappedElement".equals(method.getName())) {
            return element;
        }
        final Constructor<?> cons = wrappingType.getConstructor(WebDriver.class, WebElement.class, By.class, ElementLocator.class);

        try {
            final Object thing = cons.newInstance(driver, element, by, locator);
            return method.invoke(wrappingType.cast(thing), objects);
        } catch (final InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }
}