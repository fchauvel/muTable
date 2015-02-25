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

import java.util.ArrayList;
import java.util.List;

/**
 * The table that contains data elements
 */
public class DataTable extends AbstractTable {

    private final Schema schema;
    private final List<List<Object>> data;

    /**
     * Build a new table, where fields are initialized according the given data
     * schema
     *
     * @param schema the data schema governing the fields of this table
     */
    public DataTable(Schema schema) {
        this.schema = schema;
        this.data = new ArrayList<>(schema.getFields().size());
        for (Field eachField : schema.getFields()) {
            this.data.add(new ArrayList<>());
        }
    }

    /**
     * @return the data schema that defines the fields available in this table
     */
    @Override
    public Schema getSchema() {
        return this.schema;
    }

    /**
     * @return the data at the specified position
     * @param rowIndex the row number, from 1 to n
     * @param columnIndex the column index, from 1 to n
     */
    @Override
    public Object getData(int rowIndex, int columnIndex) {
        requireValidColumnIndex(columnIndex);
        requireValidRowIndex(rowIndex);

        return this.data.get(columnIndex - 1).get(rowIndex - 1);
    }

    /**
     * @return the data contained at the given position
     * @param rowIndex the index of the row to query
     * @param fieldName the name of the field of interest
     */
    @Override
    public Object getData(int rowIndex, String fieldName) {
        requireValidFieldName(fieldName);

        return getData(rowIndex, schema.getFieldIndex(fieldName));
    }

    /**
     * @return a the selected row
     * @param rowIndex the index of the row to be fetched
     */
    @Override
    public Row getRow(int rowIndex) {
        requireValidRowIndex(rowIndex);

        return new Cursor(this, rowIndex);
    }

    /**
     * Check the validity of the given field name
     */
    private void requireValidFieldName(String fieldName) throws IllegalArgumentException {
        if (!schema.hasFieldNamed(fieldName)) {
            final String error = String.format("Unknown field name '%s' (fields are %s)", fieldName, schema.getFieldNames());
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Check the validity of the given row index, w.r.t. the row count
     */
    private void requireValidRowIndex(int rowIndex) throws IllegalArgumentException {
        if (rowIndex <= 0 || rowIndex > getRowCount()) {
            String error = String.format("Invalid row index '%d' (should be within [1, %d])", rowIndex, getRowCount());
            if (getRowCount() == 0) {
                error = "Invalid row index '%d' (empty table)";
            }
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Check the validity of the given column index, w.r.t. the row count
     */
    private void requireValidColumnIndex(int columnIndex) throws IllegalArgumentException {
        if (columnIndex <= 0 || columnIndex > getColumnCount()) {
            String error = String.format("Invalid column index '%d' (should be within [1, %d])", columnIndex, getColumnCount());
            if (getColumnCount() == 0) {
                error = "Invalid column index '%d' (empty table)";
            }
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Append several rows at the end of this table.
     *
     * @param rows the rows to append to this table
     * @throws IllegalArgumentException when at least one row is does not match
     * the table schema
     */
    public final void appendRows(Object[][] rows) throws IllegalArgumentException {
        final int firstNewRowIndex = getRowCount();
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            try {
                appendRow(rows[rowIndex]);

            } catch (IllegalArgumentException iae) {
                removeRows(firstNewRowIndex, firstNewRowIndex + rowIndex);

                final String error = String.format("Invalid row #%d.", rowIndex);
                throw new IllegalArgumentException(error, iae);
            }
        }
    }

    /**
     * Remove a range of row. Both bounds of the range are included.
     *
     * @param startIndex the first row to remove
     * @param endIndex the last row to remove
     */
    public void removeRows(final int startIndex, int endIndex) {
        for (int i = endIndex; i >= startIndex; i--) {
            removeRow(i);
        }
    }

    /**
     * @return the number of column in this table
     */
    @Override
    public int getColumnCount() {
        return this.data.size();
    }

    /**
     * @return the number of rows in this table
     */
    @Override
    public int getRowCount() {
        return this.data.get(0).size();
    }

    /**
     * Append a new row of data at the end of the table
     *
     * @param row an ordered array of data to be append in each column
     */
    public void appendRow(Object[] row) {
        rejectInvalidRow(row);

        final int newRowIndex = getRowCount();
        for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
            final Object cell = row[columnIndex];
            final FieldType actualType = FieldType.of(cell);
            final FieldType expectedType = schema.getField(columnIndex + 1).getType();
            if (actualType != expectedType) {
                removeRow(newRowIndex);
                final String error = String.format("Unexpected type '%s' in column %d (expecting type '%s')", expectedType.name(), columnIndex, actualType.name());
                throw new IllegalArgumentException(error);

            } else {
                this.data.get(columnIndex).add(row[columnIndex]);
            }
        }
    }

    private void rejectInvalidRow(Object[] row) throws IllegalArgumentException {
        if (row == null) {
            throw new IllegalArgumentException("Illegal row ('null' found)");
        }
        if (row.length < schema.getFields().size()) {
            final String error = String.format("Missing values (expected %d values but only %d were found)", schema.getFields().size(), row.length);
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Remove a single row, whose index is given
     *
     * @param rowIndex the index of the row to remove
     */
    public void removeRow(final int rowIndex) {
        assert rowIndex >= 0 : "Invalid row index, expected positive value (found '" + rowIndex + "')";

        for (List<Object> eachColumn : this.data) {
            if (eachColumn.size() > rowIndex) {
                eachColumn.remove(rowIndex);
            }
        }
    }


}
