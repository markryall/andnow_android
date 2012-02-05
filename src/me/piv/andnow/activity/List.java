package me.piv.andnow.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionRepository;

public class List extends ListActivity {
    private SessionRepository sessionRepository;
    private java.util.List<Session> sessions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sessionRepository = new SessionRepository(this);
        sessions = sessionRepository.getSessions();
        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, sessions));
    }
}