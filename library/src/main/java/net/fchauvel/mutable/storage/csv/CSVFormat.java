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

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.fchauvel.mutable.Table;
import net.fchauvel.mutable.storage.Format;
import net.fchauvel.mutable.storage.Options;
import net.fchauvel.mutable.storage.ReaderException;

/**
 * Provide information about the CSV format
 */
public class CSVFormat implements Format {

    private final List<String> extensions;
 
    public CSVFormat() {
        this(Arrays.asList("csv", "tsv"));
    }

    public CSVFormat(List<String> extensions) {
        this.extensions = new ArrayList<>(extensions);
    }

    @Override
    public List<String> getFileExtensions() {
        return extensions;
    }

    @Override
    public boolean hasExtension(String extension) {
        for (String eachExtension : extensions) {
            if (eachExtension.equals(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Table read(InputStream input, Options options, long timeout) throws ReaderException {
        return new CSVReader().readFrom(input, options, timeout);
    }

    @Override
    public void write(Table table, OutputStream output, Options options) {
        new CSVWriter().write(table, output, options);
    }
    
    @Override
    public Options getDefaultOptions() {
        return new CSVOptions();
    }

}
