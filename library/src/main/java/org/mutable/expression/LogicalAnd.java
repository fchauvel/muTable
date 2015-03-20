package org.mutable.expression;

import org.mutable.Row;

/**
 * the logical conjunction operator
 */
public class LogicalAnd extends BinaryExpression {

    public LogicalAnd(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Object evaluate(Row row) {
        Boolean leftValue = asBoolean(getLeftOperand().evaluate(row));        
        Boolean rightValue = asBoolean(getRightOperand().evaluate(row));
        return leftValue && rightValue;
    }

    private Boolean asBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        final String error = String.format("Illegal operand tyoe in conjunction (should be Boolean, but found '%s')", value.getClass().getName());
        throw new IllegalArgumentException(error);
    }

}
