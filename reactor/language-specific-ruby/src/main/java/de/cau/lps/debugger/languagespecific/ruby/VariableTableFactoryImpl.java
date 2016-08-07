package de.cau.lps.debugger.languagespecific.ruby;

import de.cau.lps.debugger.languagespecific.api.AbstractVariableTableFactory;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * Ruby implementation of the {@link AbstractVariableTableFactory}.
 * 
 * @author Thomas Ulrich
 */
public class VariableTableFactoryImpl extends AbstractVariableTableFactory {

    /*
     * (non-Javadoc)
     * 
     * @see de.cau.lps.debugger.languagespecific.api.AbstractVariableTableFactory#create()
     */
    @Override
    public VariableTable create() {
        return new VariableTableImpl();
    }
}
