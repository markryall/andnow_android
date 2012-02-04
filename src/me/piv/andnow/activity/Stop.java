package me.piv.andnow.activity;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import me.piv.andnow.R;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionData;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static me.piv.andnow.data.Session.TABLE_NAME;
import static me.piv.andnow.data.Session.START_TIME;
import static me.piv.andnow.data.Session.END_TIME;
import static me.piv.andnow.data.Session.DESCRIPTION;

public class Stop extends ListActivity implements AdapterView.OnItemClickListener {
    private static String[] FROM = { _ID, START_TIME, DESCRIPTION };
    private static String ORDER_BY = START_TIME + " DESC";
    private static String WHERE = END_TIME + " IS NULL";

    private SessionData sessionData;
    private List sessions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sessionData = new SessionData(this);
        populateSessions();
        getListView().setOnItemClickListener(this);
    }

    private void populateSessions() {
        Cursor cursor = getIncompleteSessions();
        sessions = new ArrayList<Session>();
        while (cursor.moveToNext()) {
            Session session = new Session(cursor.getLong(0), cursor.getLong(1), 0, cursor.getString(2));
            sessions.add(session);
        }
        setListAdapter(new ArrayAdapter(this, R.layout.stop_list_item, sessions));
    }

    private Cursor getIncompleteSessions() {
        SQLiteDatabase db = sessionData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, WHERE, null, null, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SQLiteDatabase db = sessionData.getWritableDatabase();
        Session session = (Session)sessions.get(i);
        ContentValues values = new ContentValues();
        values.put(END_TIME, System.currentTimeMillis());
        db.update(TABLE_NAME, values, "_ID = " + session.getId(), null);
        finish();
    }
}