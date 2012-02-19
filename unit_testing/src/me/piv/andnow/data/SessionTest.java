package me.piv.andnow.data;

import junit.framework.TestCase;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionTest extends TestCase {
    public void testShouldConvertToJSON() throws JSONException {
        Session session = new Session(1, 100, 200, "test", 0, 0, null);
        JSONObject json = session.toJSON();
        assertEquals("test", json.get("description"));
        assertEquals(100L, json.get("start_time"));
        assertEquals(200L, json.get("end_time"));
    }

    public void testShouldDisplayDescriptionAndStartTimeWhenIncomplete() {
        String string = new Session(1, 100, 0, "test", 0, 0, null).toString();
        assertEquals("test (since 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInMillisecondsWhenCompletedInLessThanASecond() {
        String string = new Session(1, 0, 999, "test", 0, 0, null).toString();
        assertEquals("test (999 milliseconds from 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInSecondsWhenCompletedInASecond() {
        String string = new Session(1, 0, 1000, "test", 0, 0, null).toString();
        assertEquals("test (1 seconds from 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInSecondsWhenCompletedInLessThanAMinute() {
        String string = new Session(1, 0, 59 * 1000, "test", 0, 0, null).toString();
        assertEquals("test (59 seconds from 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInMinutesWhenCompletedInAMinute() {
        String string = new Session(1, 0, 60 * 1000, "test", 0, 0, null).toString();
        assertEquals("test (1 minutes from 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInMinutesWhenCompletedInLessThanAnHour() {
        String string = new Session(1, 0, 59 * 60 * 1000, "test", 0, 0, null).toString();
        assertEquals("test (59 minutes from 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInHoursWhenCompletedInAnHour() {
        String string = new Session(1, 0, 60 * 60 * 1000, "test", 0, 0, null).toString();
        assertEquals("test (1 hours from 10:00 Thu 01 Jan)", string);
    }

    public void testShouldDisplayDescriptionStartTimeInHoursWhenCompletedInMoreTHanAnHour() {
        String string = new Session(1, 0, 10 * 60 * 60 * 1000, "test", 0, 0, null).toString();
        assertEquals("test (10 hours from 10:00 Thu 01 Jan)", string);
    }
}