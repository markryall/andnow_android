package me.piv;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class StartActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.start);
        Log.i("me.piv.StartActivity", "started");
    }
}