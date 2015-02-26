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
package org.mutable.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mutable.Table;
import org.mutable.storage.csv.CSVFormat;

/**
 * Provide a facade to the various format that can be supported
 */
public class Storage {

    private static final List<Format> DEFAULT_SUPPORTED_FORMATS = Arrays.asList(new CSVFormat());

    /**
     * @return a new instance of storage, for the fluent interface API
     */
    public static Storage storage() {
        return new Storage();
    }
    
    private final List<Format> supportedFormats;

    public Storage() {
        this(DEFAULT_SUPPORTED_FORMATS);
    }

    public Storage(List<Format> supportedFormats) {
        this.supportedFormats = new ArrayList<>(supportedFormats);
    }

    /**
     * @return the list of supported format
     */
    public List<Format> getSupportedFormats() {
        return this.supportedFormats;
    }

    /**
     * @return the table read for the content available at the given location
     * @param location the location of the data to read
     * @throws ReaderException if some error occurs while reading the data
     */
    public Table fetch(String location) throws ReaderException {
        return fetch(
                location,
                TimedOutLineReader.DEFAULT_READING_TIMEOUT);
    }

    /**
     * @return the table read for the content available at the given location
     * @param location the URL of the table to fetch
     * @param timeout the time to wait
     * @throws ReaderException 
     */
    public Table fetch(String location, long timeout) throws ReaderException {
        return fetch(
                location, 
                selectFormat(getFileExtension(location)), 
                timeout);
    }

    /**
     * @return a table containing the data available at the given location
     * @param location the URL of the table to fetch
     * @param format the format which is expected
     * @param timeout the time to wait
     * @throws ReaderException when some I/O errors occurs
     */
    public Table fetch(String location, Format format, long timeout) throws ReaderException {
        return fetch(
                location, 
                format, 
                format.getDefaultOptions(), 
                timeout);
    }

    /**
     * @return a table containing the data available at the given location
     * @param location the URL of the table to fetch
     * @param options the options that govern the read operation
     * @param timeout the time to wait
     * @throws ReaderException when some I/O errors occurs
     */
    public Table fetch(String location, Options options, long timeout) throws ReaderException {
        return fetch(
                location, 
                selectFormat(getFileExtension(location)), 
                options, 
                timeout);
    }

    /**
     * @return a table containing the data available at the given location
     * @param location the URL of the table to fetch
     * @param format the format which is expected
     * @param options the options that govern the read operation
     * @param timeout the time to wait
     * @throws ReaderException when some I/O errors occurs
     */
    public Table fetch(String location, Format format, Options options, long timeout) throws ReaderException {
        try {
            return format.read(getStream(location), options, timeout);

        } catch (IOException ex) {
            throw new ReaderException("Unable to open the location '" + location + "'", ex);
        }
    }


    private void requireValidUrl(String location) throws IllegalArgumentException {
        if (location == null) {
            throw new IllegalArgumentException("Invalid location ('null' found)");
        }
        if (location.isEmpty()) {
            throw new IllegalArgumentException("Invalid location ('' found)");
        }
    }

    /**
     * For testing purpose. This method can be overridden to fake the content of
     * the data at the URL.
     *
     * @param location the URL whose input stream is needed
     * @return an input stream to the content of URL
     */
    protected InputStream getStream(String location) throws IOException {
        return new URL(location).openStream();
    }

    /**
     * @return the format that matches the given file extension
     * @param extension
     */
    private Format selectFormat(String extension) {
        for (Format anyFormat : supportedFormats) {
            if (anyFormat.hasExtension(extension)) {
                return anyFormat;
            }
        }
        final String error = String.format("Unsupported file extension '%s' (supported extensions are %s)", extension, supportedFileExtensions());
        throw new IllegalArgumentException(error);
    }

    /**
     * @return the list of supported file extensions
     */
    private List<String> supportedFileExtensions() {
        final List<String> extensions = new ArrayList<>();
        for (Format eachFormat : supportedFormats) {
            extensions.addAll(eachFormat.getFileExtensions());
        }
        return extensions;
    }

    /**
     * @return the file extension in that URL
     * @param location the URL whose file extension is needed
     */
    private String getFileExtension(String location) throws IllegalArgumentException {
        requireValidUrl(location);
        int lastDotIndex = location.lastIndexOf('.');
        return location.substring(lastDotIndex + 1, location.length());
    }

}
