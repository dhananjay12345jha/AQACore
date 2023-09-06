package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.CheckBoxImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElement
 */
@ImplementedBy(CheckBoxImpl.class)
public interface CheckBox extends ExtWebElement {

    /**
     * Toggle the state of the checkbox.
     */
    void toggle();

    /**
     * Toggle the state of the checkbox using JavascriptExecutor
     */
    void jsToggle();

    /**
     * Checks checkbox if unchecked.
     */
    void check();

    /**
     * Un-checks checkbox if checked.
     */
    void uncheck();

    /**
     * Check if an element is selected, and return boolean.
     *
     * @return true if check is checked, return false in other case
     */
    boolean isChecked();

}
