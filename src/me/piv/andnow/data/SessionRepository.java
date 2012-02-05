package me.piv.andnow.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static me.piv.andnow.data.Session.*;

public class SessionRepository {
    private static final String[] ALL_COLS = { _ID, START_TIME, END_TIME, DESCRIPTION };
    private static final String[] DESCRIPTION_COL = { DESCRIPTION };
    private SessionData sessionData;

    public SessionRepository(Context context) {
        sessionData = new SessionData(context);
    }

    public void each(SessionConsumer consumer) {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, ALL_COLS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            consumer.consume(new Session(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3)));
        }
    }

    public List<String> getUniqueDescriptions() {
        Cursor cursor = sessionData.getReadableDatabase().query(TABLE_NAME, DESCRIPTION_COL, null, null, DESCRIPTION, null, DESCRIPTION);
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
}