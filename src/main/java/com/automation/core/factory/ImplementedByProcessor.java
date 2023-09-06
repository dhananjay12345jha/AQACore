package com.automation.core.factory;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;

/**
 * Processes the iface type into a useful class reference for wrapping WebElements.
 */
public final class ImplementedByProcessor {
    private ImplementedByProcessor() {
    }

    /**
     * Gets the wrapper class (descended from ExtendedWebElementImpl) for the annotation @ImplementedBy.
     *
     * @param iface iface to process for annotations
     * @param <T>   type of the wrapped class.
     * @return The class name of the class in question
     */
    public static <T> Class<?> getWrapperClass(final Class<T> iface) {
        if (iface.isAnnotationPresent(ImplementedBy.class)) {
            final ImplementedBy annotation = iface.getAnnotation(ImplementedBy.class);
            final Class<?> clazz = annotation.value();
            if (ExtWebElement.class.isAssignableFrom(clazz)) {
                return annotation.value();
            }
        }
        throw new UnsupportedOperationException("Apply @ImplementedBy interface to your Interface " +
                iface.getCanonicalName() + " if you want to extend ");
    }

}
