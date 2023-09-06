package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.TextInputImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElement
 */
@ImplementedBy(TextInputImpl.class)
public interface TextInput extends ExtWebElement {
    /**
     * @see org.openqa.selenium.WebElement#clear()
     */
    @Override
    public void clear();

    /**
     * @param text - The text to type into the field.
     */
    void set(String text);

    /**
     * Method to set text to the {@link WebElement}
     *
     * @param keysToSend text to set
     * @see TextInputImpl#setKeybyKey(String)
     */
    void setKeybyKey(String keysToSend);

    /**
     * Method to set text to the {@link WebElement}
     *
     * @param keysToSend text to set
     * @see TextInputImpl#setandRefresh(String)
     */
    void setandRefresh(String keysToSend);

    /**
     * Method to set text to the {@link WebElement}
     *
     * @param keysToSend text to set
     * @param  timegap time gap between each key stroke in millis
     * @see TextInputImpl#setKeybyKey(long, String)
     */
    void setKeybyKey(long timegap,String keysToSend);

    /**
     * @param text - The text to type into the field.
     */
    void scrollAndSet(String text);

    /**
     * @see org.openqa.selenium.WebElement#getText()
     */
    @Override
    public String getText();
}
