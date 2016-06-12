package com.malaspina.dashclocktimezone;

/**
 * Created by Ryan on 3/4/2016.
 */
public class Extension2 extends ExtensionBase {
    @Override
    public void onCreate() {
        setTimezone_key(getString(R.string.prefs_select_timezone_key_2));
        setHour_format_key(getString(R.string.prefs_12_or_24_key_2));
        setUseSystemFormatKey(getString(R.string.prefs_key_use_system_2));
        setCustomFormatKey(getString(R.string.prefs_key_extended_format_2));
        super.onCreate();
    }
}
