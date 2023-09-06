package com.automation.core.interfaces.impl;

import com.automation.core.base.ExtWebElement;
import com.automation.core.base.ExtWebElementImpl;
import com.automation.core.interfaces.Table;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElementImpl
 * @see Table
 */
public class TableImpl extends ExtWebElementImpl implements Table {

    private final WebDriver driver;
    private final ElementLocator locator;
    private final By by;

    /**
     * Creates a Element for a given WebElement.
     *
     * @param driver  webdriver
     * @param element Webelement to wrap up
     * @param by      By locator
     * @param locator element locator
     */
    public TableImpl(final WebDriver driver, final WebElement element, final By by, final ElementLocator locator) {
        super(driver, element, by, locator);
        this.driver = driver;
        this.by = by;
        this.locator = locator;
    }

    private List<ExtWebElement> getRows() {
        return getWrappedElement().findElements(By.xpath("tr|tbody/tr")).stream()
                .map(ele -> new ExtWebElementImpl(driver, ele, by, locator))
                .collect(Collectors.toList());
    }

    private List<ExtWebElement> getColumns(final ExtWebElement row) {
        return row.findAllBy(By.xpath("th|td"));
    }

    @Override
    public int getRowCount() {
        return getRows().size();
    }

    @Override
    public int getColumnCount(final int row) {
        return getColumns(getRows().get(row)).size();
    }

    @Override
    public String getCellData(final int row, final int column) {
        return getCell(row, column).getText();
    }

    @Override
    public ExtWebElement getCell(final int row, final int column) {
        return getColumns(getRows().get(row)).get(column);
    }

    @Override
    public void clickCell(final int row, final int column) {
        getCell(row, column).waitUntilClickable().click();
    }

}
