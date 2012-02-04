package me.piv.data;

import android.database.Cursor;

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
}
