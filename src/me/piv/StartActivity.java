package me.piv;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import me.piv.data.SessionData;

import static me.piv.data.Session.TABLE_NAME;
import static me.piv.data.Session.START_TIME;
import static me.piv.data.Session.DESCRIPTION;

public class StartActivity extends Activity implements View.OnClickListener {
    private SessionData sessions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        sessions = new SessionData(this);
        Log.i("me.piv.StartActivity", "started");
        findViewById(R.id.ok).setOnClickListener(this);
    }

    public void createSession(String description) {
        Log.i("me.piv.StartActivity",description);
        SQLiteDatabase db = sessions.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(START_TIME, System.currentTimeMillis());
        values.put(DESCRIPTION, description);
        db.insertOrThrow(TABLE_NAME, null, values);
    }

    @Override
    public void onClick(View view) {
        EditText edittext = (EditText) findViewById(R.id.description);
        createSession(edittext.getText().toString());
        finish();
    }
}