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

package net.fchauvel.mutable.expression;

import net.fchauvel.mutable.Row;

/**
 * The reference to a field in a table
 */
public class FieldReference extends Expression {
    
    /**
     * Factory method, part of the fluent interface pattern
     */
    public static FieldReference field(String fieldName) {
        return new FieldReference(fieldName);
    }
    
    private final String fieldName;
    
    // TODO check for null reference
    public FieldReference(String fieldName) {
        assert fieldName != null: "Illegal field name request (found 'null')";
        
        this.fieldName = fieldName;
    }

    @Override
    public Object evaluate(Row cursor) {
        return cursor.getField(fieldName);
    }
    
    @Override
    public String toString() {
        return "field(" + fieldName + ")";
    }
    
    
    
}
