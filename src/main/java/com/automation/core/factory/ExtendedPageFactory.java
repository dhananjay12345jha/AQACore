package com.automation.core.factory;

import com.automation.core.exceptions.CoreException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Element factory for wrapped elements. Similar to {@link org.openqa.selenium.support.PageFactory}
 */
public class ExtendedPageFactory {
    /**
     * See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.WebDriver driver, Class)}
     */
    public static <T> T initElements(final WebDriver driver, final Class<T> pageClassToProxy) {
        final T page = instantiatePage(driver, pageClassToProxy);
        final WebDriver driverRef = driver;
        PageFactory.initElements(new ElementDecorator(new CustomElementLocatorFactory(driverRef)), page);
        return page;
    }
    /**
     * See {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.FieldDecorator, Object)}
     */
    public static void initElements(final WebDriver driver, final Object page) {
        final WebDriver driverRef = driver;
        PageFactory.initElements(new ElementDecorator(new CustomElementLocatorFactory(driverRef), driverRef), page);
    }

    /**
     * see {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.ElementLocatorFactory, Object)}
     */
    public static void initElements(final CustomElementLocatorFactory factory, final Object page) {
        final CustomElementLocatorFactory factoryRef = factory;
        PageFactory.initElements(new ElementDecorator(factoryRef), page);
    }

    /**
     * see {@link org.openqa.selenium.support.PageFactory#initElements(org.openqa.selenium.support.pagefactory.ElementLocatorFactory, Object)}
     */
    public static void initElements(final FieldDecorator decorator, final Object page) {
        PageFactory.initElements(decorator, page);
    }

    private static <T> T instantiatePage(final WebDriver driver, final Class<T> pageClassToProxy) {
        try {
            try {
                final Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
                return constructor.newInstance(driver);
            } catch (final NoSuchMethodException e) {
                try {
                    return pageClassToProxy.newInstance();
                } catch (final InstantiationException ie) {
                    throw new CoreException("Failed to create instance of: " + pageClassToProxy.getName());
                }
            }
        } catch (final InstantiationException e) {
            throw new CoreException("Failed to create instance of: " + pageClassToProxy.getName());
        } catch (final IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}