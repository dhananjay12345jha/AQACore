package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.LabelImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElement
 */
@ImplementedBy(LabelImpl.class)
public interface Label extends ExtWebElement {

    /**
     * Gets the for attribute on the label.
     *
     * @return string containing value of the for attribute, null if empty.
     */
    String getFor();
}
