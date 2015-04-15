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

package net.fchauvel.mutable.storage.csv;

import net.fchauvel.mutable.storage.Writer;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import net.fchauvel.mutable.Row;
import net.fchauvel.mutable.Schema;
import net.fchauvel.mutable.Table;
import net.fchauvel.mutable.storage.Options;

/**
 * A Table CSV writer
 */
public class CSVWriter implements Writer {


    @Override
    public void write(Table table, OutputStream output, Options options) {
        requireValidTable(table);
        requireValidOutput(output);
        requireValidOptions(options);

        final CSVOptions csvOptions = (CSVOptions) options;
        final PrintStream out = openStream(output);
        
        final Schema schema = table.getSchema();
        
        if (csvOptions.hasHeaders()) {
            printHeaderLine(schema, out, csvOptions);
        }
        
        for (Row eachRow : table) {
            printRow(schema, out, eachRow, csvOptions);
        }
    }

    private PrintStream openStream(OutputStream output) throws RuntimeException {
        try {
            return new PrintStream(output, true, UTF_8);
            
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Error while writing table", ex);
            
        }
    }

    private void requireValidOptions(Options options) throws IllegalArgumentException {
        if (options == null) {
            throw new IllegalArgumentException("Invalid CSV options ('null' found)");
        }
        if (!(options instanceof CSVOptions)) {
            throw new IllegalArgumentException("Invalid CSV options (found " + options.getClass().getName()+ ")");
        }
    }
    
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

    private static final String UTF_8 = "UTF-8";

    /**
     * Print the header line of the CSV file
     */
    private void printHeaderLine(final Schema schema, final PrintStream out, CSVOptions options) {
        for (String eachFieldName : schema.getFieldNames()) {
            out.print(eachFieldName);
            if (schema.getFieldIndex(eachFieldName) < schema.getFieldCount()) {
                out.print(options.getFieldSeparator());
            }
        }
        out.print(LINE_SEPARATOR);
    }

    /**
     * Format a single row
     */
    private void printRow(final Schema schema, final PrintStream out, Row eachRow, CSVOptions options) {
        for (String eachField : schema.getFieldNames()) {
            out.print(eachRow.getField(eachField));
            if (schema.getFieldIndex(eachField) < schema.getFieldCount()) {
                out.print(options.getFieldSeparator());
            }
        }
        out.print(LINE_SEPARATOR);
    }
    
    private static final String LINE_SEPARATOR = System.lineSeparator();

}
