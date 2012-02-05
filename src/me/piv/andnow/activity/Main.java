package me.piv.andnow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import me.piv.andnow.R;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionConsumer;
import me.piv.andnow.data.SessionRepository;
import me.piv.andnow.transport.SessionUploader;

public class Main extends Activity implements View.OnClickListener, SessionConsumer {
    SessionUploader sessionUploader;

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
            case R.id.list:
                startActivity(new Intent(this, List.class));
        }
        return false;
    }

    private void synchronise() {
        String url = PreferenceManager.getDefaultSharedPreferences(this).getString("server", "")+"/sessions.json";
        sessionUploader = new SessionUploader(url);
        (new SessionRepository(this)).each(this);
    }

    public void consume(Session session) {
        sessionUploader.upload(session);
    }
}