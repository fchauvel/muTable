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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import net.fchauvel.mutable.Table;

/**
 * Table storage format
 */
public interface Format {

    /**
     * @return the list file extensions, commonly used for this format
     */
    List<String> getFileExtensions();

    /**
     * @return true the given extension is used to denote this format
     * @param extension
     */
    boolean hasExtension(String extension);

    /**
     * @return the table read from the given input stream
     * @param input the stream where the table shall be read
     * @param options the options that may govern the read
     * @param timeout the maximum time to remain blocked waiting for data (in
     * ms.)
     * @throws net.fchauvel.mutable.storage.ReaderException if issue arise while reading
     */
    Table read(InputStream input, Options options, long timeout) throws ReaderException;

    /**
     * Write the given table on the given stream in this format
     *
     * @param table the table to be serialized
     * @param output the output stream where the data shall be written
     * @param options format-specific options that govern the writing operation
     */
    void write(Table table, OutputStream output, Options options);

    /**
     * @return the default options associated with this format
     */
    Options getDefaultOptions();

}
