package me.piv.andnow.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import me.piv.R;
import me.piv.andnow.data.SessionData;

import java.util.ArrayList;
import java.util.List;

import static me.piv.andnow.data.Session.TABLE_NAME;
import static me.piv.andnow.data.Session.START_TIME;
import static me.piv.andnow.data.Session.DESCRIPTION;

public class Start extends Activity implements TextView.OnEditorActionListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private SessionData sessionData;
    private List<String> activities;
    private static String[] FROM = { DESCRIPTION };
    private static String ORDER_BY = DESCRIPTION;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        sessionData = new SessionData(this);
        loadData();
        findViewById(R.id.ok).setOnClickListener(this);
        ((TextView)findViewById(R.id.description)).setOnEditorActionListener(this);
        getListView().setOnItemClickListener(this);
    }

    public void loadData() {
        Cursor cursor = getDescriptionCursor();
        activities = new ArrayList<String>();
        while (cursor.moveToNext()) activities.add(cursor.getString(0));
        getListView().setAdapter(new ArrayAdapter(this, R.layout.start_list_item, activities));
    }

    private ListView getListView() {
        return (ListView)findViewById(R.id.activities);
    }

    private Cursor getDescriptionCursor() {
        SQLiteDatabase db = sessionData.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, FROM, null, null, DESCRIPTION, null, ORDER_BY);
        startManagingCursor(cursor);
        return cursor;
    }

    public void createSession(String description) {
        SQLiteDatabase db = sessionData.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(START_TIME, System.currentTimeMillis());
        values.put(DESCRIPTION, description);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    @Override
    public void onClick(View view) {
       confirm((TextView)findViewById(R.id.description));
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) confirm(textView);
        return true;
    }

    private void confirm(TextView textView) {
        createSession(textView.getText().toString());
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        createSession(activities.get(i));
        finish();
    }
}