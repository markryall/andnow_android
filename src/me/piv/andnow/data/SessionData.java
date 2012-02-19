package me.piv.andnow.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static me.piv.andnow.data.Session.*;

public class SessionData extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "session_data.db";
    private static final int DATABASE_VERSION = 2;

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
            END_TIME + " INTEGER," +
            COUNT + " INTEGER," +
            COST + " INTEGER," +
            NOTES + " TEXT" +
            ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int actual, int expected) {
    }
}
