package io.github.sealor.mediaurlgrabber.lib.normalizer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class KeyNormalizer {

	@SuppressWarnings("unchecked")
	public Object normalize(Object obj) {
		if (obj instanceof Map)
			return normalize((Map) obj);

		if (obj instanceof List)
			return normalize((List) obj);

		return obj;
	}

	public Map<String, Object> normalize(Map<String, Object> map) {
		Map<String, Object> newMap = new LinkedHashMap<>();

		for (String key : map.keySet()) {
			Object value = map.get(key);

			value = normalize(value);
			key = normalize(key);

			if (newMap.containsKey(key))
				throw new RuntimeException(format("Key '%s' is already used", key));

			newMap.put(key, value);
		}

		return newMap;
	}

	public List normalize(List list) {
		List<Object> newList = new ArrayList<>();

		for (Object value : list) {
			value = normalize(value);
			newList.add(value);

		}

		return newList;
	}

	public String normalize(String key) {
		return key.replaceAll("^[$:(){}\",]", "_").replaceAll("[$:(){}\",]", "-");
	}

}
