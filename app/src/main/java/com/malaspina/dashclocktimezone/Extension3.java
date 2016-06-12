package com.malaspina.dashclocktimezone;

/**
 * Created by Ryan on 3/4/2016.
 */
public class Extension3 extends ExtensionBase {
    @Override
    public void onCreate() {
        setTimezone_key(getString(R.string.prefs_select_timezone_key_3));
        setHour_format_key(getString(R.string.prefs_12_or_24_key_3));
        setUseSystemFormatKey(getString(R.string.prefs_key_use_system_3));
        setCustomFormatKey(getString(R.string.prefs_key_extended_format_3));
        super.onCreate();
    }
}
