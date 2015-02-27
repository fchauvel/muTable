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

import java.io.OutputStream;
import org.mutable.Table;

/**
 * Behavior of the writer object
 */
public interface Writer {
    
    /**
     * Write the given table on the selected output stream
     * @param table the table to write
     * @param output the output stream where the table shall be serialized
     */
    void write(Table table, OutputStream output, Options options);
    
}
