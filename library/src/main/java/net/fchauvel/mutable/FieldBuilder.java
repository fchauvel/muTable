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

/**
 * Provide a fluent interface for building field description
 */
public class FieldBuilder {
    
    public static FieldBuilder aField(String name) {
        return new FieldBuilder(name);
    }
    
    private final String name;
    private FieldType type;
    
    public FieldBuilder(String name) {
        this.name = name;
        this.type = Field.DEFAULT_FIELD_TYPE;
    }
            
    public FieldBuilder ofType(FieldType type) {
        this.type = type;
        return this;
    }
    
    public Field build() {
        return new Field(name, type);
    }
    
}
