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

import net.fchauvel.mutable.storage.csv.CSVFormat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class CSVFormatTest {
    
    @Test
    public void shouldProvideAListOfFileExtensions() {
        CSVFormat csv = new CSVFormat();
        
        assertThat(csv.getFileExtensions(), hasItems("csv", "tsv"));
    }
   
     
    @Test
    public void shouldTellWhetherAnExtensionIsValid() {
        CSVFormat csv = new CSVFormat();
        
        assertThat(csv.hasExtension("csv"), is(true));
    }
    
}
