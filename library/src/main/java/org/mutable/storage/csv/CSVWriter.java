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

import org.mutable.storage.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mutable.Row;
import org.mutable.Schema;
import org.mutable.Table;

/**
 * A Table CSV writer
 */
public class CSVWriter implements Writer {

    public static final boolean WITH_HEADER = true;
    public static final boolean WITHOUT_HEADER = false;
    public static final String DEFAULT_FIELD_SEPARATOR = ",";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private final String fieldSeparator;
    private final boolean includeHeaders;

    public CSVWriter() {
        this(DEFAULT_FIELD_SEPARATOR, WITH_HEADER);
    }
    
    public CSVWriter(boolean withHeader) { 
        this(DEFAULT_FIELD_SEPARATOR, withHeader);
    }

    /**
     * Configure a new CSVWriter
     * @param fieldSeparator the marker to use to separate fields
     * @param writeHeader indicate whether a header line is needed.
     */
    public CSVWriter(String fieldSeparator, boolean writeHeader) {
        this.fieldSeparator = fieldSeparator;
        this.includeHeaders = writeHeader;
    }

    @Override
    public void write(Table table, OutputStream output) {
        requireValidTable(table);
        requireValidOutput(output);

        PrintStream out = null; 
        try {
            out = new PrintStream(output, true, UTF_8);
        
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(CSVWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        final Schema schema = table.getSchema();
        if (includeHeaders) {
            printHeaderLine(schema, out);
        }
        for (Row eachRow : table) {
            printRow(schema, out, eachRow);
        }
    }
    private static final String UTF_8 = "UTF-8";

    private void requireValidOutput(OutputStream output) throws IllegalArgumentException {
        if (output == null) {
            throw new IllegalArgumentException("Invalid output ('null' found)");
        }
    }

    private void requireValidTable(Table table) throws IllegalArgumentException {
        if (table == null) {
            throw new IllegalArgumentException("Invalid table ('null' found)");
        }
    }

    /**
     * Print the header line of the CSV file
     */
    private void printHeaderLine(final Schema schema, final PrintStream out) {
        for (String eachFieldName : schema.getFieldNames()) {
            out.print(eachFieldName);
            if (schema.getFieldIndex(eachFieldName) < schema.getFieldCount()) {
                out.print(fieldSeparator);
            }
        }
        out.print(LINE_SEPARATOR);
    }

    /**
     * Format a single row
     */
    private void printRow(final Schema schema, final PrintStream out, Row eachRow) {
        for (String eachField : schema.getFieldNames()) {
            out.print(eachRow.getField(eachField));
            if (schema.getFieldIndex(eachField) < schema.getFieldCount()) {
                out.print(fieldSeparator);
            }
        }
        out.print(LINE_SEPARATOR);
    }

}
