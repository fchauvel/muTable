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

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.mutable.FieldType.*;

@RunWith(JUnit4.class)
public class SchemaTest {

    @Test
    public void shouldExposeItsFields() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);

        assertThat(schema.getFields(), hasItems(fields));
    }

    @Test
    public void shouldExposeTheFieldCount() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);

        assertThat(schema.getFieldCount(), is(equalTo(2)));

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectBeingEmpty() {
        List<Field> fields = null;
        new Schema(fields);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectHavingANullField() {
        final Field[] fields = new Field[]{new Field("f1"), null, new Field("f2")};
        final Schema schema = new Schema(fields);

        assertThat(schema.getFields(), hasItems(fields));
    }
    
    @Test
    public void shouldAllowCheckingForTheExistenceOfAField() {
        final Schema schema = sampleSchema();

        assertThat(schema.hasFieldNamed("f1"), is(true));
        assertThat(schema.hasFieldNamed("no such field"), is(false));
    }
    
    @Test
    public void shouldExposeTheNamesOfAvailableFields() {
        final Schema schema = sampleSchema();

        assertThat(schema.getFieldNames(), hasItems("f1", "f2"));
    }
    
    @Test
    public void shouldExposeIndexOfField() {
        final Schema schema = sampleSchema();
    
        assertThat(schema.getFieldIndex("f1"), is(equalTo(1)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeIndexForFieldThatDoNotExist() {
        final Schema schema = sampleSchema();
    
        schema.getFieldIndex("field that does not exist");
    }
    

    @Test
    public void shouldExposeFieldByIndex() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);

        assertThat(schema.getField(2), is(equalTo(fields[1])));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectTooLowIndices() {
        Schema schema = sampleSchema();

        schema.getField(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectTooLargeIndices() {
        Schema schema = sampleSchema();

        schema.getField(3);
    }

    @Test
    public void shouldExposeFieldsByName() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);

        assertThat(schema.getField("f1"), is(equalTo(fields[0])));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectFieldNamesThatDoNotExist() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);

        schema.getField("unknown field");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullFieldNames() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);

        schema.getField(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectFieldsWithDuplicatedNames() {
        final Field[] fields = new Field[]{
            new Field("f1"),
            new Field("f2"),
            new Field("f1") // Duplicated name
        };

        new Schema(fields);
    }

    @Test
    public void equalsShouldDetectDifferentFieldsList() {
        final Schema schemaA = new Schema(new Field[]{
            new Field("name", STRING),
            new Field("age", DOUBLE)
        });

        final Schema schemaB = new Schema(new Field[]{
            new Field("name", STRING),
            new Field("age", INTEGER)
        });

        assertThat(schemaA.equals(schemaB), is(false));
    }

    @Test
    public void equalsShouldDetectEquivalentFieldsList() {
        final Schema schemaA = new Schema(new Field[]{
            new Field("name", STRING),
            new Field("age", DOUBLE)
        });

        final Schema schemaB = new Schema(new Field[]{
            new Field("name", STRING),
            new Field("age", DOUBLE)
        });

        assertThat(schemaA.equals(schemaB), is(true));
    }

    @Test
    public void shouldBeInferedFromObjectArrays() {

        Schema schema = Schema.inferedFrom("bob", 34, true, 45.6);

        assertThat(schema.getField(1).getType(), is(equalTo(STRING)));
        assertThat(schema.getField(2).getType(), is(equalTo(INTEGER)));
        assertThat(schema.getField(3).getType(), is(equalTo(BOOLEAN)));
        assertThat(schema.getField(4).getType(), is(equalTo(DOUBLE)));
    }

    @Test
    public void shouldBeInferedFromObjectArraysWithNames() {
        String columnNames[] = new String[]{"name", "age", "married", "salary"};
        Object values[] = new Object[]{"bob", 34, true, 45.6};

        Schema expected = new Schema(
                new Field[]{
                    new Field("name", STRING),
                    new Field("age", INTEGER),
                    new Field("married", BOOLEAN),
                    new Field("salary", DOUBLE)
                }
        );

        Schema actual = Schema.inferedFrom(columnNames, values);

        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shemaInferenceShouldRejectInconsistentInputs() {
        String columnNames[] = new String[]{"name", "age", "salary"};
        Object values[] = new Object[]{"bob", 34, true, 45.6};
        Schema.inferedFrom(columnNames, values);
    }

    private static Field[] sampleFieldList() {
        return new Field[]{new Field("f1"), new Field("f2")};
    }

    private Schema sampleSchema() {
        final Field[] fields = sampleFieldList();
        final Schema schema = new Schema(fields);
        return schema;
    }

}
