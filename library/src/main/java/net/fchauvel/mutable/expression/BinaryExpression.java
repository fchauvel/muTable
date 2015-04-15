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

/**
 * Binary operators, such as AND, OR, equality tests
 */
public abstract class BinaryExpression extends Expression {

    protected static final String LEFT_OPERAND = "left";
    protected static final String RIGHT_OPERAND = "right";

    private final Expression leftOperand;
    private final Expression rightOperand;

    public BinaryExpression(Expression leftOperand, Expression rightOperand) {
        assert leftOperand != null : "Invalid left operand (found 'null')";
        assert rightOperand != null : "Invalid right operand (foud 'null')";

        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * @return the expression of the left operand
     */
    public Expression getLeftOperand() {
        return leftOperand;
    }

    /**
     * @return the expression of the right operand
     */
    public Expression getRightOperand() {
        return rightOperand;
    }

}
