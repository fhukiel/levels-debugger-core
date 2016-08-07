package de.cau.lps.debugger.commontest;

import org.junit.Assert;
import org.junit.Test;

import de.cau.lps.debugger.common.Position;

/**
 * Tests the {@link Position} class.
 * 
 * @author Thomas Ulrich
 */
public class PositionTest {

    /**
     * Tests the constructor.
     */
    @Test
    public void ctor() {
        int line = 1;
        int row = 2;
        Position pos = new Position(line, row);

        Assert.assertNotNull(pos);
        Assert.assertEquals(line, pos.getLine());
        Assert.assertEquals(row, pos.getRow());
    }

    /**
     * Tests the equals method.
     */
    @Test
    public void equals() {
        Position pos = new Position(1, 2);

        // Assert is equal to self
        Assert.assertTrue(pos.equals(pos));

        // Assert not equal to null
        Assert.assertFalse(pos.equals(null));

        // Assert not equal to other class
        Assert.assertFalse(pos.equals("test"));

        // Assert not equal if line number is different
        Assert.assertFalse(pos.equals(new Position(2, 2)));

        // Assert not equal if row number is different
        Assert.assertFalse(pos.equals(new Position(1, 1)));

        // Assert equals to other position object with identical line/row number
        Assert.assertTrue(pos.equals(new Position(1, 2)));
    }
}
