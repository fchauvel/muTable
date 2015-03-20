package org.mutable.expression;

/**
 * Binary operators, such as AND, OR, ==, <=, etc.
 */
public abstract class BinaryExpression extends Expression {

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
