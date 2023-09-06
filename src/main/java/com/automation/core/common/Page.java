package com.automation.core.common;

import com.automation.core.base.ExtWebElement;
import com.automation.core.driver.CoreAppiumDriver;
import com.automation.core.driver.CoreDriver;
import com.automation.core.driver.CoreIOSDriver;
import com.automation.core.exceptions.ElementNotEnabledException;
import com.automation.core.factory.ExtendedPageFactory;
import com.automation.core.utilities.Sleeper;
import com.automation.core.utilities.grid.PropUtils;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static com.automation.core.allure.AllurePaths.DEFAULT_POLLING_TIME;
import static com.automation.core.allure.AllurePaths.DEFAULT_WAIT_TIME;
import static org.testng.Reporter.log;

/**
 * Base Page with reusable methods
 */
@Component
@Slf4j
public abstract class Page {

    private static final int MOBILE_SCREENWIDTH = 768;
    private static final int TABLET_SCREENWIDTH = 1025;

    //Driver object
    @Autowired(required = false)
    private CoreDriver driver;

    //Driver object
    @Autowired(required = false)
    private CoreIOSDriver iosDriver;

    //Driver object
    @Autowired(required = false)
    private CoreAppiumDriver appiumDriver;

    /**
     * Page factory to initialise the Page Elements
     *
     * @see ExtendedPageFactory#initElements(WebDriver, Object)
     */
    @PostConstruct
    private void init() {
        ExtendedPageFactory.initElements(getDriver().getWrappedDriver(), this);
    }

    public CoreDriver getDriver() {
        if (appiumDriver != null) {
            return appiumDriver;
        } else if (iosDriver != null) {
            return iosDriver;
        } else {
            return driver;
        }
    }

    /**
     * Function to return the Page Title
     *
     * @return Page title
     */
    public String getTitle() {
        final String title = getDriver().getTitle();
        log("Title of the page is " + title);
        return title;
    }

    /**
     * Function to return Current URL
     *
     * @return Current URL
     */
    public String getCurrentUrl() {
        final String currentUrl = getDriver().getCurrentUrl();
        log("Current URL is " + currentUrl);
        return currentUrl;
    }

    /**
     * Function to pause the script for given time
     *
     * @param timeInMilliseconds Pause time in milli seconds
     */
    public void waitFor(final long timeInMilliseconds) {
        log("Sleeping for " + timeInMilliseconds + " milliseconds");
        Sleeper.sleep(timeInMilliseconds);
    }

    /**
     * Function to open the URL
     *
     * @param url URL string
     */
    public void open(final String url) {
        log("Opening the URL " + url);
        if (PropUtils.has("external")) {
            //Hit Cookie url once
            final String baseUrl = url.substring(0, url.indexOf(".com") + 4);
            final String cookieUrl = baseUrl + "/S5g86bZGwT98rwxGTHyr7D49hc2EEw2bA9funj7PpZq2vHUdGMAqtXvafKbyyBfN/entry.html";
            getDriver().get(cookieUrl);
        }
        getDriver().get(url);
    }

    /**
     * Function to Switch to {@link Alert}
     *
     * @return {@link Alert} instance
     */
    public Alert getAlert() {
        log("Switching to the alert");
        return getDriver().switchTo().alert();
    }

    /**
     * Function to get {@link Actions} object
     *
     * @return {@link Actions} object
     */
    public Actions action() {
        return new Actions(getDriver().getWrappedDriver());
    }

    /**
     * Function to refresh the browser
     */
    public void browserRefresh() {
        log("Browser refresh");
        getDriver().navigate().refresh();
    }

    /**
     * Function to simulate browser back
     */
    public void browserBack() {
        log("Browser back");
        getDriver().navigate().back();
    }

    /**
     * Method to simulate browser back of a specific number of pages
     * @param numOfPages to navigate back by
     */
    public void navigateBackXPages(final int numOfPages) {
        final CoreDriver coreDriver = getDriver();
        coreDriver.executeScript("window.history.go(-" + numOfPages + ")");
    }

    /**
     * Function to simulate browser forward
     */
    public void browserForward() {
        log("Browser forward");
        getDriver().navigate().forward();
    }

    /**
     * Function to navigate to given url
     *
     * @param url URL String
     */
    public void goTo(final String url) {
        log("Navigate to URL " + url);
        getDriver().navigate().to(url);
    }

    /**
     * Function to switch to default content
     */
    public void switchToDefault() {
        log("Switch to default window");
        getDriver().switchTo().defaultContent();
    }

