package org.mutable.expression;

import org.mutable.Row;

/**
 * Comparison operator
 */
public class IsGreaterThan extends BinaryExpression {

    public IsGreaterThan(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Object evaluate(Row row) {
        Comparable left = evaluate(row, getLeftOperand(), LEFT_OPERAND);
        Comparable right = evaluate(row, getRightOperand(), RIGHT_OPERAND);
        return left.compareTo(right) > 0;
    }

    private static final String LEFT_OPERAND = "left operand";
    private static final String RIGHT_OPERAND = "right operand";
    
    private Comparable evaluate(Row row, Expression expression, String operandName) throws RuntimeException {
        final Object value = expression.evaluate(row);
        if (value instanceof Comparable) {
            return (Comparable) value;
        }
        
        final String error 
                = String.format("Invalid isBelow %s (expecting '%s' but found '%s')", 
                        operandName, 
                        Comparable.class.getName(), 
                        value.getClass().getName());
        
        throw new RuntimeException(error);
    }

}
