package org.mutable.expression;

/**
 * Factor out some code used by numerical comparison
 */
public abstract class NumericalComparison extends BinaryExpression {

    public NumericalComparison(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    protected Comparable asComparable(Object value, String operandName) throws RuntimeException {
        if (value instanceof Comparable) {
            return (Comparable) value;
        }

        final String error
                = String.format("Invalid %s operand (expecting '%s' but found '%s')",
                        operandName,
                        Comparable.class.getName(),
                        value.getClass().getName());

        throw new RuntimeException(error);
    }

}
