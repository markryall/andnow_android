package me.piv;

import android.test.ActivityInstrumentationTestCase2;
import me.piv.andnow.activity.Main;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class me.piv.HomeActivityTest \
 * me.piv.tests/android.test.InstrumentationTestRunner
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<Main> {

    public HomeActivityTest() {
        super("me.piv", Main.class);
    }

}
