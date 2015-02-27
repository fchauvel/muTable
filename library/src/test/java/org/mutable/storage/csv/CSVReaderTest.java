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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mutable.Schema;
import org.mutable.DataTable;
import org.mutable.samples.Employees;
import org.mutable.storage.ReaderException;

@RunWith(JUnit4.class)
public class CSVReaderTest {

    // TODO: Handle missing values
    @Test
    public void shouldReadSimpleCSVSnippetWithoutHeaders() throws ReaderException {

        final DataTable table = invokeRead(
                Employees.getCsvWithoutHeader(),
                CSVOptions.getDefaults().withoutHeaders()
        );

        verifyTable(table, Employees.getGeneratedSchema(), Employees.getRawData());
    }

    private static DataTable invokeRead(final String CSVSnippet, final CSVOptions options) throws ReaderException {
        return new CSVReader().readFrom(toStream(CSVSnippet), options, 1000L);
    }

    @Test
    public void shouldReadCSVSnippetWithHeaders() throws ReaderException {

        final DataTable table = invokeRead(
                Employees.getCsvWithHeader(),
                CSVOptions.getDefaults().withHeaders());                
        
        verifyTable(table, Employees.getSchema(), Employees.getRawData());
    }

    public void verifyTable(final DataTable table, final Schema expectedSchema, final Object[][] expectedData) {
        assertThat("schema", table.getSchema(), is(equalTo(expectedSchema)));

        assertThat("row count", table.getRowCount(), is(expectedData.length));
        assertThat("column count", table.getColumnCount(), is(expectedData[0].length));
        assertThat("data count", table.getDataCount(), is(expectedData.length * expectedData[0].length));

        for (int row = 0; row < table.getRowCount(); row++) {
            for (int column = 0; column < table.getColumnCount(); column++) {
                assertThat(
                        String.format("Wrong values at [%d,%d]", row + 1, column + 1),
                        table.getData(row + 1, column + 1),
                        is(equalTo(expectedData[row][column])));
            }
        }
    }

    @Test(expected = ReaderException.class)
    public void shouldTimeoutWhenThereIsNothingToRead() throws ReaderException {
        new CSVReader().readFrom(new BlockingInputStream(), CSVOptions.getDefaults(), 1000L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectReadingFromNull() throws ReaderException {
        final InputStream stream = null;
        new CSVReader().readFrom(stream, CSVOptions.getDefaults(), 1000L);
    }

    private static ByteArrayInputStream toStream(final String CSVSnippet) {
        return new ByteArrayInputStream(CSVSnippet.getBytes(StandardCharsets.UTF_8));
    }

    private static class BlockingInputStream extends InputStream {

        @Override
        public void close() throws IOException {
            throw new IOException("Closed stream!");
        }

        @Override
        public int read(byte[] b) throws IOException {
            try {
                while (true) {
                    wait();
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return 0;
        }

        @Override
        public int read() throws IOException {
            try {
                while (true) {
                    wait();
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return 0;
        }

    }
}
