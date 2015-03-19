package org.mutable.expression;

import org.mutable.Row;

/**
 * Comparison operator
 */
public class IsGreaterThan extends Expression {

    private final Expression leftOperand;
    private final Expression rightOperand;

    public IsGreaterThan(Expression leftOperand, Expression rightOperand) {
        assert leftOperand != null : "Invalid left operand (found 'null')";
        assert rightOperand != null : "Invalid right operand (foud 'null')";

        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    @Override
    public Object evaluate(Row row) {
        Comparable left = evaluate(row, leftOperand, LEFT_OPERAND);
        Comparable right = evaluate(row, rightOperand, RIGHT_OPERAND);
        return left.compareTo(right) > 0;
    }

    private static final String LEFT_OPERAND = "left operand";
    private static final String RIGHT_OPERAND = "right operand";

    private Comparable evaluate(Row row, Expression expression, String operand) throws RuntimeException {
        final Object value = expression.evaluate(row);
        if (value instanceof Comparable) {
            return (Comparable) value;
        }
        
        final String error 
                = String.format("Invalid IsGreatherThan %s (expecting '%s' but found '%s')", 
                        operand, 
                        Comparable.class.getName(), 
                        value.getClass().getName());
        
        throw new RuntimeException(error);
    }

}
