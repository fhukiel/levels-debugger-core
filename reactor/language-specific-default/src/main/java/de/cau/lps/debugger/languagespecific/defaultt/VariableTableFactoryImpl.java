package de.cau.lps.debugger.languagespecific.defaultt;

import de.cau.lps.debugger.languagespecific.api.AbstractVariableTableFactory;
import de.cau.lps.debugger.languagespecific.api.VariableTable;

/**
 * Creates new {@link VariableTableImpl}s.
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
