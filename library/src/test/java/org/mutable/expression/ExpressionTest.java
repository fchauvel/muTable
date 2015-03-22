
package org.mutable.expression;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.mutable.Table;
import static org.mutable.expression.FieldReference.field;
import static org.mutable.expression.Literal.value;
import org.mutable.samples.Employees;

/**
 * Specification of the 
 */
public class ExpressionTest {

    @Test
    public void logicalAndShouldEvaluateProperly() {
        Table employees = Employees.getTable();
        
        Table selection = employees.where(field("salary").isAbove(value(50D)).and(field("name").is(value("derek")))); 
  
        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("derek")));
    }
    
    
    @Test
    public void lessThanShouldEvaluateProperly() {
        Table employees = Employees.getTable();
        Table selection = employees.where(field("salary").isBelow(value(50D)));
        
        assertThat(selection.getRowCount(), is(equalTo(2)));
        assertThat(selection.getData(1, "name"), is(equalTo("bob")));
        assertThat(selection.getData(2, "name"), is(equalTo("john")));
    }
    
    
    @Test
    public void greaterThanShouldEvaluateProperly() {
        Table employees = Employees.getTable();
        Table selection = employees.where(field("salary").isAbove(value(50D)));
        
        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("derek")));
    }
    
    
}
