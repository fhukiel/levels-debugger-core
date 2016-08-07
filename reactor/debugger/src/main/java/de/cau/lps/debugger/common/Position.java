package de.cau.lps.debugger.common;

/**
 * Encapsulates a source code {@link Position}, wrapping the line number and row number.
 * 
 * @author Thomas Ulrich
 *
 */
public class Position {

    private int line;
    private int row;

    /**
     * Initializes a new instance of the Position class. All values are set to 0.
     */
    public Position() {
        this.line = 0;
        this.row = 0;
    }

    /**
     * Initializes a new instance of the Position class.
     * 
     * @param line
     *            The line number.
     * @param row
     *            The row number.
     */
    public Position(int line, int row) {
        this.line = line;
        this.row = row;
    }

    /**
     * Initializes a new instance of the Position class.
     * 
     * @param other
     *            Another Position to create a copy of.
     */
    public Position(Position other) {
        this.line = other.getLine();
        this.row = other.getRow();
    }

    /**
     * Gets this Position's line number.
     * 
     * @return The line number.
     */
    public int getLine() {
        return line;
    }

    /**
     * Gets this Position's row number.
     * 
     * @return The row number.
     */
    public int getRow() {
        return row;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + line;
        result = prime * result + row;
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Position other = (Position) obj;
        if (line != other.line) {
            return false;
        }
        if (row != other.row) {
            return false;
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Position [line=" + line + ", row=" + row + "]";
    }
}
