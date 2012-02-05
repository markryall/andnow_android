package me.piv.andnow.data;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionTest extends TestCase {
    public void testShouldConvertToJSON() throws JSONException {
        Session session = new Session(1, 100, 200, "test");
        JSONObject json = session.toJSON();
        assertEquals("test", json.get("description"));
        assertEquals(100L, json.get("start_time"));
        assertEquals(200L, json.get("end_time"));
    }

    public void testShouldDisplayDescriptionAndStartTime() {
        String string = new Session(1, 100, 200, "test").toString();
        assertEquals("test (10:00 Thu 01 Jan)", string);
    }
}