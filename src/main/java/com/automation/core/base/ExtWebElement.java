package com.automation.core.base;

import com.automation.core.annotation.ImplementedBy;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * wraps a web element interface with extra functionality. Anything added here will be added to all descendants.
 */
@ImplementedBy(ExtWebElementImpl.class)
public interface ExtWebElement extends WebElement, WrapsElement, Locatable {

    /**
     * Gets/Creates the {@link WebDriver} instance
     *
     * @return {@link WebDriver} object
     * @see ExtWebElementImpl#getDriver()
     */
    WebDriver getDriver();

    /**
            * Gets/Creates the {@link WebElement} instance
     *
             * @return {@link WebElement} object
     * @see ExtWebElementImpl#getElement()
     */
    WebElement getElement();

    /**
     * Method to check the visibility of {@link WebElement}
     *
     * @return true if {@link WebElement} is visible, if not false
     * @see ExtWebElementImpl#isVisible()
     */
    boolean isVisible();

    /**
     * Method to wait until the {@link ExtWebElement} is visible within default time
     *
     * @return {@link WebElement} object
     * @see ExtWebElementImpl#waitUntilVisible()
     */
    ExtWebElement waitUntilVisible();

    /**
     * Method to wait until the {@link ExtWebElement} is visible within given time
     *
     * @param timeOutInSeconds time to wait for {@link ExtWebElement} visibility
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilVisible(long)
     */
    ExtWebElement waitUntilVisible(long timeOutInSeconds);

    /**
     * Method to wait until the {@link ExtWebElement} is visible within default time with {@link By} locator
     *
     * @return {@link WebElement} object
     * @see ExtWebElementImpl#waitByUntilVisible()
     */
    ExtWebElement waitByUntilVisible();

    /**
     * Method to wait until the {@link ExtWebElement} is visible within given time with {@link By} locator
     *
     * @param timeOutInSeconds time to wait for {@link ExtWebElement} visibility
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitByUntilVisible(long)
     */
    ExtWebElement waitByUntilVisible(long timeOutInSeconds);

    /**
     * Method to wait until the {@link ExtWebElement} is present within default time
     *
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilPresent()
     */
    ExtWebElement waitUntilPresent();

    /**
     * Method to wait until the {@link ExtWebElement} is present within given time
     *
     * @param timeOutInSeconds time to wait for {@link ExtWebElement} to present
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilPresent(long)
     */
    ExtWebElement waitUntilPresent(long timeOutInSeconds);

    /**
     * Method to wait for invisibility of the {@link ExtWebElement} within default time
     *
     * @return true if {@link ExtWebElement} is not visible, if not false
     * @see ExtWebElementImpl#waitUntilNotVisible()
     */
    boolean waitUntilNotVisible();


    /**
     * Method to wait for invisibility of the {@link ExtWebElement} within given time
     *
     * @param timeOutInSeconds time to wait for invisibility of the {@link ExtWebElement}
     * @return true if {@link ExtWebElement} is not visible, if not false
     * @see ExtWebElementImpl#waitUntilNotVisible(long)
     */
    boolean waitUntilNotVisible(long timeOutInSeconds);

    /**
     * Method to wait until the {@link ExtWebElement} is enabled within default time
     *
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilEnabled()
     */
    ExtWebElement waitUntilEnabled();

    /**
     * Method to wait until the {@link ExtWebElement} is enabled within given time
     *
     * @param timeOutInSeconds time to wait for {@link ExtWebElement} to be enabled
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilEnabled(long)
     */
    ExtWebElement waitUntilEnabled(long timeOutInSeconds);

    /**
     * Method to wait until the {@link ExtWebElement} is clickable within default time
     *
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilClickable()
     */
    ExtWebElement waitUntilClickable();

    /**
     * Method to wait until the {@link ExtWebElement} is clickable within give time
     *
     * @param timeOutInSeconds time to wait for {@link ExtWebElement} to be clickable
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilClickable(long)
     */
    ExtWebElement waitUntilClickable(long timeOutInSeconds);

    /**
     * Method to wait until the {@link ExtWebElement} is disabled within default time
     *
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilDisabled()
     */
    ExtWebElement waitUntilDisabled();

    /**
     * Method to wait until the {@link ExtWebElement} is disabled within give time
     *
     * @param timeOutInSeconds time to wait for {@link ExtWebElement} to be disabled
     * @return {@link ExtWebElement} object
     * @see ExtWebElementImpl#waitUntilDisabled(long)
     */
    ExtWebElement waitUntilDisabled(long timeOutInSeconds);

    /**
     * Method to return text of value attribute for the {@link ExtWebElement}
     *
     * @return text of value attribute
     * @see ExtWebElementImpl#getValue()
     */
    String getValue();

    /**
     * Method to return inner text of the {@link ExtWebElement}
     *
     * @return inner text
     * @see ExtWebElementImpl#getText()
     */
    String getText();


    /**
     * Method to return text of textContent attribute for the {@link ExtWebElement}
     *
     * @return text of textContent attribute
     * @see ExtWebElementImpl#getTextContent()
     */
    String getTextContent();

    /**
     * Method to click the {@link ExtWebElement}
     *
     * @see ExtWebElementImpl#click()
     */
    void click();

    /**
     * Method to click the {@link ExtWebElement} using javascript with {@link JavascriptExecutor}
     *
     * @see ExtWebElementImpl#jsClick()
     */
    void jsClick();

