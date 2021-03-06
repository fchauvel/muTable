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


public enum FieldType {
    STRING(String.class.getName()),
    INTEGER(Integer.class.getName()),
    LONG(Long.class.getName()),
    FLOAT(Float.class.getName()),
    DOUBLE(Double.class.getName()),
    BOOLEAN(Boolean.class.getName()),
    CHARACTER(Character.class.getName())
    ;
    
    private final String className;
    
    private FieldType(String className) {
        this.className = className;
    }
    
    public String getClassName() {
        return this.className;
    }
    
    public static FieldType of(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Unsupported column type 'null'");
        }
        
        for(FieldType eachType: values()) {
            if (eachType.className.equals(object.getClass().getName())) {
                return eachType;
            }
        }
        throw new IllegalArgumentException("Unsupported column type '" + object.getClass().getName() + "'");
    }
    
}
