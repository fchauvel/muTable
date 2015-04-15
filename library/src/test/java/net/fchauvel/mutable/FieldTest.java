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
package net.fchauvel.mutable;

import net.fchauvel.mutable.Field;
import net.fchauvel.mutable.FieldType;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class FieldTest {

    
    @Test
    public void shouldHaveAName() {
        final String FIELD_NAME = "my field";
        Field field = new Field(FIELD_NAME);

        assertThat(field.getName(), is(equalTo(FIELD_NAME)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullName() {
        new Field(null);
    }

    @Test
    public void shouldBeOfTypeStringByDefault() {
        Field field = new Field("my field");
        assertThat(field.getType(), is(equalTo(FieldType.STRING)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullAsType() {
        FieldType type = null;
        new Field("my field", type);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyStringAsName() {
        new Field("");
    }
    
    @Test
    public void equalsShouldDetectDifferentNames() {
        Field f1 = new Field("f1", FieldType.BOOLEAN);
        Field f2 = new Field("f2", FieldType.BOOLEAN);
        
        assertThat(f1.equals(f2), is(false));
        assertThat(f2.equals(f1), is(false));
    }
    
    @Test
    public void equalsShouldDetectDifferentType() {
        Field f1 = new Field("f1", FieldType.INTEGER);
        Field f2 = new Field("f1", FieldType.DOUBLE);
        
        assertThat(f1.equals(f2), is(false));
        assertThat(f2.equals(f1), is(false));
    }
    
    @Test
    public void equalsShouldDetectEquality() {
        Field f1 = new Field("f1", FieldType.INTEGER);
        Field f2 = new Field("f1", FieldType.INTEGER);
        
        assertThat(f1.equals(f2), is(true));
        assertThat(f2.equals(f1), is(true));
    }

}
