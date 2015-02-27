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

import org.mutable.samples.Employees;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mutable.FieldBuilder.aField;
import static org.mutable.FieldType.INTEGER;
import static org.mutable.FieldType.STRING;
import static org.mutable.SchemaBuilder.aSchema;
import static org.mutable.expression.FieldReference.field;
import static org.mutable.expression.Literal.value;

@RunWith(JUnit4.class)
public class DataTableTest {

    @Test
    public void shouldExposeTheSourceSchema() {
        Schema schema = aSchema()
                .with(aField("name").ofType(STRING))
                .with(aField("age").ofType(INTEGER))
                .build();

        DataTable table = new DataTable(schema);

        assertThat(table.getSchema(), is(equalTo(schema)));
    }
    
    
    @Test
    public void shouldTellWhenItIsEmpty() {
        final DataTable table = Employees.getEmptyTable();
        
        assertThat(table.isEmpty(), is(true));
    }

    @Test
    public void shouldExposeTheColumnCount() {
        final DataTable table = Employees.getTable();

        assertThat(table.getColumnCount(), is(equalTo(4)));
    }

    @Test
    public void shouldExposeTheRowCount() {
        final DataTable table = Employees.getTable();

        assertThat(table.getRowCount(), is(equalTo(3)));
    }

    @Test
    public void shouldExposeTheDataCount() {
        final DataTable table = Employees.getTable();

        assertThat(table.getDataCount(), is(equalTo(12)));
    }

    @Test
    public void shouldExposeSingleDataValues() {
        final DataTable table = Employees.getTable();

        final Object expectation = 25;
        assertThat(table.getData(1, 2), is(equalTo(expectation)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeDataOnRowsThatDoNotExist() {
        final DataTable table = Employees.getTable();

        final int row = 34;
        final int column = 1;

        table.getData(row, column);
    }

    @Test
    public void shouldExposeDataByFieldName() {
        final DataTable table = Employees.getTable();

        final int row = 1;
        final String column = "name";

        assertThat(table.getData(row, column), is(equalTo("bob")));
    }


    @Test
    public void shouldPermitToAppendNewRows() {
        DataTable table = Employees.getTable();
        table.appendRow(new Object[]{"Steven", 35, false, 345.5});

        assertThat(table.getRowCount(), is(equalTo(4)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectAppendingNullRows() {
        DataTable table = Employees.getTable();
        table.appendRow(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectAppendingRowsWithMissingValues() {
        DataTable table = Employees.getTable();
        table.appendRow(new Object[]{"Steven", 35, 345.5}); // Should have 4 fields
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDetectAppendingRowsWithWrongTypes() {
        DataTable table = Employees.getTable();
        table.appendRow(new Object[]{"Steven", 35, "should be a boolean", 345.5});
    }

    @Test
    public void appendingASingleIllegalRowShouldNotChangeTheTable() {
        DataTable table = Employees.getTable();
        final int dataCount = table.getDataCount();

        try {
            table.appendRow(new Object[]{"Steven", 35, "error: value should be a boolean", 345.5});

        } catch (IllegalArgumentException iae) {
            assertThat(table.getDataCount(), is(equalTo(dataCount)));
        }
    }

    @Test
    public void appendingRowsWhereTheLastOneIsInvalidShouldNotChangeTheTable() {
        DataTable table = Employees.getTable();
        final int dataCount = table.getDataCount();

        try {
            table.appendRows(
                    new Object[][]{
                        {"Brandon", 32, true, 123.4},
                        {"Steven", 35, "should be a boolean", 345.5}
                    }
            );
            fail("Expecting IllegalArgumentException");

        } catch (IllegalArgumentException iae) {
            assertThat(table.getDataCount(), is(equalTo(dataCount)));
        }
    }

// TODO: refactor test getRow()
//    @Test
//    public void shouldExposeSingleRowsAsArrays() {
//        DataTable table = makeSampleTable(fromValidRawData());
//        Object[] row = table.getRow(1);
//
//        assertThat(row, is(equalTo(fromValidRawData()[0])));
//    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeRowsThatDoNotExist() {
        DataTable table = Employees.getTable();
        table.getRow(134);
    }

    
    @Test
    public void shouldResolveQuery() {
        DataTable table = Employees.getTable();
        ResultSet results = table.where(field("name").is(value("derek")));
        assertThat(results.getRowCount(), is(equalTo(1)));
    }
    
    
    @Test
    public void shouldProvideCursors() {
        DataTable table = Employees.getTable();
        Cursor cursor = table.newCursor();
        
        assertThat(cursor.getRowIndex(), is(equalTo(1)));
    }
    
     @Test
    public void shouldBeIterable() {
        List<String> names = new ArrayList<>();
     
        for(Row eachRow: Employees.getTable()) {
            names.add((String) eachRow.getField("name"));
        }
        
        assertThat(names, hasItems("bob", "john", "derek"));
    }

}
