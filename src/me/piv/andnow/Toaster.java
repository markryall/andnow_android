package me.piv.andnow;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    private Context context;

    public Toaster(Context context) {
        this.context = context;
    }

    public void shortToast(CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
