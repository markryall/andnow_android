package me.piv.andnow.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import me.piv.andnow.R;

public class Preferences extends PreferenceActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}