package me.piv.andnow.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import me.piv.andnow.R;
import me.piv.andnow.data.Session;
import me.piv.andnow.data.SessionRepository;

public class Edit extends Activity implements TextView.OnEditorActionListener {
    private SessionRepository sessionRepository;
    private Session session;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit);
        Bundle extras = getIntent().getExtras();
        session = (Session)extras.getSerializable("session");
        sessionRepository = new SessionRepository(this);
        getTextView(R.id.description).setText(session.getDescription());
        getTextView(R.id.description).setOnEditorActionListener(this);
        getTextView(R.id.count).setText(""+session.getCount());
        getTextView(R.id.count).setOnEditorActionListener(this);
        getTextView(R.id.cost).setText(""+session.getCost());
        getTextView(R.id.cost).setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_ACTION_DONE) update();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                sessionRepository.restart(session);
                finish();
                return true;
            case R.id.destroy:
                sessionRepository.destroy(session);
                finish();
                return true;
        }
        return false;
    }

    private void update() {
        sessionRepository.update(session, toString(R.id.description), toLong(R.id.count), toLong(R.id.cost));
        finish();
    }

    private String toString(int id) {
        return getTextView(id).getText().toString();
    }

    private long toLong(int id) {
        return Long.parseLong(toString(id));
    }

    private TextView getTextView(int id) {
        return (TextView)findViewById(id);
    }
}