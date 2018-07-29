package io.github.sealor.mediaurlgrabber;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static java.lang.String.format;

public class Json {

	private final Object json;

	public Json(Object jsonObjectOrArray) {
		this.json = jsonObjectOrArray;
		assert isObject() || isArray();
	}

	public boolean isObject() {
		return this.json instanceof JSONObject;
	}

	public boolean isArray() {
		return this.json instanceof JSONArray;
	}

	public String getString(String attributeName) {
		JSONObject jsonObject = (JSONObject) this.json;
		try {
			return (String) jsonObject.get(attributeName);
		} catch (Exception e) {
			String message = format("string attribute '%s' not in '%s'", attributeName, jsonObject);
			throw new RuntimeException(message, e);
		}
	}

	public String getString(int index) {
		JSONArray jsonArray = (JSONArray) this.json;
		try {
			return (String) jsonArray.get(index);
		} catch (Exception e) {
			String message = format("index '%d' not in '%s'", index, jsonArray);
			throw new RuntimeException(message, e);
		}
	}

	public Json getJson(String path) {
		Json currentJson = this;
		for (String pathFragment : path.split("[.\\[\\]]+")) {
			Integer index = tryToParseInt(pathFragment);

			if (index != null) {
				JSONArray jsonArray = (JSONArray) currentJson.json;
				currentJson = new Json(jsonArray.get(index));
			} else {
				JSONObject jsonObject = (JSONObject) currentJson.json;
				currentJson = new Json(jsonObject.get(pathFragment));
			}
		}
		return currentJson;
	}

	private Integer tryToParseInt(String integer) {
		try {
			return Integer.valueOf(integer);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String toString() {
		if (isArray())
			return ((JSONArray) this.json).toJSONString();
		else
			return ((JSONObject) this.json).toJSONString();
	}

}
