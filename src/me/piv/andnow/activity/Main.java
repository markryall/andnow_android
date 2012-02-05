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
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionConsumer;
import me.piv.andnow.data.SessionData;
import me.piv.andnow.data.SessionRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import static me.piv.andnow.data.Session.*;

public class Main extends Activity implements View.OnClickListener, SessionConsumer {
    String url;

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
        url = PreferenceManager.getDefaultSharedPreferences(this).getString("server", "")+"/sessions.json";
        (new SessionRepository(this)).each(this);
    }

    public void consume(Session session) {
        try {
            AbstractHttpClient httpClient = new DefaultHttpClient();
            HttpPost postMethod = new HttpPost(url);
            JSONObject parent = new JSONObject();
            parent.put("session", session.toJSON());
            StringEntity entity = new StringEntity(parent.toString(), "utf-8");
            entity.setContentType("application/json");
            postMethod.setEntity(entity);
            HttpResponse response = httpClient.execute(postMethod);
            String responseString = response.toString();
            Log.i("me.piv", responseString);
        } catch (Exception e) {
            Log.e("me.piv","error sending session", e);
        }
    }
}