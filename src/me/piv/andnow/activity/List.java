package me.piv.andnow.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionRepository;

public class List extends ListActivity {
    private SessionRepository sessionRepository;
    private java.util.List<Session> sessions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sessionRepository = new SessionRepository(this);
        reload();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this, Edit.class);
        intent.putExtra("session", sessions.get(position));
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reload();
    }

    private void reload() {
        sessions = sessionRepository.getSessions();
        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, sessions));
    }
}