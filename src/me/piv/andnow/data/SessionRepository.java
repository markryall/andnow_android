package me.piv.andnow.data;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static me.piv.andnow.data.Session.*;

public class SessionRepository {
    private static final String[] ALL_COLS = { _ID, START_TIME, END_TIME, DESCRIPTION };
    private static final String[] ALL_EXCEPT_END = { _ID, START_TIME, DESCRIPTION };
    private static final String[] DESCRIPTION_COL = { DESCRIPTION };
    private static String WHERE_END_IS_NULL = END_TIME + " IS NULL";
    private static String ORDER_BY_START_DESC = START_TIME + " DESC";
    private SessionData sessionData;
    private Activity activity;

    public SessionRepository(Activity activity) {
        this.activity = activity;
        sessionData = new SessionData(activity);
    }

    public void each(SessionConsumer consumer) {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, ALL_COLS, null, null, null, null, null);
        activity.startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            consumer.consume(new Session(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3)));
        }
    }

    public List<String> getUniqueDescriptions() {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, DESCRIPTION_COL, null, null, DESCRIPTION, null, DESCRIPTION);
        activity.startManagingCursor(cursor);
        List<String> activities = new ArrayList<String>();
        while (cursor.moveToNext()) activities.add(cursor.getString(0));
        return activities;
    }

    public long start(String description, long start) {
        ContentValues values = new ContentValues();
        values.put(START_TIME, start);
        values.put(DESCRIPTION, description);
        return sessionData.getWritableDatabase().insertOrThrow(TABLE_NAME, null, values);
    }

    public List<Session> getIncompleteSessions() {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, ALL_EXCEPT_END, WHERE_END_IS_NULL, null, null, null, ORDER_BY_START_DESC);
        activity.startManagingCursor(cursor);
        List<Session> sessions = new ArrayList<Session>();
        while (cursor.moveToNext()) {
            Session session = new Session(cursor.getLong(0), cursor.getLong(1), 0, cursor.getString(2));
            sessions.add(session);
        }
        return sessions;
    }

    public void end(Session session) {
        SQLiteDatabase db = sessionData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(END_TIME, System.currentTimeMillis());
        db.update(TABLE_NAME, values, "_ID = " + session.getId(), null);
    }
}