    /**
     * Function to switch to window
     *
     * @param window window name/id
     */
    public void switchToWindow(final String window) {
        log("Switch to window " + window);
        getDriver().switchTo().window(window);
    }

    /**
     * Function to get the current window handle
     *
     * @return current window handle
     */
    public String getWindowHandle() {
        final String windowHandle = getDriver().getWindowHandle();
        log("Current window handle is " + windowHandle);
        return windowHandle;
    }

    /**
     * Function to get all the window handles as {@link Set}
     *
     * @return all the window handles
     */
    public Set<String> getWindowHandles() {
        final Set<String> windowHandles = getDriver().getWindowHandles();
        log("List fo windows " + new ArrayList<>(windowHandles));
        return windowHandles;
    }

    /**
     * Function to get all the window handles as {@link List}
     *
     * @return all the window handles
     */
    public List<String> getWindowHandlesList() {
        final List<String> windowHandles = new ArrayList<>(getDriver().getWindowHandles());
        log("List fo windows " + windowHandles);
        return windowHandles;
    }

    /**
     * Method to switch to a specified browser tab
     *
     * @param specifiedTab as int of tab to switch to
     */
    public void switchTab(final int specifiedTab) {
        final ArrayList<String> tabs = new ArrayList<>(getDriver().getWindowHandles());
        getDriver().switchTo().window(tabs.get(specifiedTab));
    }

    /**
     * Method to create wait condition
     *
     * @return {@link WebDriverWait}
     */
    public WebDriverWait waitForCondition() {
        return new WebDriverWait(getDriver().getWrappedDriver(), Duration.ofSeconds(DEFAULT_WAIT_TIME));
    }

    /**
     * Method to create wait condition with given time
     *
     * @param timeOutInSeconds time out in seconds
     * @return {@link WebDriverWait}
     */
    public WebDriverWait waitForCondition(final long timeOutInSeconds) {
        return new WebDriverWait(getDriver().getWrappedDriver(), Duration.ofMillis(timeOutInSeconds));
    }

