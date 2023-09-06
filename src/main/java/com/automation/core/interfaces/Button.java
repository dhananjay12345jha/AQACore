package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.ButtonImpl;

@ImplementedBy(ButtonImpl.class)
public interface Button extends ExtWebElement {


    @Override
    void click();

    @Override
    void jsClick();

    @Override
    ExtWebElement waitUntilClickable();

    @Override
    ExtWebElement waitUntilClickable(long timeOutInSeconds);
}
