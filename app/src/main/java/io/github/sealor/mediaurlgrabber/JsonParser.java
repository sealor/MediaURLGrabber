package io.github.sealor.mediaurlgrabber;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class JsonParser {

    protected final JSONParser parser = new JSONParser();

    public Json parse(String jsonString) {
        try {
            return new Json(this.parser.parse(jsonString));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Json parse(Reader jsonReader) {
        try {
            return new Json(this.parser.parse(jsonReader));
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Json parse(InputStream jsonInputStream) {
        try {
            return new Json(this.parser.parse(new InputStreamReader(jsonInputStream)));
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
