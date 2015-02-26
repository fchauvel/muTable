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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mutable.storage.csv.CSVFormat;

@RunWith(JUnit4.class)
public class StorageTest {

    @Test
    public void shouldExposeTheListOfSupportedFormats() {
        final CSVFormat csv = new CSVFormat();
        Storage storage = new Storage(Arrays.asList(csv));

        assertThat(storage.getSupportedFormats(), hasItem(csv));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullURLs() throws ReaderException {
        Storage storage = new Storage();
        String location = null;
        storage.fetch(location);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectURLWithoutExtension() throws MalformedURLException, ReaderException {
        final FakeFormat csv = new FakeFormat();
        Storage storage = new Storage(Arrays.asList(csv));
        storage.fetch("http://foo.com/bar");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectInvalidExtension() throws MalformedURLException, ReaderException {
        final FakeFormat csv = new FakeFormat();
        Storage storage = new Storage(Arrays.asList(csv));
        storage.fetch("http://foocom/bar");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectURLWithUnknownExtension() throws MalformedURLException, ReaderException {
        final FakeFormat csv = new FakeFormat();
        Storage storage = new Storage(Arrays.asList(csv));
        storage.fetch("http://foo.com/bar.unknown");
    }

    @Test
    public void shouldReadFromURLs() throws ReaderException, MalformedURLException {
        final FakeFormat csv = new FakeFormat();
        Storage storage = new StorageWithContent(Arrays.asList(csv));
        storage.fetch("http://foo.com/bar.csv");

        assertThat(csv.wasCalled(), is(true));
    }

    private static class StorageWithContent extends Storage {

        public StorageWithContent(List<Format> supportedFormats) {
            super(supportedFormats);
        }

        @Override
        protected InputStream getStream(String location) throws IOException {
            final String csvSnippet
                    = "bob,25,false,1213.43\n"
                    + "derek,45,true,1234.53\n";
            return new ByteArrayInputStream(csvSnippet.getBytes(Charset.defaultCharset()));
        }

    }
}
