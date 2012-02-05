package me.piv.andnow.data;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionTest extends TestCase {
    public void testShouldConvertToJSON() throws JSONException {
        Session session = new Session(1, 100, 200, "test");
        JSONObject json = session.toJSON();
        assertEquals(json.get("description"), "test");
        assertEquals(json.get("start_time"), 100L);
        assertEquals(json.get("end_time"), 200L);
    }
}
