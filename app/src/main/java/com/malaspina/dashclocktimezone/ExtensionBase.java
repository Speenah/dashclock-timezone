package com.malaspina.dashclocktimezone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Created by Ryan on 2/24/2016.
 *
 */
public class ExtensionBase extends DashClockExtension {

    private String timezone_key;
    private String hour_format_key;
    private String use_system_format_key;
    private String customFormatKey;

    SharedPreferences prefs;

    private final static IntentFilter INTENT_FILTER;

    static {
        INTENT_FILTER = new IntentFilter();
        INTENT_FILTER.addAction(Intent.ACTION_TIME_TICK);
        INTENT_FILTER.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        INTENT_FILTER.addAction(Intent.ACTION_TIME_CHANGED);
    }

    @Override
    protected void onInitialize(boolean isReconnect) {
        setUpdateWhenScreenOn(true);
        super.onInitialize(isReconnect);
        registerReceiver(mTimeInfoReceiver, INTENT_FILTER);
    }

    @Override
    protected void onUpdateData(int reason) {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String timezone = prefs.getString(getTimezone_key(),
                getString(R.string.default_timezone));
        String hourFormat = prefs.getString(getHour_format_key(),
                getString(R.string.default_hour_format));
        boolean useSystemFormat = prefs.getBoolean(getUseSystemFormatKey(), false);

        String customFormat = prefs.getString(getCustomFormatKey(),
                DateTimeFormat.patternForStyle("MS", Locale.getDefault()));

        ExtensionData data = new ExtensionData();

        data.visible(true);
        data.icon(R.mipmap.ic_stat);
        data.status(getTime(timezone, hourFormat));

        if (useSystemFormat) {
            data.expandedTitle(getExtendedTime(timezone, hourFormat));
        } else {
            data.expandedTitle(getTime(timezone, customFormat));
        }

        data.expandedBody(timezone);

        publishUpdate(data);
    }

    /**
     * Get the time for the timezone formatted properly
     * @param timezone The timezone
     * @param pattern A DateTime format pattern
     * @return
     */
    private String getTime(String timezone, String pattern) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.print(getDateTime(timezone));
    }

    /**
     * Uses getTime but with the system's date format included in the pattern
     * @param timezone The timezone
     * @param hourFormat The format for the hours
     * @return
     */
    private String getExtendedTime(String timezone, String hourFormat) {
        String pattern = DateTimeFormat.patternForStyle("M-", Locale.getDefault())
                + " " + hourFormat;
        return getTime(timezone, pattern);
    }

    /**
     * Returns a DateTime object that can be used with DateTimeFormatter
     * @param timezone The timezone
     * @return A Joda Time DateTime Object
     */
    private DateTime getDateTime(String timezone) {
        DateTime now = new DateTime();
        DateTimeZone tz = DateTimeZone.forID(timezone);
        return now.toDateTime(tz);
    }

    // If time changed, update extension
    private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(Intent.ACTION_TIME_TICK) ||
                    action.equals(Intent.ACTION_TIME_CHANGED) ||
                    action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
                onUpdateData(0);
            }
        }
    };

    @Override
    public void onDestroy() {
        unregisterReceiver(mTimeInfoReceiver);
        super.onDestroy();
    }

    public String getTimezone_key() {
        return timezone_key;
    }

    public void setTimezone_key(String timezone_key) {
        this.timezone_key = timezone_key;
    }

    public String getHour_format_key() {
        return hour_format_key;
    }

    public void setHour_format_key(String hour_format_key) {
        this.hour_format_key = hour_format_key;
    }

    public String getUseSystemFormatKey() {
        return use_system_format_key;
    }

    public void setUseSystemFormatKey(String use_system_format_key) {
        this.use_system_format_key = use_system_format_key;
    }

    public String getCustomFormatKey() {
        return customFormatKey;
    }

    public void setCustomFormatKey(String customFormatKey) {
        this.customFormatKey = customFormatKey;
    }
}
