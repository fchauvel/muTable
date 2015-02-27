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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.mutable.expression.Expression;
import static org.mutable.expression.FieldReference.field;
import static org.mutable.expression.Literal.value;



public class ResultSetTest {

    private ResultSet sampleResultSetWithCache(Map<Integer, Integer> cache) {
        ResultSet results = makeResultSet(field("isMarried").is(value(false)), cache);
        return results;
    }

    private ResultSet makeResultSet(final Expression query, Map<Integer, Integer> cache) {
        Table table = Employees.getTable();
        ResultSet results = new ResultSet(table, query, cache);
        return results;
    }

    private ResultSet sampleResultSet() {
        return sampleResultSetWithCache(defaultCache());
    }

    private static HashMap<Integer, Integer> defaultCache() {
        return new HashMap<>();
    }

    @Test
    public void shouldTellWhenEmpty() {
        final ResultSet results = emptyResultSet();

        assertThat(results.isEmpty(), is(true));
    }

    private ResultSet emptyResultSet() {
        ResultSet results = makeResultSet(field("name").is(value("brandon")), defaultCache());
        return results;
    }

    @Test
    public void shouldExposeTheRowCount() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getRowCount(), is(equalTo(2)));
    }

    @Test
    public void shouldExposeSingleRowByIndex() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getRow(1).getField("name"), is(equalTo("john")));
    }

    @Test
    public void shouldGraduallyFillItsCache() {
        final Map<Integer, Integer> cache = defaultCache();
        final ResultSet results = sampleResultSetWithCache(cache);

        results.getRow(1).getField("name");

        assertThat(cache.size(), is(equalTo(1)));
    }

    @Test
    public void shouldFillItsCacheAsNeeded() {
        final Map<Integer, Integer> cache = defaultCache();
        final ResultSet results = sampleResultSetWithCache(cache);

        results.getRow(2).getField("name");

        assertThat(cache.size(), is(equalTo(2)));
    }

    @Test
    public void shouldExposeDataByFieldIndex() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getData(1, 1), is(equalTo("john")));
    }

    @Test
    public void shouldExposeDataByFieldName() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getData(1, "name"), is(equalTo("john")));
    }

    @Test
    public void shouldExposeDataCount() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getDataCount(), is(equalTo(8)));

    }

    @Test
    public void shouldExposeColumnCount() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getColumnCount(), is(equalTo(4)));

    }

    @Test
    public void shouldExposeItsSchema() {
        final ResultSet results = sampleResultSet();

        assertThat(results.getSchema(), is(equalTo(Employees.getSchema())));
    }

    @Test
    public void shouldSupportQueries() {
        final ResultSet results = sampleResultSet();
        ResultSet selection = results.where(field("name").is(value("john")));

        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("john")));
    }
    
    @Test
    public void shouldBeIterable() {
        Table table = Employees.getTable();

        List<String> names = new ArrayList<>();
        for(Row eachRow: table.where(field("isMarried").is(value(false)))) { 
            names.add((String) eachRow.getField("name"));
        }
        
        assertThat(names, hasItems("john", "derek"));
    }

}
