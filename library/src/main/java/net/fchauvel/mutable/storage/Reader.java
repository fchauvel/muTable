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
import net.fchauvel.mutable.DataTable;

/**
 * Protocol of Table readers
 */
public interface Reader {

    /**
     * @return the table read from the given input stream
     * @param input the stream where the table shall be read
     * @param options specific to format to be read
     * @param readingTimeout the number of ms. to wait on a 'readline' before to fail
     * @throws ReaderException when errors when reading the stream (I/O error,
     * timeouts, etc.)
     */
    DataTable readFrom(InputStream input, Options options, long readingTimeout) throws ReaderException;

    
}
