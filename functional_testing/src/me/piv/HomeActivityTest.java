package me.piv;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.Smoke;
import com.jayway.android.robotium.solo.Solo;
import me.piv.andnow.activity.Main;

public class HomeActivityTest extends ActivityInstrumentationTestCase2<Main> {
    private Solo solo;

    public HomeActivityTest() {
        super("me.piv", Main.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Smoke
    public void testClickStart() throws Exception {
       // do some stuff here
    }
}
