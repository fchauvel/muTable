package org.mutable.storage.csv;

import org.mutable.storage.Options;

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
