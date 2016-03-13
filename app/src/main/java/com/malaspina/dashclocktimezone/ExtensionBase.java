package com.malaspina.dashclocktimezone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by Ryan on 2/24/2016.
 */
public class ExtensionBase extends DashClockExtension {

    private String timezone_key;
    private String hour_format_key;
    private String ext_format_key;

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
        String extFormat  = prefs.getString(getExt_format_key(),
                getString(R.string.default_extended_format));

        ExtensionData data = new ExtensionData();

        data.visible(true);
        data.icon(R.mipmap.ic_stat);
        data.status(getTime(timezone));
        data.expandedTitle(getTime(timezone));
        data.expandedBody(timezone);

        publishUpdate(data);
    }

    private String getTime(String timezone) {
        DateTime now = new DateTime();
        DateTimeZone tz = DateTimeZone.forID(timezone);
        DateTime now_tz = now.toDateTime(tz);
        return DateUtils.formatDateTime(this, now_tz, DateUtils.FORMAT_SHOW_TIME);
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

    public String getExt_format_key() {
        return ext_format_key;
    }

    public void setExt_format_key(String ext_format_key) {
        this.ext_format_key = ext_format_key;
    }
}
