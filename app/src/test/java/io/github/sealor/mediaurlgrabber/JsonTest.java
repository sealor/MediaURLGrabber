package io.github.sealor.mediaurlgrabber;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonTest {

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

}
