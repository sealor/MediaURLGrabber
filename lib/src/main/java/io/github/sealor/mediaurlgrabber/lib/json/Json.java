package io.github.sealor.mediaurlgrabber.lib.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static java.lang.String.format;

public class Json {

	private final Object json;

	public Json(Object jsonObjectOrArray) {
		this.json = jsonObjectOrArray;
		assert isObject() || isArray() || isString();
	}

	public boolean isObject() {
		return this.json instanceof JSONObject;
	}

	public boolean isArray() {
		return this.json instanceof JSONArray;
	}

	public boolean isString() {
		return this.json instanceof String;
	}

	public Object get(String attributeName) {
		try {
			JSONObject jsonObject = (JSONObject) this.json;
			return jsonObject.get(attributeName);
		} catch (Exception e) {
			String message = format("attribute '%s' not in '%s'", attributeName, this.json);
			throw new RuntimeException(message, e);
		}
	}

	public Object get(int index) {
		try {
			JSONArray jsonArray = (JSONArray) this.json;
			return jsonArray.get(index);
		} catch (Exception e) {
			String message = format("attribute '%s' not in '%s'", index, this.json);
			throw new RuntimeException(message, e);
		}
	}

	public String getString(String attributeName) {
		try {
			return (String) get(attributeName);
		} catch (Exception e) {
			String message = format("string attribute '%s' not in '%s'", attributeName, this.json);
			throw new RuntimeException(message, e);
		}
	}

	public String getString(int index) {
		try {
			return (String) get(index);
		} catch (Exception e) {
			String message = format("index '%d' not in '%s'", index, this.json);
			throw new RuntimeException(message, e);
		}
	}

	public Json getJson(String path) {
		Json currentJson = this;
		for (String pathFragment : path.split("[.\\[\\]]+")) {
			Integer index = tryToParseInt(pathFragment);

			try {
				if (index != null) {
					JSONArray jsonArray = (JSONArray) currentJson.json;
					currentJson = new Json(jsonArray.get(index));
				} else if (!pathFragment.isEmpty()) {
					JSONObject jsonObject = (JSONObject) currentJson.json;
					currentJson = new Json(jsonObject.get(pathFragment));
				}
			} catch (Throwable e) {
				throw new JsonException(format(
						"Path '%s' ('%s') not found in document:%n%s",
						path, pathFragment, this.json.toString()), e);
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
		else if (isObject())
			return ((JSONObject) this.json).toJSONString();
		else
			return this.json.toString();
	}

}
