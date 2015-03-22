/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mutable.expression;

import org.mutable.Row;

/**
 * the 'less than' comparison operator
 */
public class IsBelow extends NumericalComparison {

    public IsBelow(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Object evaluate(Row row) {
        Comparable left = asComparable(getLeftOperand().evaluate(row), LEFT_OPERAND);
        Comparable right = asComparable(getRightOperand().evaluate(row), RIGHT_OPERAND);
        return left.compareTo(right) < 0;
    }
    

}
