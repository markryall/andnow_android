package me.piv.andnow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import me.piv.andnow.R;
import me.piv.andnow.data.SessionRepository;
import java.util.List;

public class Start extends Activity implements TextView.OnEditorActionListener, View.OnClickListener, AdapterView.OnItemClickListener {
    private SessionRepository sessionRepository;
    private List<String> activities;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        sessionRepository = new SessionRepository(this);
        loadData();
        findViewById(R.id.ok).setOnClickListener(this);
        ((TextView)findViewById(R.id.description)).setOnEditorActionListener(this);
        getListView().setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        createSession(activities.get(i));
        finish();
    }

    private void confirm(TextView textView) {
        createSession(textView.getText().toString());
        finish();
    }

    private void loadData() {
        activities = sessionRepository.getUniqueDescriptions();
        getListView().setAdapter(new ArrayAdapter(this, R.layout.start_list_item, activities));
    }

    private ListView getListView() {
        return (ListView)findViewById(R.id.activities);
    }

    private void createSession(String description) {
        sessionRepository.start(description, System.currentTimeMillis());
    }
}