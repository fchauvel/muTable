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
 * Cursor: A movable pointer to a specific row in a table
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
        requireValidIndex();
        return source.getData(index, fieldIndex);
    }

    private void requireValidIndex() throws IllegalStateException {
        if (index == 0) {
            throw new IllegalStateException("Invalid cursor index (index = 0)");
        }
    }

    @Override
    public Object getField(String fieldName) {
        requireValidIndex();
        return source.getData(index, fieldName);
    }

    @Override
    public int getInteger(String fieldName) {
        return getFieldAs(Integer.class, fieldName);
    }

    @Override
    public int getInteger(int fieldIndex) {
        return getFieldAs(Integer.class, fieldIndex);
    }

    @Override
    public long getLong(String fieldName) {
        return getFieldAs(Long.class, fieldName);
    }

    @Override
    public long getLong(int fieldIndex) {
        return getFieldAs(Long.class, fieldIndex);
    }

    @Override
    public float getFloat(String fieldName) {
        return getFieldAs(Float.class, fieldName);
    }

    @Override
    public float getFloat(int fieldIndex) {
        return getFieldAs(Float.class, fieldIndex);
    }

    @Override
    public double getDouble(String fieldName) {
        return getFieldAs(Double.class, fieldName);
    }

    @Override
    public double getDouble(int fieldIndex) {
        return getFieldAs(Double.class, fieldIndex);
    }

    @Override
    public boolean getBoolean(String fieldName) {
        return getFieldAs(Boolean.class, fieldName);
    }

    @Override
    public boolean getBoolean(int fieldIndex) {
        return getFieldAs(Boolean.class, fieldIndex);
    }

    /**
     * @return the value of the selected field, casted in the given type
     * @param <T> the type in which the value should be casted
     * @param type the class that represent the type Java
     * @param fieldIndex the index of the field of interest in [1, n]
     */
    private <T> T getFieldAs(Class<T> type, int fieldIndex) {
        assert type != null : "Cannot cast an object to the 'null' type";

        final Object value = getField(fieldIndex);
        try {
            return type.cast(value);
            
        } catch (ClassCastException cce) {
            final FieldType expectedType = source.getSchema().getField(fieldIndex).getType();
            final String error = String.format("Type conversion error for field no. %d (from '%s' to '%s')", fieldIndex, expectedType.getClassName(), type.getName());
            throw new IllegalArgumentException(error, cce);
        
        }
    }

    /**
     * @return the value of the selected field, casted in the given type
     * @param <T> the type in which the value should be casted
     * @param type the class that represent the type Java
     * @param fieldName the name of the field of interest
     */
    private <T> T getFieldAs(Class<T> type, String fieldName) {
        return getFieldAs(type, source.getSchema().getFieldIndex(fieldName));
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
        for (String eachField : source.getSchema().getFieldNames()) {
            buffer.append(getField(eachField));
            buffer.append(",");
        }
        buffer.append(")");
        return buffer.toString();
    }

}
