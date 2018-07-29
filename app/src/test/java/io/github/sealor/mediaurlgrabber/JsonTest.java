package io.github.sealor.mediaurlgrabber;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonTest {

	@Test
	public void testIsObject() {
		String jsonString = "{}";
		assertTrue(new JsonParser().parse(jsonString).isObject());
	}

	@Test
	public void testIsArray() {
		String jsonString = "[]";
		assertTrue(new JsonParser().parse(jsonString).isArray());
	}

	@Test
	public void testGetStringWithAttributeName() {
		String jsonString = "{\"attr\":\"abc\"}";
		assertEquals("abc", new JsonParser().parse(jsonString).getString("attr"));
	}

	@Test
	public void testGetStringWithIndex() {
		String jsonString = "[\"abc\"]";
		assertEquals("abc", new JsonParser().parse(jsonString).getString(0));
	}

	@Test
	public void testGetJsonWithPath() {
		String jsonString = "{\"attr1\":{\"attr2\":[\"def\",\"ghi\",{\"attr3\":\"abc\"}]}}";
		assertEquals("{\"attr3\":\"abc\"}", new JsonParser().parse(jsonString).getJson("attr1.attr2[2]").toString());
	}

	@Test
	public void splitTest() {
		assertArrayEquals("abc.def[3].ghi".split("[.\\[\\]]+"), Arrays.asList("abc", "def", "3", "ghi").toArray());
		assertArrayEquals("abc.def[3].ghi".split("[.\\[\\]]+", 2), Arrays.asList("abc", "def[3].ghi").toArray());
		assertArrayEquals("abc".split("[.\\[\\]]+", 2), Arrays.asList("abc").toArray());
	}

}
