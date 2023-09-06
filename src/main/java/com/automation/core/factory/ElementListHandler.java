package com.automation.core.factory;

import com.automation.core.base.ExtWebElement;
import com.automation.core.exceptions.CoreException;
import com.automation.core.exceptions.ElementNotPresentException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Wraps a list of WebElements in multiple wrapped elements.
 */
public class ElementListHandler implements InvocationHandler {

    private final ElementLocator locator;
    private final Class<?> wrappingType;
    private WebDriver driver;

    /**
     * Given an interface and a locator, apply a wrapper over a list of elements.
     *
     * @param interfaceType interface type we're trying to wrap around the element.
     * @param locator       locator on the page for the elements.
     * @param <T>           type of the interface.
     */
    public <T> ElementListHandler(final Class<T> interfaceType, final ElementLocator locator) {
        this.locator = locator;
        if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to ExtWebElement.");
        }
        wrappingType = ImplementedByProcessor.getWrapperClass(interfaceType);

    }

    public <T> ElementListHandler(final Class<T> interfaceType, final ElementLocator locator, final WebDriver driver) {
        this.locator = locator;
        this.driver = driver;
        if (!ExtWebElement.class.isAssignableFrom(interfaceType)) {
            throw new RuntimeException("interface not assignable to ExtWebElement.");
        }
        wrappingType = ImplementedByProcessor.getWrapperClass(interfaceType);
    }

    /**
     * Executed on invoke of the requested proxy. Used to gather a list of wrapped WebElements.
     *
     * @param o       object to invoke on
     * @param method  method to invoke
     * @param objects parameters for method
     * @return return value from method
     * @throws Throwable when frightened.
     */
    @Override
    public Object invoke(final Object o, final Method method, final Object[] objects) throws Throwable {
        final By by;
        final Field elementField;
        try {
            elementField = locator.getClass().getDeclaredField("by");
            elementField.setAccessible(true);
            by = (By) elementField.get(locator);
        } catch (final Exception e) {
            throw new CoreException("Failed to obtain element locator - " + locator.toString());
        }
        final List<Object> wrappedList = new ArrayList<Object>();
        final Constructor<?> cons = wrappingType.getConstructor(WebDriver.class, WebElement.class, By.class, ElementLocator.class);
        if (locator.findElements().isEmpty())
            throw new ElementNotPresentException("Element not found  - " + locator);
        for (final WebElement element : locator.findElements()) {
            final Object thing = cons.newInstance(driver, element, by, locator);
            wrappedList.add(wrappingType.cast(thing));
        }
        try {
            return method.invoke(wrappedList, objects);
        } catch (final InvocationTargetException e) {
            // Unwrap the underlying exception
            throw e.getCause();
        }
    }


}