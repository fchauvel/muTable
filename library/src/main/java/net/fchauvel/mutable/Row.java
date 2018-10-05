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

/**
 * Behavior of a row in a table
 */
public interface Row {

    /**
     * @return the value contained is a specific field/column of this row
     * @param fieldIndex the position of the field starting at 1
     */
    Object getField(int fieldIndex);

    /**
     * @return the value contained is a specific field/column of this row
     * @param fieldName the name of the field
     */
    Object getField(String fieldName);

    /**
     * @return the value contained is a specific field/column of this row,
     * casted as an int
     * @param fieldName the name of the field
     */
    int getInteger(String fieldName);

    /**
     * @return the value contained is a specific field/column of this row casted
     * as an int
     * @param fieldIndex the position of the field starting at 1
     */
    int getInteger(int fieldIndex);

    /**
     * @return the value contained is a specific field/column of this row,
     * casted as a long
     * @param fieldName the name of the field
     */
    long getLong(String fieldName);

    /**
     * @return the value contained is a specific field/column of this row casted
     * as a long
     * @param fieldIndex the position of the field starting at 1
     */
    long getLong(int fieldIndex);

    /**
     * @return the value contained is a specific field/column of this row,
     * casted as a double
     * @param fieldName the name of the field
     */
    double getDouble(String fieldName);

    /**
     * @return the value contained is a specific field/column of this row casted
     * as a double
     * @param fieldIndex the position of the field starting at 1
     */
    double getDouble(int fieldIndex);

    /**
     * @return the value contained is a specific field/column of this row,
     * casted as a boolean
     * @param fieldName the name of the field
     */
    float getFloat(String fieldName);

    /**
     * @return the value contained is a specific field/column of this row casted
     * as a float
     * @param fieldIndex the position of the field starting at 1
     */
    float getFloat(int fieldIndex);

    /**
     * @return the value contained is a specific field/column of this row,
     * casted as a boolean
     * @param fieldName the name of the field
     */
    boolean getBoolean(String fieldName);

    /**
     * @return the value contained is a specific field/column of this row casted
     * as a boolean
     * @param fieldIndex the position of the field starting at 1
     */
    boolean getBoolean(int fieldIndex);

    /**
     * @return the index of this row in the table, starting at 1
     */
    int getRowIndex();

}
