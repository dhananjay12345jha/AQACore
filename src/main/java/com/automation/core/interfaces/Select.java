package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.SelectImpl;

import java.util.List;


@ImplementedBy(SelectImpl.class)
public interface Select extends ExtWebElement {


    boolean isMultiple();

    void deselectByIndex(int index);

    void selectByValue(String value);
    ExtWebElement getFirstSelectedOption();

    void selectByVisibleText(String text);

    void deselectByValue(String value);

    void deselectAll();

    List<ExtWebElement> getAllSelectedOptions();

    List<ExtWebElement> getOptions();

    void deselectByVisibleText(String text);

    void selectByIndex(int index);
}
