
package org.mutable.expression;

import org.mutable.Row;

/**
 * The logical negation
 */
public class Negation extends Expression {

    public static Expression not(Expression operand) {
        return new Negation(operand);
    }
    
    private final Expression operand;
    
    public Negation(Expression operand) {
        this.operand = operand;
    }

    @Override
    public Object evaluate(Row row) {
        Boolean value = asBoolean(operand.evaluate(row));
        return !value;
    }
    
}
