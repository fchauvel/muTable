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

import org.mutable.storage.ReaderException;
import org.mutable.storage.TimedOutLineReader;
import org.mutable.storage.Reader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mutable.Schema;
import org.mutable.DataTable;
import org.mutable.storage.Options;

/**
 * Read tables from CSV data
 */
public class CSVReader implements Reader {

    
    @Override
    public DataTable readFrom(final InputStream input, Options options, long readingTimeout) throws ReaderException {
        if (input == null) {
            throw new IllegalArgumentException("Cannot read table from CSV (found 'null' stream)");
        }

        final CSVOptions csvOptions = (CSVOptions) options;
        final TimedOutLineReader reader = new TimedOutLineReader(input, readingTimeout);
        final DataTable result = detectSchema(reader, csvOptions);
        while (reader.hasMoreLines()) {
            final Object[] row = extractObjects(reader.nextLine(), csvOptions);
            appendRow(result, row, reader);
        }

        return result;
    }

    /**
     * Look at the two first lines of the CSV and extract columns names and the
     * first row (used to infer columns type)
     */
    private DataTable detectSchema(TimedOutLineReader reader, CSVOptions options) throws ReaderException {
        String[] columnNames = null;
        Object[] row = null;

        if (options.hasHeaders()) {
            String headerLine = reader.nextLineOrThrow("Cannot find the headers line");
            columnNames = headerLine.split(options.getFieldSeparator());
            String sampleLine = reader.nextLineOrThrow("Could not find any CSV data, only headers");
            row = extractObjects(sampleLine, options);

        } else {
            String sampleLine = reader.nextLineOrThrow("Could not find any CSV data");
            row = extractObjects(sampleLine, options);
            columnNames = Schema.defaultColumnNames(row.length);
        }

        DataTable resultTable = new DataTable(Schema.inferedFrom(columnNames, row));
        appendRow(resultTable, row, reader);
        return resultTable;
    }

    /**
     * Breaks a line of text into an array of primitive types (e.g., int,
     * double, boolean)
     */
    private Object[] extractObjects(String line, CSVOptions options) {
        final List<Object> results = new ArrayList<>();
        final Scanner scanner = new Scanner(line);
        scanner.useDelimiter(options.getFieldSeparator());
        while (scanner.hasNext()) {
            if (scanner.hasNextInt()) {
                results.add(scanner.nextInt());
            } else if (scanner.hasNextLong()) {
                results.add(scanner.nextLong());
            } else if (scanner.hasNextFloat()) {
                results.add(scanner.nextFloat());
            } else if (scanner.hasNextDouble()) {
                results.add(scanner.nextDouble());
            } else if (scanner.hasNextBoolean()) {
                results.add(scanner.nextBoolean());
            } else {
                results.add(scanner.next());
            }
        }
        return results.toArray();
    }

    private void appendRow(DataTable result, Object[] row, final TimedOutLineReader reader) throws ReaderException {
        try {
            result.appendRow(row);

        } catch (IllegalArgumentException iae) {
            final String error = String.format("Invalid row %d", reader.getLineCount());
            throw new ReaderException(error, iae);

        }
    }

}
