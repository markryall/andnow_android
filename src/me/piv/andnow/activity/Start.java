package me.piv.andnow.activity;

import android.app.Activity;
import android.content.Intent;
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
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionRepository;
import java.util.List;

public class Start extends Activity implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener {
    private SessionRepository sessionRepository;
    private List<String> activities;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        sessionRepository = new SessionRepository(this);
        activities = sessionRepository.getUniqueDescriptions();
        getListView().setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, activities));
        getTextView().setOnEditorActionListener(this);
        getListView().setOnItemClickListener(this);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    private void confirm(TextView textView) {
        createSession(textView.getText().toString());
    }

    private ListView getListView() {
        return (ListView)findViewById(R.id.activities);
    }

    private TextView getTextView() {
        return (TextView)findViewById(R.id.description);
    }

    private void createSession(String description) {
        Session session = sessionRepository.start(description, System.currentTimeMillis());
        Intent intent = new Intent(this, Edit.class);
        intent.putExtra("session", session);
        startActivity(intent);
    }
}