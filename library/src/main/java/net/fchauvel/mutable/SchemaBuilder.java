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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Provide a fluent interface for building schemas
 */
public class SchemaBuilder {
    
    public static SchemaBuilder aSchema() {
        return new SchemaBuilder();
    }
    
    private final List<FieldBuilder> fieldBuilders;
    
    public SchemaBuilder() {
        this.fieldBuilders = new LinkedList<>();
    }
    
    public SchemaBuilder with(FieldBuilder field) {
        if (field == null) {
            throw new IllegalArgumentException("Illegal field builder (found 'null')");
        }
        this.fieldBuilders.add(field);
        return this;
    }
    
    public Schema build() {
        final List<Field> fields = new ArrayList<>(fieldBuilders.size());
        for(FieldBuilder eachBuilder: fieldBuilders) {
            fields.add(eachBuilder.build());
        }
        return new Schema(fields);
    }
    
}
