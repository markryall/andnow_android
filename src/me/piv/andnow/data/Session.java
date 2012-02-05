package me.piv.andnow.data;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Session {
    public static final String TABLE_NAME = "sessions";
    public static final String DESCRIPTION = "description";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
    public static final String FORMAT = "HH:mm EEE dd MMM";
    public static final SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
    private long id;
    private long start;
    private long end;
    private String description;

    public Session(long id, long start, long end, String description) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.description = description;
    }

    @Override
    public String toString() {
        String startDate = formatter.format(new Date(start));
        if (end <= 0) {
            return String.format("%s (since %s)", description, startDate);
        }
        long duration = end - start;
        if (duration < 1000) {
            return String.format("%s (%d milliseconds from %s)", description, duration, startDate);
        }
        duration /= 1000;
        if (duration < 60) {
            return String.format("%s (%d seconds from %s)", description, duration, startDate);
        }
        duration /= 60;
        if (duration < 60) {
            return String.format("%s (%d minutes from %s)", description, duration, startDate);
        }
        duration /= 60;
        if (duration < 60) {
            return String.format("%s (%d hours from %s)", description, duration, startDate);
        }
        return "";
    }

    public long getId() {
        return id;
    }
 
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("start_time", start);
        json.put("end_time", end);
        return json;
    }
}
