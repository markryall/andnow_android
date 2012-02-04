package me.piv.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static me.piv.data.Session.TABLE_NAME;
import static me.piv.data.Session.DESCRIPTION;
import static me.piv.data.Session.START_TIME;
import static me.piv.data.Session.END_TIME;

public class SessionData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "session_data.db";
    private static final int DATABASE_VERSION = 1;

    public SessionData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DESCRIPTION + " TEXT NOT NULL," +
            START_TIME + " INTEGER," +
            END_TIME + " INTEGER" +
            ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
