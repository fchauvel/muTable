/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mutable.expression;

import org.mutable.Row;

/**
 * The logical conjunction operator
 */
public class LogicalOr extends BinaryExpression {

    public LogicalOr(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Object evaluate(Row row) {
        Boolean leftValue = asBoolean(getLeftOperand().evaluate(row));        
        Boolean rightValue = asBoolean(getRightOperand().evaluate(row));
        return leftValue || rightValue;
    }
    
       
}
