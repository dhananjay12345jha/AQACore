package com.automation.core.base;

import com.automation.core.driver.CoreDriver;
import com.automation.core.exceptions.ElementEnabledException;
import com.automation.core.exceptions.ElementNotEnabledException;
import com.automation.core.exceptions.ElementNotPresentException;
import com.automation.core.exceptions.ElementVisibleException;
import com.automation.core.utilities.Sleeper;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Coordinates;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.automation.core.allure.AllurePaths.DEFAULT_POLLING_TIME;
import static com.automation.core.allure.AllurePaths.DEFAULT_WAIT_TIME;
import static org.testng.Reporter.log;

/**
 * An implementation of the {@link ExtWebElement} interface. Delegates its work to an
 * underlying WebElement instance for custom functionality.
 */
@Slf4j
public class ExtWebElementImpl implements ExtWebElement {

    protected WebElement element;
    protected By by;
    protected WebDriver driver;
    protected ElementLocator locator;

    private static final String JSHIGHLIGHT = "arguments[0].style.border='3px solid red'";
    private static final String JSCLEARHIGHLIGHT = "arguments[0].style.border='0px'";

    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  WebDriver
     * @param element WebElement to wrap up
     * @param by      by locator to wrap WebElement
     * @param locator element locator
     */
    public ExtWebElementImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        this.driver = driver;
        this.element = element;
        this.by = by;
        this.locator = locator;
    }


    @Override
    public void click() {
        log("Element Click " + elementLocator());
        getElement().click();
    }

    @Override
    public void jsClick() {
        log("Element Javascript Click " + elementLocator());
        ((CoreDriver) getDriver()).executeScript("arguments[0].scrollIntoView(true);arguments[0].click();", getElement());
    }

    /**
     * @see WebElement#submit()
     */
    @Override
    public void submit() {
        waitUntilPresent();
        log("Element Submit " + elementLocator());
        getElement().submit();
    }

    /**
     * @see WebElement#sendKeys(CharSequence...)
     */
    @Override
    public void sendKeys(final CharSequence... textToSet) {
        waitUntilPresent();
        getElement().sendKeys(textToSet);
        final StringBuilder keys = new StringBuilder();
        for (CharSequence key : textToSet) {
            if (key instanceof Keys) {
                if (keys.length() == 0) {
                    keys.append("Key.").append(((Keys) key).name());
                } else {
                    keys.append(" + Key.").append(((Keys) key).name());
                }
            } else {
                keys.append(key.toString());
            }
        }
        log("Sending text/keys { " + keys + " } to the textbox " + elementLocator());
    }

    /**
     * @param textToSet text to set
     * @see ExtWebElement#sendKeybyKey(String)
     */
    @Override
    public void sendKeybyKey(final String textToSet) {
        waitUntilPresent();
        Arrays.stream(textToSet.split("")).forEach(str -> {
            getElement().sendKeys(str);
            Sleeper.sleep(200);
        });
    }

    /**
     * @param timegap   time gap between each key stroke in millis
     * @param textToSet test to set
     * @see ExtWebElement#sendKeybyKey(long, String)
     */
    @Override
    public void sendKeybyKey(final long timegap, final String textToSet) {
        waitUntilPresent();
        Arrays.stream(textToSet.split("")).forEach(str -> {
            getElement().sendKeys(str);
            Sleeper.sleep(timegap);
        });
    }

    /**
     * @see WebElement#clear()
     */
    @Override
    public void clear() {
        waitUntilPresent();
        log("Clear text " + elementLocator());
        getElement().clear();
    }

    /**
     * @see WebElement#getTagName()
     */
    @Override
    public String getTagName() {
        waitUntilPresent();
        final String tagName = getElement().getTagName();
        log("Tag name is { " + tagName + " } " + elementLocator());
        return tagName;
    }

    /**
     * @see WebElement#getAttribute(String)
     */
    @Override
    public String getAttribute(final String attr) {
        waitUntilPresent();
        final String value = getElement().getAttribute(attr);
        log("Value for the attribute { " + attr + " } is { " + value + " } " + elementLocator());
        return value;
    }

    /**
     * @see WebElement#isSelected()
     */
    @Override
    public boolean isSelected() {
        waitUntilPresent();
        final boolean selected = getElement().isSelected();
        log("Element is selected { " + selected + " } " + elementLocator());
        return selected;
    }

    /**
     * @see WebElement#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        waitUntilPresent();
        final boolean enabled = getElement().isEnabled();
        log("Element is enabled { " + enabled + " } " + elementLocator());
        return enabled;
    }

    /**
     * @see WebElement#getText()
     */
    @Override
    public String getText() {
        waitUntilPresent();
        String text = getElement().getText();
        if(text.isEmpty()) {
            Sleeper.sleep(500);
            text = getElement().getText();
        }
        log("Element text is { " + text + " } " + elementLocator());
        return text;
    }

    /**
     * @see WebElement#findElements(By)
     */
    @Override
    public List<WebElement> findElements(final By by) {
        log("Finding the element list with DefaultElementLocator { " + by.toString() + " }");
        return getElement().findElements(by);
    }

    /**
     * @see WebElement#findElement(By)
     */
    @Override
    public WebElement findElement(final By by) {
        log("Finding the element with DefaultElementLocator { " + by.toString() + " }");
        return getElement().findElement(by);
    }

    @Override
    public String getTextContent() {
        waitUntilPresent();
        final String text = getElement().getAttribute("textContent");
        log("Element text content is { " + text + " } " + elementLocator());
        return text;
    }

    @Override
    public List<ExtWebElement> findAllBy(final By by) {
        waitUntilPresent();
        return findElements(by).stream().map(element -> new ExtWebElementImpl(driver, element, by, locator)).collect(Collectors.toList());
    }

    @Override
    public ExtWebElement findBy(final By by) {
        waitUntilPresent();
        return new ExtWebElementImpl(driver, findElement(by), by, locator);
    }

    /**
     * @see WebElement#isDisplayed()
     */
    @Override
    public boolean isDisplayed() {
        waitUntilPresent();
        final boolean isDisplayed = getElement().isDisplayed();
        log("Element is displayed { " + isDisplayed + " } " + elementLocator());
        return isDisplayed;
    }

    /**
     * @see WebElement#getLocation()
     */
    @Override
    public Point getLocation() {
        waitUntilPresent();
        final Point point = getElement().getLocation();
        log("Element point X,Y { " + point.toString() + " } " + elementLocator());
        return point;
    }

    /**
     * @see WebElement#getSize()
     */
    @Override
    public Dimension getSize() {
        waitUntilPresent();
        final Dimension dimension = getElement().getSize();
        log("Element width,height { " + dimension.toString() + " } " + elementLocator());
        return dimension;
    }

    /**
     * @see WebElement#getRect()
     */
    @Override
    public Rectangle getRect() {
        waitUntilPresent();
        final Rectangle rect = getElement().getRect();
        log("Element x,y { " + rect.getPoint().toString() + " } and width,height { " + rect.getDimension().toString() + " } " + elementLocator());
        return rect;
    }

    /**
     * @see WebElement#getCssValue(String)
     */
    @Override
    public String getCssValue(final String css) {
        waitUntilPresent();
        final String cssValue = getElement().getCssValue(css);
        log("Element CSS value is  { " + cssValue + " } " + elementLocator());
        return cssValue;
    }

    @Override
    public void highlight() {
        waitUntilPresent();
        log("Element highlight " + elementLocator());
        ((CoreDriver) getDriver()).executeScript(JSHIGHLIGHT, getElement());
        Sleeper.sleep(500);
        ((CoreDriver) getDriver()).executeScript(JSCLEARHIGHLIGHT, getElement());
    }

    @Override
    public void scrollIntoView() {
        waitUntilPresent();
        log("Scrolling Element into view " + elementLocator());
        ((CoreDriver) getDriver()).executeScript("arguments[0].scrollIntoView(true);", getElement());
    }

    @Override
    public void refresh() {
        ((CoreDriver) getDriver()).executeScript("const tracker = arguments[0]._valueTracker;\n" +
                "            if (tracker) {\n" +
                "                tracker.setValue('');\n" +
                "            }\n" +
                "            arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", getElement());
    }

    /**
     * @see WebElement#getScreenshotAs(OutputType)
     */
    @Override
    public <X> X getScreenshotAs(final OutputType<X> outputType) throws WebDriverException {
        log("Take Screenshot");
        return ((TakesScreenshot) getDriver()).getScreenshotAs(outputType);
    }

    @Override
    public WebDriverWait waitForCondition() {
        return new WebDriverWait(getDriver(), DEFAULT_WAIT_TIME);
    }

    @Override
    public FluentWait fluentWait() {
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIME))
                .pollingEvery(Duration.ofSeconds(DEFAULT_POLLING_TIME))
                .ignoring(NoSuchElementException.class);
    }

    @Override
    public FluentWait fluentWait(final int timeout, final int pollingTime) {
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
    }

    public WebDriverWait waitForCondition(final long timeOutInSeconds) {
        return new WebDriverWait(getDriver(), timeOutInSeconds);
    }


    @Override
    public WebElement getWrappedElement() {
        return getElement();
    }

    @Override
    public Coordinates getCoordinates() {
        log("Locating Element Coordinates " + elementLocator());
        return ((Locatable) getElement()).getCoordinates();
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    @Override
    public WebElement getElement() {
        return element;
    }

    @Override
    public boolean isVisible() {
        log("Element visibility");
        try {
            final WebElement element = getElement();
            return (element != null) && (element.isDisplayed());
        } catch (final ElementNotVisibleException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    @Override
    public ExtWebElement waitUntilVisible() {
        try {
            waitForCondition().until(ExpectedConditions.visibilityOf(getElement()));
        } catch (final TimeoutException cause) {
            throw new ElementNotVisibleException("Element is not visible " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitUntilVisible(final long timeOutInSeconds) {
        try {
            waitForCondition(timeOutInSeconds).until(ExpectedConditions.visibilityOf(getElement()));
        } catch (final TimeoutException cause) {
            throw new ElementNotVisibleException("Element is not visible after " + timeOutInSeconds + " seconds " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitByUntilVisible() {
        try {
            waitForCondition().until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (final TimeoutException cause) {
            throw new ElementNotVisibleException("Element is not visible " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitByUntilVisible(final long timeOutInSeconds) {
        try {
            waitForCondition(timeOutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (final TimeoutException cause) {
            throw new ElementNotVisibleException("Element is not visible after " + timeOutInSeconds + " seconds " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitUntilPresent() {
        try {
            waitForCondition().until(Expectations.elementIsPresent(this));
        } catch (final TimeoutException cause) {
            throw new ElementNotPresentException("Element is not present " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitUntilPresent(final long timeOutInSeconds) {
        try {
            waitForCondition(timeOutInSeconds).until(Expectations.elementIsPresent(this));
        } catch (final TimeoutException cause) {
            throw new ElementNotPresentException("Element is not present after " + timeOutInSeconds + " seconds " + elementLocator());
        }
        return this;
    }

    @Override
    public boolean waitUntilNotVisible() {
        try {
            return waitForCondition().until(ExpectedConditions.invisibilityOf(getElement()));
        } catch (final TimeoutException cause) {
            throw new ElementVisibleException("Element is still visible " + elementLocator());
        }
    }

    @Override
    public boolean waitUntilNotVisible(final long timeOutInSeconds) {
        try {
            return waitForCondition(timeOutInSeconds).until(ExpectedConditions.invisibilityOf(getElement()));
        } catch (final TimeoutException cause) {
            throw new ElementVisibleException("Element is still visible after " + timeOutInSeconds + " seconds " + elementLocator());
        }

    }

    @Override
    public ExtWebElementImpl waitUntilEnabled() {
        try {
            waitForCondition().until(Expectations.elementIsEnabled(this));
        } catch (final TimeoutException cause) {
            throw new ElementNotEnabledException("Element is not enabled " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitUntilEnabled(final long timeOutInSeconds) {
        try {
            waitForCondition(timeOutInSeconds).until(Expectations.elementIsEnabled(this));
        } catch (final TimeoutException cause) {
            throw new ElementNotEnabledException("Element is not enabled after " + timeOutInSeconds + " seconds " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitUntilClickable() {
        try {
            waitForCondition().until(ExpectedConditions.elementToBeClickable(getElement()));
        } catch (final TimeoutException cause) {
            throw new ElementNotEnabledException("Element is not clickable" + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElement waitUntilClickable(final long timeOutInSeconds) {
        try {
            waitForCondition(timeOutInSeconds).until(ExpectedConditions.elementToBeClickable(getElement()));
        } catch (final TimeoutException cause) {
            throw new ElementNotEnabledException("Element is not clickable after " + timeOutInSeconds + " seconds " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElementImpl waitUntilDisabled() {
        try {
            waitForCondition().until(Expectations.elementIsNotEnabled(this));
        } catch (final TimeoutException cause) {
            throw new ElementEnabledException("Element is still enabled " + elementLocator());
        }
        return this;
    }

    @Override
    public ExtWebElementImpl waitUntilDisabled(final long timeOutInSeconds) {
        try {
            waitForCondition(timeOutInSeconds).until(Expectations.elementIsNotEnabled(this));
        } catch (final TimeoutException cause) {
            throw new ElementEnabledException("Element is still enabled after " + timeOutInSeconds + " seconds " + elementLocator());
        }
        return this;
    }


    @Override
    public String getValue() {
        waitUntilPresent();
        final String value = getElement().getAttribute("value");
        log("Element value attribute is { " + value + " } " + elementLocator());
        return value;
    }

    @Override
    public boolean isPresent() {
        try {
            final WebElement element = getElement();
            if (element == null) {
                return false;
            }
            element.isDisplayed();
            return true;
        } catch (final ElementNotVisibleException e) {
            return true;
        } catch (final NotFoundException e) {
            return false;
        }
    }

    /**
     * Method to log the locator
     *
     * @return locator
     */
    private String elementLocator() {
        return "with { " + locator.toString() + " }";
    }

    public String getInnerHtml()
    {
        return ((String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", element));
    }

}
