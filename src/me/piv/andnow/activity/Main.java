package me.piv.andnow.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import me.piv.andnow.R;
import me.piv.andnow.Toaster;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionConsumer;
import me.piv.andnow.data.SessionRepository;
import me.piv.andnow.transport.SessionUploader;

public class Main extends Activity implements View.OnClickListener, SessionConsumer {
    SessionRepository sessionRepository;
    SessionUploader sessionUploader;
    Toaster toaster;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        sessionRepository = new SessionRepository(this);
        toaster = new Toaster(this);
        findViewById(R.id.start_button).setOnClickListener(this);
        findViewById(R.id.list_button).setOnClickListener(this);
        findViewById(R.id.stop_button).setOnClickListener(this);
        refresh();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refresh();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.start_button:
                if (sessionRepository.hasIncompleteSessions()) {
                    promptToFinishMostRecentSession();
                } else {
                    launchStart();
                }
                return;
            case R.id.list_button:
                startActivity(new Intent(this, List.class));
                return;
            case R.id.stop_button:
                startActivity(new Intent(this, Stop.class));
                return;
        }
    }

    private void launchStart() {
        startActivity(new Intent(this, Start.class));
    }

    private void promptToFinishMostRecentSession() {
        final Session session = sessionRepository.getIncompleteSessions().get(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Have you finished "+session.getDescription()+" ?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sessionRepository.end(session);
                    launchStart();
                }
            })
            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    launchStart();
                }
            });
        builder.create().show();
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
            case R.id.upload:
                upload();
                return true;
        }
        return false;
    }

    private void refresh() {
        findViewById(R.id.list_button).setEnabled(sessionRepository.hasSessions());
        findViewById(R.id.stop_button).setEnabled(sessionRepository.hasIncompleteSessions());
    }

    private void upload() {
        String token = PreferenceManager.getDefaultSharedPreferences(this).getString("token","exampletoken");
        sessionUploader = new SessionUploader(token);
        sessionRepository.each(this);
    }

    public void consume(Session session) {
        try {
            if (sessionUploader.upload(session)) {
                toaster.shortToast("Successfully uploaded "+session.toString());
                sessionRepository.destroy(session);
            } else {
                toaster.shortToast("Failed to upload "+session.toString()+" check token is set correctly");
            }
        } catch (Exception e) {
            toaster.shortToast("Failed to upload "+session.toString()+" ("+e.getMessage()+")");
            Log.e("me.piv", "error sending session", e);
        }
    }
}