/**
 * This file is part of MuTable.
 *
 * Copyright (C) 2015 Franck Chauvel <franck.chauvel@gmail.com>
 *
 * MuTable is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Mutable is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with MuTable.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mutable.expression;

import org.mutable.Row;

/**
 * The protocol of the expression that can be evaluate against a given row
 */
public abstract class Expression {

    /**
     * Evaluate this expression on the given row.
     *
     * @param row the row which should be used to resolve fields
     * @return the result of the evaluation as a single object
     */
    public abstract Object evaluate(Row row);

    /**
     * Logical conjunction of this expression and the given one
     *
     * @param right the right operand of the logical expression
     * @return the logical conjunction
     */
    public final LogicalAnd and(Expression right) {
        return new LogicalAnd(this, right);
    }

    /**
     * Logical disjunction of this expression and the given one
     *
     * @param right the right operand of the logical expression
     * @return the logical disjunction
     */
    public final LogicalOr or(Expression right) {
        return new LogicalOr(this, right);
    }

    /**
     * Logical implication of this expression and the given one
     *
     * @param right operand of the logical expression
     * @return the logical implication
     */
    public final LogicalOr implies(Expression right) {
        return new Negation(this).or(right);
    }

    /**
     * Equality operator
     *
     * @param right the right operand of the operator (this expression for
     * equality operator)
     * @return the expression for the equality test
     */
    public final IsEqualTo is(Expression right) {
        return new IsEqualTo(this, right);
    }

    /**
     * GreaterThan (>) comparison operator
     *
     * @param right the right operand of the operator (this expression for
     * equality operator)
     * @return the expression for the equality test
     */
    public final IsAbove isAbove(Expression right) {
        return new IsAbove(this, right);
    }

    /**
     * LessThan (<) comparison operator
     *
     * @param right the right operand of the operator (this expression for
     * equality operator)
     * @return the expression for the inferiority test
     */
    public final IsBelow isBelow(Expression right) {
        return new IsBelow(this, right);
    }

    /**
     * Range comparison. Check whether the two operand are within a range
     *
     * @param right the right operand
     * @return the range comparison test
     */
    public final CloseTo isCloseTo(Expression right) {
        return new CloseTo(this, right);
    }

    /**
     * Regular expression (R.E.) matching test
     *
     * @param pattern a literal containing the text of the R.E. to test
     * @return the Regex test object
     */
    public final RegexMatch matches(Expression pattern) {
        return new RegexMatch(this, pattern);
    }

    // Helper for converting operands
    
    protected Boolean asBoolean(Object value) {
        return as(value, Boolean.class);
    }

    protected Comparable asComparable(Object value) throws RuntimeException {
        return as(value, Comparable.class);
    }

    protected String asString(Object value) {
        return as(value, String.class);
    }

    protected Number asNumber(Object value) {
        return as(value, Number.class);
    }

    protected <T> T as(Object value, Class<T> type) {
        if (type.isAssignableFrom(value.getClass())) {
            return (T) value;
        }

        final String errorMessage
                = String.format(
                        "Invalid operand type (expecting '%s', but found '%s')",
                        type.getName(),
                        value.getClass().getName()
                );
        throw new IllegalArgumentException(errorMessage);
    }

}
