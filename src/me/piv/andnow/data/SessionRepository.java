package me.piv.andnow.data;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static me.piv.andnow.data.Session.*;

public class SessionRepository {
    private static final String[] ALL_COLS = { _ID, START_TIME, END_TIME, DESCRIPTION, COUNT, COST };
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

    private Session fromAllColumnCursor(Cursor cursor) {
        int index = 0;
        long id = cursor.getLong(index++);
        long start = cursor.getLong(index++);
        long end = cursor.getLong(index++);
        String description = cursor.getString(index++);
        long count = cursor.getLong(index++);
        long cost = cursor.getLong(index++);
        return new Session(id, start, end, description, count, cost);
    }
    
    public void each(SessionConsumer consumer) {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, ALL_COLS, null, null, null, null, null);
        activity.startManagingCursor(cursor);
        while (cursor.moveToNext()) {
            consumer.consume(fromAllColumnCursor(cursor));
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
            Session session = new Session(cursor.getLong(0), cursor.getLong(1), 0, cursor.getString(2), 0, 0);
            sessions.add(session);
        }
        return sessions;
    }

    public List<Session> getSessions() {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, ALL_COLS, null, null, null, null, ORDER_BY_START_DESC);
        activity.startManagingCursor(cursor);
        List<Session> sessions = new ArrayList<Session>();
        while (cursor.moveToNext()) {
            sessions.add(fromAllColumnCursor(cursor));
        }
        return sessions;
    }

    public void end(Session session) {
        SQLiteDatabase db = sessionData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(END_TIME, System.currentTimeMillis());
        db.update(TABLE_NAME, values, "_ID = " + session.getId(), null);
    }

    public void update(Session session, String description, long count, long cost) {
        SQLiteDatabase db = sessionData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DESCRIPTION, description);
        values.put(COUNT, count);
        values.put(COST, cost);
        db.update(TABLE_NAME, values, "_ID = " + session.getId(), null);
    }

    public void destroy(Session session) {
        SQLiteDatabase db = sessionData.getWritableDatabase();
        db.delete(TABLE_NAME, "_ID = " + session.getId(), null);
    }
}