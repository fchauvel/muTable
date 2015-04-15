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
package net.fchauvel.mutable.expression;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import net.fchauvel.mutable.Table;
import static net.fchauvel.mutable.expression.FieldReference.field;
import static net.fchauvel.mutable.expression.Literal.value;
import static net.fchauvel.mutable.expression.Negation.not;
import static net.fchauvel.mutable.expression.RegexMatch.pattern;
import net.fchauvel.mutable.samples.Employees;

/**
 * Specification of the
 */
public class ExpressionTest {

    public final Table employees;

    public ExpressionTest() {
        employees = Employees.getTable();
    }

    @Test
    public void logicalAndShouldEvaluateProperly() {
        Table selection = employees.where(field("salary").isAbove(value(50D)).and(field("name").is(value("derek"))));

        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("derek")));
    }

    @Test
    public void disjunctionShouldEvaluateProperly() {
        Table selection = employees.where(field("salary").isAbove(value(50D)).or(field("salary").isBelow(value(30D))));

        assertThat(selection.getRowCount(), is(equalTo(2)));
        assertThat(selection.getData(1, "name"), is(equalTo("bob")));
        assertThat(selection.getData(2, "name"), is(equalTo("derek")));
    }

    @Test
    public void negationShouldEvaluateProperly() {
        Table selection = employees.where(not(field("name").is(value("derek"))));

        assertThat(selection.getRowCount(), is(equalTo(2)));
        assertThat(selection.getData(1, "name"), is(equalTo("bob")));
        assertThat(selection.getData(2, "name"), is(equalTo("john")));
    }

    @Test
    public void implicationShouldEvaluateProperly() {
        Table selection = employees.where(field("name").is(value("derek")).implies(field("salary").isAbove(value(50D))));

        assertThat(selection.getRowCount(), is(equalTo(3)));
        assertThat(selection.getData(1, "name"), is(equalTo("bob")));
        assertThat(selection.getData(2, "name"), is(equalTo("john")));
        assertThat(selection.getData(3, "name"), is(equalTo("derek")));
    }

    @Test
    public void regexMatchingShouldEvaluateProperly() {
        Table selection = employees.where(field("name").matches(pattern("de.+")));

        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("derek")));
    }

    @Test
    public void lessThanShouldEvaluateProperly() {
        Table selection = employees.where(field("salary").isBelow(value(50D)));

        assertThat(selection.getRowCount(), is(equalTo(2)));
        assertThat(selection.getData(1, "name"), is(equalTo("bob")));
        assertThat(selection.getData(2, "name"), is(equalTo("john")));
    }

    @Test
    public void greaterThanShouldEvaluateProperly() {
        Table selection = employees.where(field("salary").isAbove(value(50D)));

        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("derek")));
    }

    @Test
    public void isWithinShouldEvaluateProperly() {
        Table selection = employees.where(field("salary").isCloseTo(value(34D)).by(0.5));

        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("john")));
    }

}
