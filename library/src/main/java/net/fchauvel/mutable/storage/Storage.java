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
package net.fchauvel.mutable.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.fchauvel.mutable.Table;
import net.fchauvel.mutable.storage.csv.CSVFormat;

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
                selectFormat(location),
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
                selectFormat(location),
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
            return format.read(getInputStream(location), options, timeout);

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
    protected InputStream getInputStream(String location) throws IOException {
        return new URL(location).openStream();
    }

    /**
     * @return the format that matches the given file extension
     * @param extension
     */
    private Format selectFormat(String location) {
        final String extension = getFileExtension(location);
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

    /**
     * Store a table at the given location on disk
     *
     * @param table the table to be serialized
     * @param location the path to the local to be written
     */
    public void store(Table table, String location) throws FileNotFoundException {
        store(table, location, selectFormat(location));
    }

    /**
     * Store a table at the given location on disk
     *
     * @param table the table to be serialized
     * @param location the path to the local to be written
     * @param format the format to use to write the file
     */
    public void store(Table table, String location, Format format) throws FileNotFoundException {
        store(table, location, format, format.getDefaultOptions());
    }

    /**
     * Store a table at the given location on disk
     *
     * @param table the table to be serialized
     * @param location the path to the local to be written
     * @param options the format-specific options to use
     */
    public void store(Table table, String location, Options options) throws FileNotFoundException {
        store(table, location, selectFormat(location), options);
    }

    /**
     * Store a table at the given location on disk
     *
     * @param table the table to be serialized
     * @param location the path to the local to be written
     * @param format the format to use to write the file
     * @param options the format-specific options to use
     */
    public void store(Table table, String location, Format format, Options options) throws FileNotFoundException {
        format.write(table, getOutputStream(location), options);
    }

    
    protected OutputStream getOutputStream(String location) throws FileNotFoundException {
        return new FileOutputStream(new File(location));
    }
}
