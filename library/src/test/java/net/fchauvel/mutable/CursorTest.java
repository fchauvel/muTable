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

import net.fchauvel.mutable.Table;
import net.fchauvel.mutable.Cursor;
import net.fchauvel.mutable.samples.Employees;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Cursor specification
 */
@RunWith(JUnit4.class)
public class CursorTest {

    private Cursor cursor;

    public CursorTest() {
        Table table = Employees.getTable();
        cursor = new Cursor(table, 0);
        cursor.moveToNext();
    }

    @Test
    public void shouldBeDuplicable() {
        Cursor copy = new Cursor(cursor);
        assertThat(copy.getRowIndex(), is(equalTo(cursor.getRowIndex())));
    }

    // TODO: Test comparison of cursors
    @Test
    public void shouldExposeItsPosition() {
        assertThat(cursor.getRowIndex(), is(equalTo(1)));
    }

    @Test
    public void shouldExposeDataByFieldName() {
        assertThat(cursor.getField("name"), is(equalTo("bob")));
    }

    @Test
    public void shouldExposeValueAsDoubleIfTypeMatches() {
        assertThat(cursor.getDouble("salary"), is(equalTo(23.54)));
    }

    @Test
    public void shouldExposeValueByIndexAsDoubleIfTypeMatches() {
        assertThat(cursor.getDouble(4), is(equalTo(23.54)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeValueAsDoubleIfTypeDoesNotMatch() {
        cursor.getDouble("isMarried");
    }

    /* Unable to cast double to float
    @Test
    public void shouldExposeValueAsFloatIfTypeMatches() {
        assertThat(cursor.getFloat("salary"), is(equalTo(23.54)));
    }

    @Test
    public void shouldExposeValueByIndexAsFloatIfTypeMatches() {
        assertThat(cursor.getFloat(4), is(equalTo(23.54)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeValueAsFloatIfTypeDoesNotMatch() {
        cursor.getFloat("isMarried");
    }
    */

    @Test
    public void shouldExposeValueAsBooleanIfTypeMatches() {
        assertThat(cursor.getBoolean("isMarried"), is(true));
    }

    @Test
    public void shouldExposeValueByIndexAsBooleanIfTypeMatches() {
        assertThat(cursor.getBoolean(3), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeValueAsBooleanIfTypeDoesNotMatch() {
        cursor.getBoolean("salary");
    }

    @Test
    public void shouldExposeValueAsIntegerIfTypeMatches() {
        assertThat(cursor.getInteger("age"), is(equalTo(25)));
    }

    @Test
    public void shouldExposeValueByIndexAsIntegerIfTypeMatches() {
        assertThat(cursor.getInteger(2), is(equalTo(25)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeValueAsIntegerIfTypeDoesNotMatch() {
        cursor.getInteger("name");
    }
    
    /*
    @Test
    public void shouldExposeValueAsLongIfTypeMatches() {
        assertThat(cursor.getLong("age"), is(equalTo(25)));
    }

    @Test
    public void shouldExposeValueByIndexAsLongIfTypeMatches() {
        assertThat(cursor.getLong(1), is(equalTo(25)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotExposeValueAsLongIfTypeDoesNotMatch() {
        cursor.getLong("isMarried");
    }
    */

    @Test
    public void shouldExposeDataByFieldIndex() {
        assertThat(cursor.getField(1), is(equalTo("bob")));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotExposeDataWhenThereIsNone() {
        cursor = new Cursor(Employees.getEmptyTable(), 0);
        cursor.getField("name");
    }

    @Test
    public void shouldAllowMovingToTheNextRow() {
        cursor.moveToNext();
        assertThat(cursor.getRowIndex(), is(equalTo(2)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventMovingBeyondTheLastRow() {
        cursor.moveToNext();
        cursor.moveToNext();
        cursor.moveToNext();
    }

    @Test
    public void shouldAllowMovingToThePreviousRow() {
        cursor.moveToNext();
        cursor.moveToPrevious();
        assertThat(cursor.getRowIndex(), is(equalTo(1)));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldPreventMovingBeyondTheFirstRow() {
        cursor.moveToPrevious();
    }

    @Test
    public void shouldPermitCheckingForTheExistenceOfANextRow() {
        assertThat(cursor.hasNext(), is(true));
    }

    @Test
    public void shouldPermitCheckingForTheExistenceOfAPreviousRow() {
        assertThat(cursor.hasPrevious(), is(false));
    }

}
