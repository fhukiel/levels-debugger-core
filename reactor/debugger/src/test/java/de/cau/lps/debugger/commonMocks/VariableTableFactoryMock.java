package de.cau.lps.debugger.commonMocks;

import de.cau.lps.debugger.languagespecific.api.AbstractVariableTableFactory;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * Mock implementation of {@link AbstractVariableTableFactory}.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableFactoryMock extends AbstractVariableTableFactory {

    @Override
    public VariableTable create() {
        return new VariableTableMock();
    }
}
