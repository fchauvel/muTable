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
import java.util.List;
import org.mutable.Table;

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
     * @param timeout the maximum time to remain blocked waiting for data (in
     * ms.)
     * @throws org.mutable.storage.ReaderException if issue arise while reading
     */
    Table read(InputStream input, long timeout) throws ReaderException;

}
