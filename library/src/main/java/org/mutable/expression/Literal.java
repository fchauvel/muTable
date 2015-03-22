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
package org.mutable.expression;

import org.mutable.Row;

public class Literal extends Expression {

    /**
     * Factory method part of the fluent interface API
    */
    public static Literal value(Object value) {
        return new Literal(value);
    }

    private final Object value;

    // TODO: Check for null
    public Literal(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(Row row) {
        return value;
    }

    @Override
    public String toString() {
        return "value(" + value + ")";
    }
    
}
