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

import net.fchauvel.mutable.storage.Options;

/**
 * The options with can impact the reading / writing of CSV content
 */
public class CSVOptions implements Options {

    public static final String DEFAULT_FIELD_SEPARATOR = ",";
    public static final boolean DEFAULT_HAS_HEADER = false;

    public static CSVOptions getDefaults() {
        return new CSVOptions();
    }
    
    private String fieldSeparator;
    private boolean hasHeaders;

    public CSVOptions() {
        this(DEFAULT_FIELD_SEPARATOR, DEFAULT_HAS_HEADER);
    }

    public CSVOptions(String fieldSeparator) {
        this(fieldSeparator, DEFAULT_HAS_HEADER);
    }

    public CSVOptions(String fieldSeparator, boolean hasHeaders) {
        setFieldSeparator(fieldSeparator);
        this.hasHeaders = hasHeaders;
    }

    private void setFieldSeparator(String separator) {
        if (separator == null) {
            throw new IllegalArgumentException("Invalid field separator (found 'null')");
        }
        if (separator.isEmpty()) {
            throw new IllegalArgumentException("Invalid field separator '' (cannot be empty)");
        }
        this.fieldSeparator = separator;
    }

    public CSVOptions withFieldSeparator(String separator) {
        setFieldSeparator(separator);
        return this;
    }

    public String getFieldSeparator() {
        return this.fieldSeparator;
    }

    public boolean hasHeaders() {
        return this.hasHeaders;
    }

    public CSVOptions withHeaders() {
        this.hasHeaders = true;
        return this;
    }

    public CSVOptions withoutHeaders() {
        this.hasHeaders = false;
        return this;
    }

}
