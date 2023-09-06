package com.automation.core.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;
import java.util.List;

/**
 * ExpectedConditions to wait for a particular condition
 *
 * @see org.openqa.selenium.support.ui.ExpectedConditions
 */
public class Expectations {

    private final static List<String> HTML_FORM_TAGS = Arrays.asList("input", "button", "select", "textarea", "link", "option");

    public static ExpectedCondition<Boolean> elementIsPresent(final ExtWebElement element) {
        return new ExpectedCondition<Boolean>() {
            private ExtWebElement element;

            public ExpectedCondition<Boolean> forElement(final ExtWebElement element) {
                this.element = element;
                return this;
            }

            public Boolean apply(final WebDriver driver) {
                return element.isPresent();
            }

            @Override
            public String toString() {
                return element.toString() + " to be present";
            }

        }.forElement(element);
    }

    /**
     * Condition to wait for the {@link WebElement} to be enabled
     *
     * @param element {@link ExtWebElementImpl}
     * @return Expected Condition is true or false
     */
    public static ExpectedCondition<Boolean> elementIsEnabled(final ExtWebElementImpl element) {
        return new ExpectedCondition<Boolean>() {
            private ExtWebElementImpl element;

            public ExpectedCondition<Boolean> forElement(final ExtWebElementImpl element) {
                this.element = element;
                return this;
            }

            public Boolean apply(final WebDriver driver) {
                final WebElement resolvedElement = element.getElement();
                return ((resolvedElement != null) && (!isDisabledField(element)));
            }

            @Override
            public String toString() {
                return element.toString() + " to be enabled";
            }

        }.forElement(element);
    }

    /**
     * Condition to wait for the {@link WebElement} tis not enabled
     *
     * @param element {@link ExtWebElementImpl}
     * @return Expected Condition is true or false
     */
    public static ExpectedCondition<Boolean> elementIsNotEnabled(final ExtWebElementImpl element) {
        return new ExpectedCondition<Boolean>() {
            private ExtWebElementImpl element;

            public ExpectedCondition<Boolean> forElement(final ExtWebElementImpl element) {
                this.element = element;
                return this;
            }

            public Boolean apply(final WebDriver driver) {
                final WebElement resolvedElement = element.getElement();
                return ((resolvedElement != null) && (!resolvedElement.isEnabled()));
            }

            @Override
            public String toString() {
                return element.toString() + " to not be enabled";
            }

        }.forElement(element);
    }

    /**
     * Method to check {@link WebElement} is disabled
     *
     * @param element {@link WebElement}
     * @return {@link WebElement} is disabled or not
     */
    private static boolean isDisabledField(final WebElement element) {
        return (isAFormElement(element) && (!element.isEnabled()));
    }

    /**
     * Method to check {@link WebElement} is a form element
     *
     * @param element {@link WebElement}
     * @return {@link WebElement} is form element or not
     */
    private static boolean isAFormElement(final WebElement element) {
        if ((element == null) || (element.getTagName() == null)) {
            return false;
        }
        final String tag = element.getTagName().toLowerCase();
        return HTML_FORM_TAGS.contains(tag);

    }
}
