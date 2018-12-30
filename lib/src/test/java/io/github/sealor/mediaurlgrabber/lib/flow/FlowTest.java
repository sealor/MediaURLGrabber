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
		String text = new Flow("a&amp;b").decodeHtml().toString();
		assertEquals("a&b", text);
	}

	@Test
	public void testDecodeUrl() {
		String text = new Flow("abc%20def").decodeUrl().toString();
		assertEquals("abc def", text);
	}

	@Test
	public void testFindRegex() {
		String text = new Flow("text abc text").findRegex(".* (abc) .*").toString();
		assertEquals("abc", text);
	}

	@Test
	public void testFindRegexFailedWithNoMatch() {
		thrown.expect(FlowException.class);
		thrown.expectMessage("Regex '.* (abc) .*' not found in document:\ntext def text");

		new Flow("text def text").findRegex(".* (abc) .*");
	}

	@Test
	public void testFindRegexFailedWithNullInput() {
		thrown.expect(NullPointerException.class);
		new Flow(null).findRegex("(abc)");
	}

	@Test
	public void testFormatContent() {
		String text = new Flow("abc").formatContent("%sdef").toString();
		assertEquals("abcdef", text);
	}

	@Test
	public void testResolveJsonString() {
		String json = "{'abc':[1,'def',9]}".replace('\'', '"');
		String text = new Flow(json).resolveJson("abc[1]").toString();
		assertEquals("def".replace('\'', '"'), text);
	}

	@Test
	public void testResolveJsonObject() {
		String json = "{'abc':[1,{'inner':'object'},9]}".replace('\'', '"');
		String text = new Flow(json).resolveJson("abc[1]").toString();
		assertEquals("{'inner':'object'}".replace('\'', '"'), text);
	}

	@Test
	public void testXPathWithJson() {
		String json = "{'abc':[1,{'key':'value'},9]}".replace('\'', '"');

		String result = new Flow(json).resolveXPathInJson("//abc/key").toString();
		assertEquals("value", result);
	}

	@Test
	public void testExceptionIfXPathNotFound() {
		String json = "{'abc':[1,{'key':'value'},9]}".replace('\'', '"');

		thrown.expect(FlowException.class);
		thrown.expectMessage("XPath '//abc/abc' not found in document:\n" +
				"<root><abc>1</abc><abc><key>value</key></abc><abc>9</abc></root>");
		new Flow(json).resolveXPathInJson("//abc/abc");
	}

	@Test
	public void testResolveUrlValue() {
		String urlParameters = "google.de?abc=def&ghi=klm";
		String text = new Flow(urlParameters).resolveUrlValue("abc").toString();
		assertEquals("def", text);
	}

	@Test
	public void testResolveUrlValueFailed() {
		String urlParameters = "google.de?abc=def&ghi=klm";

		thrown.expect(FlowException.class);
		thrown.expectMessage("xyz in URL abc=def&ghi=klm not found");
		new Flow(urlParameters).resolveUrlValue("xyz");
	}

}
