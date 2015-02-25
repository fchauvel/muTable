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


public class IsEqualTo extends Expression {
    
    private final Expression left;
    private final Expression right;
    
    
    public IsEqualTo(Expression left, Expression right) {
        assert left != null: "Invalid 'IsEqualTo' (left is 'null')";
        assert right != null: "Invalid 'IsEqualTo' (right is 'null')";
        
        this.left = left;
        this.right = right;
    }

    @Override
    public Object evaluate(Row row) {
        Object leftValue = left.evaluate(row);
        Object rightValue = right.evaluate(row);
        return leftValue.equals(rightValue);
    }
    
    @Override
    public String toString() {
        return left + " is " + right;
    }
    
}
