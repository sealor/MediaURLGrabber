package io.github.sealor.mediaurlgrabber.lib.normalizer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class KeyNormalizerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testNormalization() {
		KeyNormalizer keyNormalizer = new KeyNormalizer();

		for (String key : Arrays.asList("$abc", "\"abc", "(abc", "{abc", ",abc"))
			assertEquals("_abc", keyNormalizer.normalize(key));

		for (String key : Arrays.asList("abc$def", "abc\"def", "abc(def", "abc{def", "abc,def"))
			assertEquals("abc-def", keyNormalizer.normalize(key));
	}

	@Test
	public void testMapNormalization() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("$abc", "1");
		map.put("{}abc", "2");
		map.put("a:bc(abc{abc}abc)a$bc", "3");

		map = new KeyNormalizer().normalize(map);

		Map<String, Object> expectedMap = new LinkedHashMap<>();
		expectedMap.put("_abc", "1");
		expectedMap.put("_-abc", "2");
		expectedMap.put("a-bc-abc-abc-abc-a-bc", "3");

		assertEquals(expectedMap, map);
	}

	@Test
	public void testExceptionAtKeyDuplication() {
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("$abc", "1");
		map.put(":abc", "2");

		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Key '_abc' is already used");

		new KeyNormalizer().normalize(map);
	}

	@Test
	public void testNormalizationInChildMap() {
		Map<String, Object> childMap = new LinkedHashMap<>();
		childMap.put(":abc", "1");

		Map<String, Object> rootMap = new LinkedHashMap<>();
		rootMap.put("$abc", childMap);

		rootMap = new KeyNormalizer().normalize(rootMap);

		Map<String, Object> expectedChildMap = new LinkedHashMap<>();
		expectedChildMap.put("_abc", "1");

		Map<String, Object> expectedRootMap = new LinkedHashMap<>();
		expectedRootMap.put("_abc", expectedChildMap);

		assertEquals(expectedRootMap, rootMap);
	}

	@Test
	public void testNoNormalizationInChildList() {
		List<String> childList = new ArrayList<>();
		childList.add(":abc");

		Map<String, Object> rootMap = new LinkedHashMap<>();
		rootMap.put("$abc", childList);

		rootMap = new KeyNormalizer().normalize(rootMap);

		List<String> expectedChildList = new ArrayList<>();
		expectedChildList.add(":abc");

		Map<String, Object> expectedRootMap = new LinkedHashMap<>();
		expectedRootMap.put("_abc", expectedChildList);

		assertEquals(expectedRootMap, rootMap);
	}

	@Test
	public void testNormalizationInChildListMap() {
		Map<String, Object> childMap = new LinkedHashMap<>();
		childMap.put("$abc", "1");

		List<Object> childList = new ArrayList<>();
		childList.add(childMap);

		Map<String, Object> rootMap = new LinkedHashMap<>();
		rootMap.put("$abc", childList);

		rootMap = new KeyNormalizer().normalize(rootMap);

		Map<String, Object> expectedChildMap = new LinkedHashMap<>();
		expectedChildMap.put("_abc", "1");

		List<Object> expectedChildList = new ArrayList<>();
		expectedChildList.add(expectedChildMap);

		Map<String, Object> expectedRootMap = new LinkedHashMap<>();
		expectedRootMap.put("_abc", expectedChildList);

		assertEquals(expectedRootMap, rootMap);
	}

}
