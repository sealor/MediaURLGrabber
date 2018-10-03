package io.github.sealor.mediaurlgrabber.lib.flow;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FlowTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testToString() {
		assertEquals("abc", new Flow("abc").toString());
	}

	@Test
	public void testReadUrl() {
		String fileName = getClass().getName().replace('.', '/') + ".java";
		Flow flow = new Flow("file:./src/test/java/" + fileName);

		String classFileContent = flow.readUrl().toString();

		assertThat(classFileContent, startsWith("package"));
	}

	@Test
	public void testReadUrlFailed() {
		Flow flow = new Flow("http://");

		thrown.expect(FlowException.class);
		thrown.expectMessage("http://");
		flow.readUrl();
	}

	@Test
	public void testDecodeHtml() {
		assertEquals("a&b", new Flow("a&amp;b").decodeHtml().toString());
	}

	@Test
	public void testFindRegex() {
		assertEquals("abc", new Flow("text abc text").findRegex(".* (abc) .*").toString());
	}

	@Test
	public void testFindRegexFailedWithNoMatch() {
		assertEquals(null, new Flow("text def text").findRegex(".* (abc) .*").toString());
	}

	@Test
	public void testFindRegexFailedWithNullInput() {
		thrown.expect(NullPointerException.class);
		new Flow(null).findRegex("(abc)");
	}

}
