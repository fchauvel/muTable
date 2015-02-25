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
package org.mutable.reader;

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
import org.mutable.Field;
import static org.mutable.FieldType.*;
import org.mutable.Schema;
import org.mutable.DataTable;
import static org.mutable.reader.CSVReader.WITHOUT_HEADER;
import static org.mutable.reader.CSVReader.WITH_HEADER;


@RunWith(JUnit4.class)
public class CSVReaderTest {

    // TODO: Handle missing values
    
    @Test
    public void shouldReadSimpleCSVSnippetWithoutHeaders() throws ReaderException {
        final CSVReader reader = makeCSVReader(WITHOUT_HEADER);

        final String CSVSnippet
                = "bob,35,true,123.34\n"
                + "derek,45,false,4322.34";

        final DataTable table = reader.readFrom(toStream(CSVSnippet));

        final Object[][] expectedData = new Object[][]{
            {"bob", 35, true, 123.34F},
            {"derek", 45, false, 4322.34F}
        };

        final Schema expectedSchema = new Schema(new Field[]{
            new Field("column 1", STRING),
            new Field("column 2", INTEGER),
            new Field("column 3", BOOLEAN),
            new Field("column 4", FLOAT)
        });

        verifyTable(table, expectedSchema, expectedData);
    }

    @Test
    public void shouldReadCSVSnippetWithHeaders() throws ReaderException {
        final CSVReader reader = makeCSVReader(WITH_HEADER);

        final String CSVSnippet
                = "name,age,married,salary\n"
                + "bob,35,true,123.34\n"
                + "derek,45,false,4322.34";

        final DataTable table = reader.readFrom(toStream(CSVSnippet));

        final Object[][] expectedData = new Object[][]{
            {"bob", 35, true, 123.34F},
            {"derek", 45, false, 4322.34F}
        };

        final Schema expectedSchema = new Schema(new Field[]{
            new Field("name", STRING),
            new Field("age", INTEGER),
            new Field("married", BOOLEAN),
            new Field("salary", FLOAT)
        });

        verifyTable(table, expectedSchema, expectedData);
    }

    public void verifyTable(final DataTable table, final Schema expectedSchema, final Object[][] expectedData) {
        assertThat("schema", table.getSchema(), is(equalTo(expectedSchema)));

        assertThat("row count", table.getRowCount(), is(equalTo(2)));
        assertThat("column count", table.getColumnCount(), is(equalTo(4)));
        assertThat("data count", table.getDataCount(), is(equalTo(8)));

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
        CSVReader reader = makeCSVReader();
        final InputStream blockedStream = new BlockingInputStream();
        reader.readFrom(blockedStream);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectReadingFromNull() throws ReaderException {
        CSVReader reader = makeCSVReader();

        reader.readFrom(null);
    }

    private CSVReader makeCSVReader() {
        return makeCSVReader(WITHOUT_HEADER);
    }

    private CSVReader makeCSVReader(boolean withHeaders) {
        final String SEPARATOR = ",";
        final CSVReader reader = new CSVReader(SEPARATOR, withHeaders);
        return reader;
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
                while(true) { wait(); }
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return 0;
        }

        @Override
        public int read() throws IOException {
            try {
                while(true) { wait();}
                
            } catch (InterruptedException ex) {
               ex.printStackTrace();
            }
            return 0;
        }

    }
}
