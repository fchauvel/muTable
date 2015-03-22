package org.mutable.expression;

import org.mutable.Row;

/**
 * Comparison operator
 */
public class IsAbove extends NumericalComparison {

    public IsAbove(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Object evaluate(Row row) {
        Comparable left = asComparable(getLeftOperand().evaluate(row), LEFT_OPERAND);
        Comparable right = asComparable(getRightOperand().evaluate(row), RIGHT_OPERAND);
        return left.compareTo(right) > 0;
    }


    
}
