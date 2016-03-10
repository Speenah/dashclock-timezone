package com.malaspina.dashclocktimezone;

/**
 * Created by Ryan on 3/4/2016.
 */
public class Extension_2 extends ExtensionBase {
    @Override
    public void onCreate() {
        setTimezone_key(getString(R.string.prefs_select_timezone_key_2));
        setHour_format(getString(R.string.prefs_12_or_24_key_2));
        setExt_format(getString(R.string.prefs_system_or_custom_key_2));
        super.onCreate();
    }
}
