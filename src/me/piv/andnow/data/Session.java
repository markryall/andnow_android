package me.piv.andnow.data;

import android.database.Cursor;
import org.json.JSONException;
import org.json.JSONObject;

public class Session {
    public static final String TABLE_NAME = "sessions";
    public static final String DESCRIPTION = "description";
    public static final String START_TIME = "start_time";
    public static final String END_TIME = "end_time";
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
        return description + " (" + start + ")";
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
