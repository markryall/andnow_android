package me.piv.andnow.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import static android.provider.BaseColumns._ID;
import static me.piv.andnow.data.Session.*;

public class SessionRepository {
    private static final String[] ALL_COLS = { _ID, START_TIME, END_TIME, DESCRIPTION };
    private Context context;

    public SessionRepository(Context context) {
        this.context = context;
    }

    public void each(SessionConsumer consumer) {
        SessionData sessionData = new SessionData(context);
        SQLiteDatabase db = sessionData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, ALL_COLS, null, null, null, null, null);
        while (cursor.moveToNext()) {
            consumer.consume(new Session(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), cursor.getString(3)));
        }
    }
}
