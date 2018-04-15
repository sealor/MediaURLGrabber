package io.github.sealor.mediaurlgrabber;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Json {

    private final Object json;

    public Json(Object jsonObjectOrArray) {
        assert jsonObjectOrArray instanceof JSONObject || jsonObjectOrArray instanceof JSONArray;
        this.json = jsonObjectOrArray;
    }

    public String getString(String attributeName) {
        JSONObject jsonObject = (JSONObject) this.json;
        return (String) jsonObject.get(attributeName);
    }

    public String getString(int index) {
        JSONArray jsonArray = (JSONArray) this.json;
        return (String) jsonArray.get(index);
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
        if (this.json instanceof JSONArray)
            return ((JSONArray) this.json).toJSONString();
        else
            return ((JSONObject) this.json).toJSONString();
    }

}
