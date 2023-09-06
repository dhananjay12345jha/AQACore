package com.automation.core.factory;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.base.ExtWebElementImpl;
import io.appium.java_client.pagefactory.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WrapsElement;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementListHandler;

import java.lang.reflect.*;
import java.util.List;

/**
 * WrappedElementDecorator recognizes a few things that DefaultFieldDecorator does not.
 * <p/>
 * It is designed to support and return concrete implementations of wrappers for a variety of common html elements.
 */
public class ElementDecorator implements FieldDecorator {
    /**
     * factory to use when generating ElementLocator.
     */
    private final CustomElementLocatorFactory factory;
    protected WebDriver driver;

    /**
     * Constructor for an ElementLocatorFactory. This class is designed to replace DefaultFieldDecorator.
     *
     * @param factory for locating elements.
     */
    public ElementDecorator(final CustomElementLocatorFactory factory) {
        this.factory = factory;
    }

    /**
     * Constructor for an ElementLocatorFactory. This class is designed to replace DefaultFieldDecorator.
     *
     * @param factory for locating elements.
     * @param driver  webdriver {@link WebDriver}
     */
    public ElementDecorator(final CustomElementLocatorFactory factory, final WebDriver driver) {
        this.factory = factory;
        this.driver = driver;
    }

    @Override
    public Object decorate(final ClassLoader loader, final Field field) {
        if (!(WebElement.class.isAssignableFrom(field.getType()) || isDecoratableList(field))) {
            return null;
        }

        final WebDriver driverRef = driver;

        if (field.getDeclaringClass() == ExtWebElementImpl.class) {
            return null;
        }

        final ElementLocator locator = factory.createLocator(field);
        if (locator == null) {
            return null;
        }

        Class<?> fieldType = field.getType();
        if (WebElement.class.equals(fieldType)) {
            fieldType = ExtWebElement.class;
        }

        if (WebElement.class.isAssignableFrom(fieldType)) {
            return proxyForLocator(loader, fieldType, locator, driverRef);
        } else if (List.class.isAssignableFrom(fieldType)) {
            final Class<?> erasureClass = getErasureClass(field);
            return proxyForListLocator(loader, erasureClass, locator, driverRef);
        } else {
            return null;
        }
    }

    protected <T> List<T> proxyForListLocator(final ClassLoader loader, final Class<T> interfaceType, final ElementLocator locator, final WebDriver driver) {
        final WebDriver driverRef = driver;
        final InvocationHandler handler;
        if (interfaceType.getAnnotation(ImplementedBy.class) != null) {
            handler = new ElementListHandler(interfaceType, locator, driverRef);
        } else {
            handler = new LocatingElementListHandler(locator);
        }
        final List<T> proxy;
        proxy = (List<T>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        return proxy;
    }

    /**
     * Generate a type-parameterized locator proxy for the element in question. We use our customized InvocationHandler
     * here to wrap classes.
     *
     * @param loader    ClassLoader of the wrapping class
     * @param fieldType Interface wrapping the underlying WebElement
     * @param locator   ElementLocator pointing at a proxy of the object on the page
     * @param driver    Webdriver instance
     * @param <T>       The interface of the proxy.
     * @return proxy representing the class we need to wrap.
     */
    protected <T> T proxyForLocator(final ClassLoader loader, final Class<T> fieldType, final ElementLocator locator, final WebDriver driver) {
        final WebDriver driverRef = driver;

        final InvocationHandler handler = new ElementHandler(fieldType, locator, driverRef);

        final T proxy;
        proxy = fieldType.cast(Proxy.newProxyInstance(
                loader, new Class[]{fieldType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
        return proxy;
    }

    private Class<?> getErasureClass(final Field field) {
        // Type erasure in Java isn't complete. Attempt to discover the generic
        // interfaceType of the list.
        final Type genericType = field.getGenericType();
        if (!(genericType instanceof ParameterizedType)) {
            return null;
        }
        return (Class<?>) ((ParameterizedType) genericType).getActualTypeArguments()[0];
    }

    private boolean isDecoratableList(final Field field) {
        if (!List.class.isAssignableFrom(field.getType())) {
            return false;
        }

        final Class<?> erasureClass = getErasureClass(field);
        if (erasureClass == null) {
            return false;
        }

        if (!WebElement.class.isAssignableFrom(erasureClass)) {
            return false;
        }

        return field.getAnnotation(FindBy.class) != null ||
               field.getAnnotation(FindBys.class) != null ||
               field.getAnnotation(FindAll.class) != null ||
               field.getAnnotation(AndroidFindBy.class) != null ||
               field.getAnnotation(AndroidFindBys.class) != null ||
               field.getAnnotation(AndroidFindAll.class) != null ||
               field.getAnnotation(iOSXCUITFindBy.class) != null ||
               field.getAnnotation(iOSXCUITFindBys.class) != null ||
               field.getAnnotation(iOSXCUITFindBys.class) != null;
    }

    /**
     * Generate a type-parameterized locator proxy for the element in question. We use our customized InvocationHandler
     * here to wrap classes.
     *
     * @param loader        ClassLoader of the wrapping class
     * @param interfaceType Interface wrapping the underlying WebElement
     * @param locator       ElementLocator pointing at a proxy of the object on the page
     * @param <T>           The interface of the proxy.
     * @return a proxy representing the class we need to wrap.
     */
    protected <T> T proxyForLocator(final ClassLoader loader, final Class<T> interfaceType, final ElementLocator locator) {
        final InvocationHandler handler = new ElementHandler(interfaceType, locator);

        final T proxy;
        proxy = interfaceType.cast(Proxy.newProxyInstance(
                loader, new Class[]{interfaceType, WebElement.class, WrapsElement.class, Locatable.class}, handler));
        return proxy;
    }

    /**
     * generates a proxy for a list of elements to be wrapped.
     *
     * @param loader        classloader for the class we're presently wrapping with proxies
     * @param interfaceType type of the element to be wrapped
     * @param locator       locator for items on the page being wrapped
     * @param <T>           class of the interface.
     * @return proxy with the same type as we started with.
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> proxyForListLocator(final ClassLoader loader, final Class<T> interfaceType, final ElementLocator locator) {
        final InvocationHandler handler;
        if (interfaceType.getAnnotation(ImplementedBy.class) != null) {
            handler = new ElementListHandler(interfaceType, locator);
        } else {
            handler = new LocatingElementListHandler(locator);
        }
        final List<T> proxy;
        proxy = (List<T>) Proxy.newProxyInstance(
                loader, new Class[]{List.class}, handler);
        return proxy;
    }
}