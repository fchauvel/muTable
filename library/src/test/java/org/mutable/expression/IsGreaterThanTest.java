
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
public class IsGreaterThanTest {

    @Test
    public void shouldEvaluateProperly() {
        Table employees = Employees.getTable();
        Table selection = employees.where(field("salary").isGreaterThan(value(50D)));
        
        assertThat(selection.getRowCount(), is(equalTo(1)));
        assertThat(selection.getData(1, "name"), is(equalTo("derek")));
    }
    
    
}
