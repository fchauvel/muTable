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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mutable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mutable.FieldType.*;

@RunWith(JUnit4.class)
public class FieldTypeTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectUnknownTypes() {
        FieldType.of(new Field("toto"));        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNull() {
        FieldType.of(null);
    }
    
    @Test
    public void shouldDetectStringType() {
        assertThat(FieldType.of("test"), is(equalTo(STRING)));
    }

    @Test
    public void shouldDetectIntegerType() {
        assertThat(FieldType.of(55), is(equalTo(INTEGER)));
    }

    @Test
    public void shouldDetectLongIntegerType() {
        assertThat(FieldType.of(55L), is(equalTo(LONG)));
    }

    @Test
    public void shouldDetectBooleanType() {
        assertThat(FieldType.of(true), is(equalTo(BOOLEAN)));
    }

    @Test
    public void shouldDetectDoubleType() {
        assertThat(FieldType.of(55.55), is(equalTo(DOUBLE)));
    }

    @Test
    public void shouldDetectFloatType() {
        assertThat(FieldType.of(55.55F), is(equalTo(FLOAT)));
    }

    @Test
    public void shouldDetectCharacterType() {
        assertThat(FieldType.of('c'), is(equalTo(CHARACTER)));

    }

}
