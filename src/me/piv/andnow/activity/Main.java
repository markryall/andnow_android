package me.piv.andnow.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import me.piv.andnow.R;
import me.piv.andnow.data.SessionData;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import static me.piv.andnow.data.Session.*;

public class Main extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        findViewById(R.id.start_button).setOnClickListener(this);
        findViewById(R.id.stop_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.start_button:
                startActivity(new Intent(this, Start.class));
                return;
            case R.id.stop_button:
                startActivity(new Intent(this, Stop.class));
                return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                startActivity(new Intent(this, Preferences.class));
                return true;
            case R.id.synchronise:
                synchronise();
                return true;
        }
        return false;
    }

    private void synchronise() {
        SessionData sessionData = new SessionData(this);
        SQLiteDatabase db = sessionData.getReadableDatabase();
        String[] cols = { DESCRIPTION, START_TIME, END_TIME };
        Cursor cursor = db.query(TABLE_NAME, cols, null, null, null, null, null);
        String server = PreferenceManager.getDefaultSharedPreferences(this).getString("server", "");
        while (cursor.moveToNext()) {
            try {
                AbstractHttpClient httpClient = new DefaultHttpClient();
                String url = server+"/sessions.json";
                HttpPost postMethod = new HttpPost(url);
                JSONObject session = new JSONObject();
                session.put("description", cursor.getString(0));
                session.put("start_time", cursor.getLong(1));
                session.put("end_time", cursor.getLong(2));
                JSONObject parent = new JSONObject();
                parent.put("session", session);
                String json = parent.toString();
                StringEntity entity = new StringEntity(json, "utf-8");
                entity.setContentType("application/json");
                postMethod.setEntity(entity);
                HttpResponse response = httpClient.execute(postMethod);
                String responseString = response.toString();
                Log.i("me.piv", responseString);
            } catch (Exception e) {
                Log.e("me.piv","error sending message", e);
            }
        }
    }
}