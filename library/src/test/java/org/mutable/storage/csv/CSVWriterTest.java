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
package org.mutable.storage.csv;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mutable.samples.Employees;
import org.mutable.Table;

@RunWith(JUnit4.class)
public class CSVWriterTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullTables() {
        final Table table = null;
        final ByteArrayOutputStream buffer = makeBuffer();
        
        write(table, buffer);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullOutput() {
        final Table table = Employees.getTable();
        final ByteArrayOutputStream output = null;

        write(table, output);
    }

    @Test
    public void shouldFormatTableWithoutHeaders() throws UnsupportedEncodingException {
        final Table table = Employees.getTable();
        final ByteArrayOutputStream output = makeBuffer();

        write(table, output);

        assertThat(output.toString(UTF_8), is(equalTo(Employees.getCsvWithoutHeader())));
    }

    @Test
    public void shouldFormatTableWithHeader() throws UnsupportedEncodingException {
        final Table table = Employees.getTable();
        final ByteArrayOutputStream output = makeBuffer();
        final CSVOptions options = defaultOptions().withHeaders();

        write(table, output, options);

        assertThat(output.toString(UTF_8), is(equalTo(Employees.getCsvWithHeader())));
    }

    private static ByteArrayOutputStream makeBuffer() {
        return new ByteArrayOutputStream();
    }

    private void write(Table table, final ByteArrayOutputStream output) {
        write(table, output, defaultOptions());
    }

    private void write(final Table table, final ByteArrayOutputStream output, final CSVOptions options) {
        new CSVWriter().write(table, output, options);
    }

    private static CSVOptions defaultOptions() {
        return CSVOptions.getDefaults();
    }

    private static final String UTF_8 = "UTF-8";


}
