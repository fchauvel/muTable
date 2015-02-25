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
package org.mutable;

import java.util.Objects;

public class Field {

    public static final FieldType DEFAULT_FIELD_TYPE = FieldType.STRING;

    private final String name;
    private final FieldType type;

    /**
     * Create a string field (the default type)
     *
     * @param name the name of the field
     */
    public Field(String name) {
        this(name, DEFAULT_FIELD_TYPE);
    }

    public Field(String name, FieldType type) {
        this.name = validateName(name);
        this.type = validateType(type);
    }

    /**
     * @return the given name only if valid (i.e., not null and not empty).
     * Raise an illegal argument exception otherwise.
     */
    private String validateName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Illegal field name (found 'null')");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Illegal field name (found '')");
        }
        return name;
    }

    /**
     * @return the name of this field
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the given type only if it is valid. Raise an illegal argument
     * exception otherwise.
     */
    private FieldType validateType(FieldType type) {
        if (type == null) {
            throw new IllegalArgumentException("Illegal field type (found 'null')");
        }
        return type;
    }

    /**
     * @return the data type associated with this field
     */
    public FieldType getType() {
        return this.type;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Field) {
            final Field otherField = (Field) other;
            return otherField.type == type && otherField.name.equals(name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

}
