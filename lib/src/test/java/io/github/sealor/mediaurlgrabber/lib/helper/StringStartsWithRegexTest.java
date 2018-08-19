package io.github.sealor.mediaurlgrabber.lib.helper;

import org.junit.Test;

import static io.github.sealor.mediaurlgrabber.lib.helper.StringStartsWithRegex.startsWithRegex;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

public class StringStartsWithRegexTest {

	@Test
	public void test() {
		assertThat("abc", startsWithRegex("a.."));
		assertThat("abc", startsWithRegex("[a-c]{3}"));
		assertThat("abc", not(startsWithRegex("[d-z]{3}")));
	}

}
