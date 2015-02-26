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
package org.mutable.storage.csv;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.mutable.Table;
import org.mutable.storage.Format;
import org.mutable.storage.Options;
import org.mutable.storage.ReaderException;
import org.mutable.storage.Writer;

/**
 * Provide information about the CSV format
 */
public class CSVFormat implements Format {

    private final List<String> extensions;
    private final Writer writer;

    public CSVFormat() {
        this(Arrays.asList("csv", "tsv"), new CSVWriter());
    }

    public CSVFormat(List<String> extensions, Writer writer) {
        this.extensions = new ArrayList<>(extensions);
        this.writer = writer;
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
    public Options getDefaultOptions() {
        return new CSVOptions();
    }

}
