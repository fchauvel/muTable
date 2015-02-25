/**
 * This file is part of MuTable.
 *
 * Copyright (C) 2015 Franck Chauvel <franck.chauvel@gmail.com>
 *
 * MuTable is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mutable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MuTable.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mutable;

/**
 * Cursor let the user iterate over a table
 */
public class Cursor implements Row {

    private final Table source;
    private int index;

    public Cursor(Cursor other) {
        this(other.source, other.index);
    }

    public Cursor(Table source, int index) {
        assert source != null : "Cannot build a cursor with 'null' as source";
        assert index <= source.getRowCount() && index >= 0 : "Invalid cursor index " + index + " (should be with [1, " + source.getRowCount() + "])";

        this.source = source;
        this.index = index;
    }

    @Override
    public Object getField(int fieldIndex) {
        return source.getData(index, fieldIndex);
    }

    @Override
    public Object getField(String fieldName) {
        if(index == 0) {
            throw new IllegalStateException("The cursor is crappy");
        } 
        return source.getData(index, fieldName);
    }

    @Override
    public int getRowIndex() {
        return this.index;
    }

    /**
     * @return true if there exists a next row
     */
    public boolean hasNext() {
        return index < source.getRowCount();
    }

    /**
     * Move the cursor to the next row, if any
     */
    public void moveToNext() {
        if (!hasNext()) {
            throw new IllegalStateException("No next row");
        }
        this.index++;
    }

    /**
     * @return true is there exists a previous row
     */
    public boolean hasPrevious() {
        return index > 1;
    }

    /**
     * Move the cursor to the previous row, if any
     */
    public void moveToPrevious() {
        if (!hasPrevious()) {
            throw new IllegalStateException("No previous row");
        }
        this.index--;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(index).append(": (");
        for(String eachField: source.getSchema().getFieldNames()) {
            buffer.append(getField(eachField));
            buffer.append(",");
        }
        buffer.append(")");
        return buffer.toString();
    }

}
