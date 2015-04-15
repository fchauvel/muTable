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

import net.fchauvel.mutable.storage.Options;
import java.io.InputStream;
import java.io.OutputStream;
import net.fchauvel.mutable.Table;
import net.fchauvel.mutable.storage.csv.CSVFormat;

/**
 * A basic mock to check whether the format was called
 */
public class FakeFormat extends CSVFormat {

    private boolean wasRead;
    private boolean wasWritten;

    public FakeFormat() {
        wasRead = false;
        wasWritten = false;
    }
    
    public boolean wasReadCalled() {
        return wasRead;
    }
    
    public boolean wasWriteCalled() {
        return wasWritten;
    }

    @Override
    public Table read(InputStream input, Options options, long timeout) {
        wasRead = true;
        return null;
    }

    @Override
    public void write(Table table, OutputStream output, Options options) {
        wasWritten = true;
    }
    
    
    
}
