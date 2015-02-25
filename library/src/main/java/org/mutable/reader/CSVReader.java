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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.mutable.Schema;
import org.mutable.DataTable;

/**
 * Read tables from CSV data
 */
public class CSVReader extends Reader {

    public static final boolean WITH_HEADER = true;
    public static final boolean WITHOUT_HEADER = false;
    public static final String DEFAULT_FIELD_SEPARATOR = ",";

    private final String separator;
    private final boolean includeHeaders;

    public CSVReader() {
        this(DEFAULT_FIELD_SEPARATOR, WITHOUT_HEADER);
    }

    public CSVReader(String separator, boolean withHeaders) {
        this.separator = separator;
        this.includeHeaders = withHeaders;
    }

    @Override
    public DataTable readFrom(final InputStream input, long readingTimeout) throws ReaderException {
        if (input == null) {
            throw new IllegalArgumentException("Cannot read table from CSV (found 'null' stream)");
        }

        final TimedOutLineReader reader = new TimedOutLineReader(input, readingTimeout);
        final DataTable result = detectSchema(reader);
        while (reader.hasMoreLines()) {
            final Object[] row = extractObjects(reader.nextLine());
            appendRow(result, row, reader);
        }

        return result;
    }

    /**
     * Look at the two first lines of the CSV and extract columns names and the
     * first row (used to infer columns type)
     */
    private DataTable detectSchema(TimedOutLineReader reader) throws ReaderException {
        String[] columnNames = null;
        Object[] row = null;

        if (includeHeaders) {
            String headerLine = reader.nextLineOrThrow("Cannot find the headers line");
            columnNames = headerLine.split(separator);
            String sampleLine = reader.nextLineOrThrow("Could not find any CSV data, only headers");
            row = extractObjects(sampleLine);

        } else {
            String sampleLine = reader.nextLineOrThrow("Could not find any CSV data");
            row = extractObjects(sampleLine);
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
    private Object[] extractObjects(String line) {
        final List<Object> results = new ArrayList<>();
        final Scanner scanner = new Scanner(line);
        scanner.useDelimiter(separator);
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
