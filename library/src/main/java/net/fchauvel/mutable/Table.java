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
package net.fchauvel.mutable;

import net.fchauvel.mutable.expression.Expression;

/**
 * Behavior of table
 */
public interface Table extends Iterable<Row> {

    /**
     * @return true if the table does not contain any row
     */
    boolean isEmpty();
    
    /**
     * @return the number of column in this table
     */
    int getColumnCount();

    /**
     * @return the data at the specified position
     * @param rowIndex the row number, from 1 to n
     * @param columnIndex the column index, from 1 to n
     */
    Object getData(int rowIndex, int columnIndex);

    /**
     * @return the data contained at the given position
     * @param rowIndex the index of the row to query
     * @param fieldName the name of the field of interest
     */
    Object getData(int rowIndex, String fieldName);

    /**
     * @return the number of data (i.e., values or cells) in this table
     */
    int getDataCount();

    /**
     * @return an array of object representing the given row
     * @param rowIndex the index of the row to be fetched
     */
    Row getRow(int rowIndex);

    /**
     * @return the number of rows in this table
     */
    int getRowCount();

    /**
     * @return the data schema that defines the fields available in this table
     */
    Schema getSchema();

    /**
     * Select the rows where the given criteria holds
     *
     * @param criteria the predicate that must be satisfied
     * @return a result set, potentially empty, containing the results
     */
    ResultSet where(Expression criteria);

    /**
     * @return a new cursor pointing on the first row of this table
     */
    Cursor newCursor();

}
