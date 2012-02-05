package me.piv.andnow.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
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
        getTextView().setText(session.getDescription());
        getTextView().setOnEditorActionListener(this);
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if(i == EditorInfo.IME_NULL && keyEvent.getAction() == KeyEvent.ACTION_DOWN) updateDescription(textView);
        return true;
    }

    private void updateDescription(TextView textView) {
        sessionRepository.updateDescription(session, textView.getText().toString());
        finish();
    }

    private TextView getTextView() {
        return (TextView)findViewById(R.id.description);
    }
}