    /**
     * Method to clear/delete within the {@link ExtWebElement}
     *
     * @see ExtWebElementImpl#clear()
     */
    void clear();

    /**
     * Method to set text to the {@link ExtWebElement}
     *
     * @param keysToSend text to set
     * @see ExtWebElementImpl#sendKeys(CharSequence...)
     */
    void sendKeys(CharSequence... keysToSend);

    /**
     * Method to set text to the {@link ExtWebElement}
     *
     * @param keysToSend text to set
     * @see ExtWebElementImpl#sendKeybyKey(String)
     */
    void sendKeybyKey(String keysToSend);

    /**
     * Method to set text to the {@link ExtWebElement}
     *
     * @param keysToSend text to set
     * @param timegap    time gap between each key stroke in millis
     * @see ExtWebElementImpl#sendKeybyKey(long, String)
     */
    void sendKeybyKey(long timegap, String keysToSend);

    /**
     * Method to submit the form {@link ExtWebElement}
     *
     * @see ExtWebElementImpl#submit()
     */
    void submit();

    /**
     * Method to return tag name of the {@link ExtWebElement}
     *
     * @return tag name
     * @see ExtWebElementImpl#getTagName()
     */
    String getTagName();

    /**
     * Method to return text value of given attribute of the {@link ExtWebElement}
     *
     * @return text value of given attribute
     * @see ExtWebElementImpl#getAttribute(String)
     */
    String getAttribute(String propertyName);

    /**
     * Method to check the {@link ExtWebElement} is selected or not
     *
     * @return true if {@link ExtWebElement} is selected, if not false
     * @see ExtWebElementImpl#isSelected()
     */
    boolean isSelected();

    /**
     * Method to check the {@link ExtWebElement} is enabled or not
     *
     * @return true if {@link ExtWebElement} is enabled, if not false
     * @see ExtWebElementImpl#isEnabled()
     */
    boolean isEnabled();

    /**
     * Method to return the list of elements {@link List<ExtWebElement>}
     *
     * @param by locator
     * @return List of WebElements
     * @see ExtWebElementImpl#findAllBy(By)
     */
    List<ExtWebElement> findAllBy(By by);

    /**
     * Method to return the {@link ExtWebElement} with locator
     *
     * @param by locator
     * @return {@link ExtWebElement}
     * @see ExtWebElementImpl#findBy(By)
     */
    ExtWebElement findBy(By by);

    /**
     * Method to check the {@link ExtWebElement} is displayed or not
     *
     * @return true if {@link ExtWebElement} is displayed, if not false
     * @see ExtWebElementImpl#isDisplayed()
     */
    boolean isDisplayed();

    /**
     * Method to get the point({@link Point}) location of the {@link ExtWebElement}
     *
     * @return location
     * @see ExtWebElementImpl#getLocation()
     */
    Point getLocation();

    /**
     * Method to get the dimension({@link Dimension}) of the {@link ExtWebElement}
     *
     * @return dimension
     * @see ExtWebElementImpl#getSize()
     */
    Dimension getSize();

    /**
     * Method to get the rectangle({@link Rectangle}) of the {@link ExtWebElement}
     *
     * @return rectangle
     * @see ExtWebElementImpl#getRect()
     */
    Rectangle getRect();

    /**
     * Method to return CSS property for the {@link ExtWebElement}
     *
     * @param propertyName CSS property
     * @return CSS value for the property
     * @see ExtWebElementImpl#getTagName()
     */
    String getCssValue(String propertyName);

    /**
     * Method to highlight the {@link ExtWebElement}
     *
     * @see ExtWebElementImpl#highlight()
     */
    void highlight();

    /**
     * Method to scroll to the the {@link ExtWebElement}
     *
     * @see ExtWebElementImpl#scrollIntoView()
     */
    void scrollIntoView();

    /**
     * Method to refresh the {@link ExtWebElement}
     * This method is for iOS only
     *
     * @see ExtWebElementImpl#refresh()
     */
    void refresh();

    /**
     * Method to take the screenshot of the page
     *
     * @param target screenshot format {@link OutputType}
     * @return screenshot of provided format
     * @see ExtWebElementImpl#getScreenshotAs(OutputType)
     */
    <X> X getScreenshotAs(OutputType<X> target);

    /**
     * Webdriver wait method {@link WebDriverWait}
     *
     * @return webdriver wait object
     * @see ExtWebElementImpl#waitForCondition()
     */
    WebDriverWait waitForCondition();

    /**
     * Webdriver wait method {@link FluentWait}
     *
     * @return webdriver wait object
     * @see ExtWebElementImpl#fluentWait()
     */
    FluentWait fluentWait();

    /**
     * Webdriver wait method {@link FluentWait}
     *
     * @return webdriver wait object
     * @see ExtWebElementImpl#fluentWait(int, int)
     */
    FluentWait fluentWait(int timeout, int pollingtime);

    /**
     * Method to check the {@link ExtWebElement} is present or not
     *
     * @return true if {@link ExtWebElement} is present, if not false
     * @see ExtWebElementImpl#isPresent()
     */
    boolean isPresent();

    /**
     * Method to get the inner HTML
     *
     * @return inner HTML
     * @see ExtWebElementImpl#getInnerHtml()
     */
    String getInnerHtml();
}
