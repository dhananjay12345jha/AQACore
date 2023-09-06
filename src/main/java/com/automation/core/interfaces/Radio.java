package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.RadioImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElement
 */
@ImplementedBy(RadioImpl.class)
public interface Radio extends ExtWebElement {

    /**
     * Select the radio button
     */
    void select();

    /**
     * Select the radio button with javascript
     */
    void jsSelect();

    /**
     * Check if an element is selected, and return boolean.
     *
     * @return true if selected, return false in other case
     */
    boolean isSelected();


}
