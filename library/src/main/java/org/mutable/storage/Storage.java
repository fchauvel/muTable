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

    // TODO: Storage should permit to pass parameters to reader in order specify options, such as headers for instance
    private final List<Format> supportedFormats;

    public Storage() {
        this(Arrays.asList(new CSVFormat()));
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

    private static final long DEFAULT_TIMEOUT = 2000;

    /**
     * @return the table read for the content available at the given location
     * @param location the location of the data to read
     * @throws ReaderException if some error occurs while reading the data
     */
    public Table fetch(URL location) throws ReaderException {
        return fetch(location, DEFAULT_TIMEOUT);
    }

    // TODO: Ensure reading timeout are set in one single place
    
    /**
     * @return a table containing the data available at the given location
     * @param location the URL of the table to fetch
     * @param timeout the time to wait
     * @throws ReaderException when some I/O errors occurs
     */
    public Table fetch(URL location, long timeout) throws ReaderException {
        requireValidUrl(location);
        try {
            Format format = selectFormat(getFileExtension(location));
            return format.read(getStream(location), timeout);

        } catch (IOException ex) {
            throw new ReaderException("Unable to open the url '" + location.toString() + "'", ex);
        }
    }

    protected void requireValidUrl(URL location) throws IllegalArgumentException {
        if (location == null) {
            throw new IllegalArgumentException("Invalid URL ('null' found)");
        }
    }

    /**
     * For testing purpose. This method can be overridden to fake the content of
     * the data at the URL.
     *
     * @param location the URL whose input stream is needed
     * @return an input stream to the content of URL
     */
    protected InputStream getStream(URL location) throws IOException {
        return location.openStream();
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
        List<String> extensions = new ArrayList<>();
        for (Format eachFormat : supportedFormats) {
            extensions.addAll(eachFormat.getFileExtensions());
        }
        return extensions;
    }

    /**
     * @return the file extension in that URL
     * @param location the URL whose file extension is needed
     */
    private String getFileExtension(URL location) throws IllegalArgumentException {
        String urlText = location.toString();
        int lastDotIndex = urlText.lastIndexOf('.');
        return urlText.substring(lastDotIndex + 1, urlText.length());
    }

}
