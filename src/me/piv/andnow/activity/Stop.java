package me.piv.andnow.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import me.piv.andnow.R;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionRepository;
import java.util.List;

public class Stop extends ListActivity implements AdapterView.OnItemClickListener {
    private SessionRepository sessionRepository;
    private List<Session> sessions;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        sessionRepository = new SessionRepository(this);
        sessions = sessionRepository.getIncompleteSessions();
        setListAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, sessions));
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        sessionRepository.end(sessions.get(i));
        finish();
    }
}