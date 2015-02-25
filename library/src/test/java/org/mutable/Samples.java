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
 * A set of sample data table for testing purposes
 */
public class Samples {

    public static Schema employeeSchema() {
        String[] fieldNames = new String[]{"name", "age", "isMarried", "salary"};
        Schema schema = Schema.inferedFrom(fieldNames, employeesData()[0]);
        return schema;
    }

    public static Object[][] employeesData() {
        return new Object[][]{
            {"bob", 25, true, 23.54},
            {"john", 34, false, 34.45},
            {"derek", 56, false, 67.34}
        };
    }

    public static DataTable emptyEmployeeTable() {
        Schema schema = employeeSchema();
        DataTable table = new DataTable(schema);
        return table;
    }

    public static DataTable employeeTable() {
        DataTable table = emptyEmployeeTable();
        table.appendRows(employeesData());
        return table;
    }

}