    /**
     * Method to create wait condition
     *
     * @return {@link FluentWait}
     */
    public FluentWait fluentWait() {
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_WAIT_TIME))
                .pollingEvery(Duration.ofSeconds(DEFAULT_POLLING_TIME))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Method to create wait condition with given time
     *
     * @param timeout     time out in seconds
     * @param pollingTime polling time in seconds
     * @return {@link FluentWait}
     */
    public FluentWait fluentWait(final int timeout, final int pollingTime) {
        return new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Method to create page element as WebElement
     *
     * @param by locator required
     * @return WebElement
     */
    protected WebElement find(final By by) {
        return getDriver().findElement(by);
    }

    /**
     * Method to create list of page elements as WebElements
     *
     * @param by locator required
     * @return List of WebElements
     */
    protected List<WebElement> findAll(final By by) {
        return getDriver().findElements(by);
    }

    /**
     * Move to page element
     *
     * @param by as locator
     */
    protected void moveTo(final By by) {
        action().moveToElement(find(by)).perform();
    }

    /**
     * Move to page element
     *
     * @param webElement as locator
     */
    protected void moveTo(final WebElement webElement) {
        action().moveToElement(webElement).perform();
    }

    /**
     * Move to element using JavascriptExecutor
     *
     * @param by as locator
     */
    protected void jsMoveTo(final By by) {
        getDriver().executeScript("arguments[0].scrollIntoView();", find(by));
    }

    /**
     * Method to scroll to and click page element using JavascriptExecutor
     *
     * @param by as locator
     */
    protected void scrollAndClick(final By by) {
        try {
            jsMoveTo(by);
            click(by);
        } catch (final StaleElementReferenceException ex) {
            jsMoveTo(by);
            click(by);
            System.out.println("INFO: Had stale element reference with selector = " + by.toString());
        }
    }

    /**
     * Method to move to and click page element.
     * actions are not chained to enable the method to work on iOS devices
     *
     * @param by as locator
     */
    protected void moveAndClick(final By by) {
        action().click(find(by)).build().perform();
    }

    /**
     * Method to move to and click page element
     * actions are not chained to enable the method to work on iOS devices
     *
     * @param element WebElement as locator
     */
    protected void moveAndClick(final WebElement element) {
        action().click(element).build().perform();
    }

    /**
     * Method to click element located
     *
     * @param by as locator
     */
    protected void click(final By by) {
        find(by).click();
    }

    /**
     * Method to execute specified String as code using JavascriptExecutor against the page element
     *
     * @param jsCode code to be executed
     * @param by as locator
     */
    protected void jsExecutor(final String jsCode, final By by) {
        getDriver().executeScript(jsCode, find(by));
    }

    /**
     * Method to execute specified String as code using JavascriptExecutor against the page element
     *
     * @param jsCode code to be executed
     * @param element WebElement as locator
     * @return
     */
    protected String jsExecutor(final String jsCode, final WebElement element) {
        return getDriver().executeScript(jsCode, element).toString();
    }

    /**
     * Method to execute specified String as code using JavascriptExecutor
     *
     * @param jsCode code to be executed
     */
    protected void jsExecutor(final String jsCode) {
        getDriver().executeScript(jsCode);
    }

    /**
     * Method to click element using JavascriptExecutor
     *
     * @param by as locator
     */
    protected void jsClick(final By by) {
        final String js = "arguments[0].click();";
        jsExecutor(js, by);
    }

    /**
     * Method to send text to page element, with or without clearing existing text first
     *
     * @param by as locator
     * @param text to be sent
     * @param append true to add text to existing text in field, or false to clear field first
     */
    protected void sentKeys(final By by, final String text, final boolean append) {
        final WebElement element = find(by);
        if (!append) {
            clear(element);
        }
        element.sendKeys(text);
    }

    /**
     * Method to send text to page element without clearing existing text
     *
     * @param by as locator
     * @param text to be sent
     */
    protected void sentKeys(final By by, final String text) {
        sentKeys(by, text, false);
    }

    protected void sentKeys(final ExtWebElement element, final Keys keys) {

    }

    /**
     * Method that will clear text from a specified field.
     * This will work on all operating systems including iOS and Android
     *
     * @param by as locator
     */
    protected void clear(final By by) {
        final WebElement element = find(by);
        if (isTablet() || isMobile()) {
            while (!element.getText().isEmpty()) {
                element.sendKeys(Keys.BACK_SPACE);
            }
        } else {
            element.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        }
    }

    /**
     * Method that will clear text from a specified field.
     * This will work on all operating systems including iOS and Android
     *
     * @param webElement ExtWebElement as locator
     */
    protected void clear(final ExtWebElement webElement) {
        if (isTablet() || isMobile()) {
            while (!webElement.getValue().isEmpty()) {
                webElement.sendKeys(Keys.BACK_SPACE);
            }
        } else {
            webElement.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        }
    }

    /**
     * Method that will clear text from a specified field.
     * This will work on all operating systems including iOS and Android
     *
     * @param webElement as locator
     */
    protected void clear(final WebElement webElement) {
        if (isTablet() || isMobile()) {
            while (!webElement.getText().isEmpty()) {
                webElement.sendKeys(Keys.BACK_SPACE);
            }
        } else {
            webElement.sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        }
    }

    /**
     * Method to get the text of element with exception catching and JavascriptExecutor used as retry
     *
     * @param by as locator
     * @return text found
     */
    protected String getText(final By by) {
        String text;
        try {
            text = find(by).getText();
        } catch (final StaleElementReferenceException ex) {
            text = jsExecutor("return arguments[0].value;", find(by));
        }
        return text;
    }

    /**
     * Method to get the text of element with exception catching and JavascriptExecutor used as retry
     *
     * @param element WebElement as locator
     * @return text found
     */
    protected String getText(final WebElement element) {
        String text;
        try {
            text = element.getText();
        } catch (final StaleElementReferenceException ex) {
            text = jsExecutor("return arguments[0].value;", element);
        }
        return text;
    }

    /**
     * Method to return the attribute "value" of an element
     *
     * @param by as locator
     * @return value found
     */
    protected String getValue(final By by) {
        return find(by).getAttribute("value");
    }

    /**
     * Method to send Tab key to element locator using By
     *
     * @param by as locator
     */
    protected void pressTab(final By by) {
        final WebElement element = find(by);
        element.sendKeys(Keys.TAB);
    }

    /**
     * Method to select element using Selenium Select with by as locator
     *
     * @param by as locator
     * @return new Select
     */
    protected org.openqa.selenium.support.ui.Select select(final By by) {
        return (new org.openqa.selenium.support.ui.Select(find(by)));
    }

    /**
     * Method to select element by a specific argument
     *
     * @param by as locator
     * @param consumer as argument
     */
    protected void selectBy(final By by, final Consumer<Select> consumer) {
        consumer.accept(select(by));
    }

    /**
     * Method to select element by specified visible text, and return boolean value
     *
     * @param by as locator
     * @param visibleText to select by
     * @return true if text is selected, false if exception thrown
     */
    protected boolean isSelectedByVisibleText(final By by, final String visibleText) {
        boolean executedWithoutError = true;
        try {
            select(by).selectByVisibleText(visibleText);
        } catch (final WebDriverException ex) {
            executedWithoutError = false;
        }
        return executedWithoutError;
    }

    /**
     * Method to check if element is selected using By
     *
     * @param by as locator
     * @return boolean value
     */
    protected boolean isSelected(final By by) {
        return find(by).isSelected();
    }

    /**
     * Method to check if element is not selected using By
     *
     * @param by as locator
     * @return boolean value
     */
    protected boolean isNotSelected(final By by) {
        return !isSelected(by);
    }

    /**
     * Method to click checkbox if it is selected but user wants it unselected or,
     * click checkbox if it's not selected but user wants it selected
     *
     * @param checkbox boolean value of whether checkbox should be selected or not
     * @param by as locator
     * @param clickFunction to click checkbox
     */
    protected void placeCheckboxInDesiredState(final boolean checkbox, final By by, final Consumer<By> clickFunction) {
        if (!checkbox && isSelected(by)) {
            clickFunction.accept(by);
        }
        if (checkbox && isNotSelected(by)) {
            clickFunction.accept(by);
        }
    }

    /**
     * Method to click checkbox if it is selected but user wants it unselected or,
     * click checkbox if it's not selected but user wants it selected.
     * Uses JavascriptExecutor
     *
     * @param checkbox boolean value of whether checkbox should be selected or not
     * @param by as locator
     */
    protected void placeCheckboxInDesiredState(final boolean checkbox, final By by) {
        placeCheckboxInDesiredState(checkbox, by, element -> scrollAndClick(by));
    }

    /**
     * Method to return boolean value if element is present in the dom or not, with exception handling
     *
     * @param duration of time to wait for
     * @param by as locator
     * @return boolean value
     */
    protected boolean isElementPresent(final Duration duration, final By by) {
        boolean found = true;
        try {
            untilPresent(duration, by);
        } catch (final TimeoutException ex) {
            found = false;
        }
        return found;
    }

    /**
     * Method to return boolean value if element is present in the dom or not, with no wait
     *
     * @param by as locator
     * @return boolean value
     */
    protected boolean isElementPresent(final By by) {
        return isElementPresent(Duration.ZERO, by);
    }

    /**
     * Method to return boolean value if element is visible or not, with exception handling
     *
     * @param by as locator
     * @param duration of time to wait for
     * @return boolean value
     */
    protected boolean isElementVisible(final By by, final Duration duration) {
        boolean visible = true;
        try {
            until(duration, ExpectedConditions.visibilityOfElementLocated(by));
        } catch (final TimeoutException | ElementNotVisibleException ex) {
            visible = false;
        }
        return visible;
    }

    /**
     * Method to return boolean value if element is visible or not, with exception handling
     *
     * @param webElement as locator
     * @param duration of time to wait for
     * @return boolean value
     */
    protected boolean isElementVisible(final WebElement webElement, final Duration duration) {
        boolean visible = true;
        try {
            until(duration, ExpectedConditions.visibilityOf(webElement));
        } catch (final TimeoutException | ElementNotVisibleException ex) {
            visible = false;
        }
        return visible;
    }

    /**
     * Method to return boolean value if element is visible in the dom or not, with no wait
     *
     * @param by as locator
     * @return boolean value
     */
    protected boolean isElementVisible(final By by) {
        return isElementVisible(by, Duration.ZERO);
    }

    /**
     * Method to return boolean value if element is visible in the dom or not, with no wait
     *
     * @param webElement as locator
     * @return boolean value
     */
    protected boolean isElementVisible(final WebElement webElement) {
        return isElementVisible(webElement, Duration.ZERO);
    }

    /**
     * Method to check if specified text is present, with wait and exception handling
     *
     * @param text to check presence of
     * @param duration of time to wait for
     * @return boolean value
     */
    protected boolean hasTextPresent(final String text, final Duration duration) {
        boolean found = true;
        try {
            untilPresent(duration, By.xpath("//*[contains(text(), '" + text + "')]"));
        } catch (final TimeoutException ex) {
            found = false;
        }
        return found;
    }

    /**
     * Method to check if specified text is present, with no wait
     *
     * @param text to check presence of
     * @return boolean value
     */
    protected boolean hasTextPresent(final String text) {
        return hasTextPresent(text, Duration.ZERO);
    }

    /**
     * Method used to wait specified time until specified expected condition
     * StaleElementReference and NoSuchElement exceptions are ignored
     *
     * @param duration of time to wait for
     * @param expectedCondition to wait for
     */
    protected void until(final Duration duration, final ExpectedCondition<?> expectedCondition) {
        final Wait<WebDriver> wait = new FluentWait<>(getDriver().getWrappedDriver())
                .withTimeout(duration)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class);

        wait.until(expectedCondition);
    }

    /**
     * Method to wait for element to be present in dom for specified time
     *
     * @param duration of time to wait for
     * @param by as locator
     */
    protected void untilPresent(final Duration duration, final By by) {
        until(duration, ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Method to wait for element to be visible for specified time
     *
     * @param duration of time to wait for
     * @param by as locator
     */
    protected void untilVisible(final Duration duration, final By by) {
        until(duration, ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Method to wait specified time for element to be in enabled state, with exception handling
     *
     * @param duration of time to wait for
     * @param by as locator
     */
    protected void untilEnabled(final Duration duration, final By by) {
        try {
            until(duration, ExpectedConditions.elementToBeClickable(by));
        } catch (final ElementNotEnabledException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * Method to wait specified time until an element with specific text is present
     * Note: Any ' inside the text argument need to be escaped
     *
     * @param duration
     * @param text
     */
    protected void untilElementWithTextIsPresent(final Duration duration, final String text) {
        final String selector = String.format("//*[contains(text(), \"%s\")]", text);
        untilPresent(duration, By.xpath(selector));
    }

    /**
     * Method to wait specified time until an element with specific text is visible
     * Note: Any ' inside the text argument need to be escaped
     *
     * @param duration
     * @param text
     */
    protected void untilElementWithTextIsVisible(final Duration duration, final String text) {
        final String selector = String.format("//*[contains(text(), \"%s\")]", text);
        untilVisible(duration, By.xpath(selector));
    }

    /**
     * Method using JavascriptExecutor to get local storage value of specified key
     *
     * @param key to get value of
     * @return value as String
     */
    public String getLocalStorageValue(final String key) {
        return ((JavascriptExecutor) getDriver()).executeScript(
                "return  window.localStorage.getItem('" + key + "')").toString();
    }

    /**
     * Method to verify current device is Mobile
     *
     * @return true if device is Mobile
     */
    public boolean isMobile() {
        return viewPortWidth() < MOBILE_SCREENWIDTH;
    }

    /**
     * Method to verify current device is Tablet
     *
     * @return true if device is Tablet
     */
    public boolean isTablet() {
        return (viewPortWidth() >= MOBILE_SCREENWIDTH && viewPortWidth() < TABLET_SCREENWIDTH);
    }

    /**
     * Method to verify current device is Desktop
     *
     * @return true if device is Desktop
     */
    public boolean isDesktop() {
        return (viewPortWidth() >= TABLET_SCREENWIDTH);
    }


    /**
     * Method to get Browser Viewport Width
     *
     * @return view port width
     */
    private long viewPortWidth() {
        return (long) executeScript("return window.innerWidth");
    }

    /**
     * Method to execute Javascript
     *
     * @param script script to be executed
     * @return Object
     */
    public Object executeScript(final String script) {
        return getDriver().executeScript(script);
    }

    /**
     * Method to execute Javascript
     *
     * @param script  script to be executed
     * @param objects objects to be passed
     * @return Object
     */
    public Object executeScript(final String script, final Object... objects) {
        return getDriver().executeScript(script, objects);
    }

    /**
     * Method to scroll to bottom of the page
     */
    public void scrollToBottom() {
        getDriver().executeScript("window.scrollTo(0, 999999999999)");
    }

    /**
     * Method to scroll to top of the page
     */
    public void scrollToTop() {
        getDriver().executeScript("window.scrollTo(0, 0)");
    }


    public long getPageYOffset(){
        return (long) getDriver().executeScript("return window.pageYOffset;");
    }

    /**
     * Method to override Implicit Timeout
     *
     * @param timeInSecs time in seconds
     */
    public void setImplicitTimeout(final long timeInSecs) {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(DEFAULT_WAIT_TIME));
    }

    /**
     * Method to reset Implicit Timeout to default
     */
    public void setImplicitTimeoutDefault() {
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(DEFAULT_WAIT_TIME));
    }

    public void waitForPageLoad()
    {

        try
        {
            Sleeper.sleep(100);
            fluentWait().until(driver -> "complete".equalsIgnoreCase((String) getDriver()
                                                    .executeScript("return document.readyState")));
        }
        catch (final Exception e)
        {
            log.error("Error waiting for page load", e);
        }
    }

}