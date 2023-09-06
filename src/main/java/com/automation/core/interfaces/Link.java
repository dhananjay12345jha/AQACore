package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.LinkImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElement
 */
@ImplementedBy(LinkImpl.class)
public interface Link extends ExtWebElement {

    /**
     * Click the button using the default Selenium click
     */
    @Override
    public void click();

    /**
     * Click the link using a JavascriptExecutor click
     */
    @Override
    public void jsClick();

    /**
     * get herf attribute for the link
     */
    public String getURL();
}
