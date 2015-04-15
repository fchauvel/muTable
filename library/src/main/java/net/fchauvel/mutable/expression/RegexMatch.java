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
 * Operator to match a value with a given pattern
 */
public class RegexMatch extends BinaryExpression {
    
    public static Expression pattern(String regex) {
        return new Literal(regex);
    }

    public RegexMatch(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Object evaluate(Row row) {
        String text = asString(getLeftOperand().evaluate(row));
        String pattern = asString(getRightOperand().evaluate(row));
        return text.matches(pattern);
    }

}
