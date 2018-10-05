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
package net.fchauvel.mutable.samples;

import net.fchauvel.mutable.DataTable;
import net.fchauvel.mutable.Schema;

/**
 * A set of sample data table for testing purposes
 */
public class Employees {

    public static Schema getSchema() {
        String[] fieldNames = new String[]{"name", "age", "isMarried", "salary"};
        Schema schema = Schema.inferedFrom(fieldNames, getRawData()[0]);
        return schema;
    }
    
    public static Schema getGeneratedSchema() {
        Schema schema = Schema.inferedFrom(getRawData()[0]);
        return schema;
    }

    public static Object[][] getRawData() {
        return new Object[][]{
            {"bob", 25, true, 23.54},
            {"john", 34, false, 34.45},
            {"derek", 56, false, 67.34}
        };
    }

    public static DataTable getEmptyTable() {
        Schema schema = getSchema();
        DataTable table = new DataTable(schema);
        return table;
    }

    public static DataTable getTable() {
        DataTable table = getEmptyTable();
        table.appendRows(getRawData());
        return table;
    }

    public static String getCsvWithHeader() {
        return "name,age,isMarried,salary" + EOL
                + getCsvWithoutHeader();
    }
    
    public static String getCsvWithoutHeader() {
        return "bob,25,true,23.54" + EOL
                + "john,34,false,34.45" + EOL
                + "derek,56,false,67.34" + EOL;
    }

    private static final String EOL = System.lineSeparator();

}
