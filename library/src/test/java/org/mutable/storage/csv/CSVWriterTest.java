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
import java.nio.charset.Charset;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mutable.Samples;
import org.mutable.Table;
import org.mutable.storage.Writer;

@RunWith(JUnit4.class)
public class CSVWriterTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullTables() {
        final Writer writer = new CSVWriter();
        
        Table table = null;
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        writer.write(table, output);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullOutput() {
        final Writer writer = new CSVWriter();
        
        Table table = Samples.employeeTable();
        final ByteArrayOutputStream output = null;
        writer.write(table, output);
    }
    
    @Test
    public void shouldFormatTableInCSVFormat() throws UnsupportedEncodingException {
        final Table table = Samples.employeeTable();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        final Writer writer = new CSVWriter();
        writer.write(table, output);

        final String expectedCSV
                = "name,age,isMarried,salary" + EOL
                + "bob,25,true,23.54" + EOL
                + "john,34,false,34.45" + EOL
                + "derek,56,false,67.34" + EOL;

        assertThat(output.toString(UTF_8), is(equalTo(expectedCSV))); 
    }

    @Test
    public void shouldFormatTableInCSVFormatWithHeader() throws UnsupportedEncodingException {
        final Table table = Samples.employeeTable();
        final ByteArrayOutputStream output = new ByteArrayOutputStream();

        final Writer writer = new CSVWriter(false);
        writer.write(table, output);

        final String expectedCSV
                = "bob,25,true,23.54" + EOL
                + "john,34,false,34.45" + EOL
                + "derek,56,false,67.34" + EOL;

        assertThat(output.toString(UTF_8), is(equalTo(expectedCSV)));
    }
    
    private static final String UTF_8 = "UTF-8";

    private static final String EOL = System.lineSeparator();

}
