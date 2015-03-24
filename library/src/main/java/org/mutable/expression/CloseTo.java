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
 * Test whether the given value is in the given range
 */
public class CloseTo extends BinaryExpression {

    private double tolerance;

    public CloseTo(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
        this.tolerance = 1e-1;
    }

    public CloseTo by(double tolerance) {
        this.tolerance = tolerance;
        return this;
    }

    @Override
    public Object evaluate(Row row) {
        double left = asNumber(getLeftOperand().evaluate(row)).doubleValue();
        double right = asNumber(getRightOperand().evaluate(row)).doubleValue();
        Double delta = Math.abs(left - right);
        return delta.compareTo(tolerance) < 0;
    }

   

}
