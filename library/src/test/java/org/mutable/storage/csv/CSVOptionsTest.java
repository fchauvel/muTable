package org.mutable.storage.csv;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Specification of the CSV Options
 */
@RunWith(JUnit4.class)
public class CSVOptionsTest {

    @Test
    public void shouldExposeTheFieldSeparator() {
        CSVOptions options = new CSVOptions(",");

        assertThat(options.getFieldSeparator(), is(equalTo(",")));
    }

    @Test
    public void shouldHaveCommaAsDefaultFieldSeparator() {
        CSVOptions options = new CSVOptions();

        assertThat(options.getFieldSeparator(), is(equalTo(",")));
    }

    @Test
    public void shouldPermitToOverrideTheDefaultFieldSeparator() {
        CSVOptions options = new CSVOptions();

        final String otherSeparator = ";";
        options.withFieldSeparator(otherSeparator);

        assertThat(options.getFieldSeparator(), is(equalTo(otherSeparator)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectEmptyFieldSeparator() {
        new CSVOptions("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRejectNullFIeldSeparator() {
        final String separator = null;
        new CSVOptions(separator);
    }

    @Test
    public void shouldExposeThePresenceOfHeaders() {
        CSVOptions options = new CSVOptions(",", true);

        assertThat(options.hasHeaders(), is(true));
    }

    @Test
    public void shouldEnableHeaders() {
        CSVOptions options = new CSVOptions(",", false);
        options.withHeaders();

        assertThat(options.hasHeaders(), is(true));
    }

    @Test
    public void shouldDisableHeaders() {
        CSVOptions options = new CSVOptions();
        options.withoutHeaders();

        assertThat(options.hasHeaders(), is(false));
    }

}
