
package org.mutable.expression;

import org.mutable.Row;

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
