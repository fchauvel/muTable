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

/**
 * The protocol of the expression that can be evaluate against a given row
 */
public abstract class Expression {

    /**
     * Evaluate this expression on the given row.
     *
     * @param row the row which should be used to resolve fields
     * @return the result of the evaluation as a single object
     */
    public abstract Object evaluate(Row row);

    public final IsEqualTo is(Expression right) {
        return new IsEqualTo(this, right);
    }

}
