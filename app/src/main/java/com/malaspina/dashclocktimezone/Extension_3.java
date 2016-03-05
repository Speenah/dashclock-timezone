package com.malaspina.dashclocktimezone;

/**
 * Created by Ryan on 3/4/2016.
 */
public class Extension_3 extends ExtensionBase {
    @Override
    public void onCreate() {
        setTimezone_key(getString(R.string.prefs_select_timezone_key_1));
        super.onCreate();
    }
}
