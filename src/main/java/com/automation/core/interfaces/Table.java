package com.automation.core.interfaces;

import com.automation.core.annotation.ImplementedBy;
import com.automation.core.base.ExtWebElement;
import com.automation.core.interfaces.impl.TableImpl;
import org.openqa.selenium.WebElement;

/**
 * Wraps default {@link WebElement} with more custom methods
 *
 * @see ExtWebElement
 */
@ImplementedBy(TableImpl.class)
public interface Table extends ExtWebElement {
    /**
     * Get the row count of the Webtable
     */
    int getRowCount();

    /**
     * Get the column count for the Webtable on a specified Row
     */
    int getColumnCount(int row);

    /**
     * Get cell data of the specified row and Column in a Webtable
     */
    String getCellData(int row, int column);

    /**
     * Return the Cell of the specified row and Column in a Webtable
     */
    ExtWebElement getCell(int row, int column);

    /**
     * Click cell in the specified row and Column in a Webtable
     */
    void clickCell(int row, int column);

}
