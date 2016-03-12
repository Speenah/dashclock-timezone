package com.malaspina.dashclocktimezone;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;

/**
 * Created by Ryan on 2/24/2016.
 */
public class ExtensionBase extends DashClockExtension {

    private String timezone_key;
    private String hour_format_key;
    private String ext_format_key;

    SharedPreferences prefs;

    @Override
    protected void onInitialize(boolean isReconnect) {
        setUpdateWhenScreenOn(true);
        super.onInitialize(isReconnect);
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
        data.status(getTime());
        data.expandedTitle(getTimezone_key());
        data.expandedBody(timezone);

        publishUpdate(data);
    }

    private String getTime() {
        DateTime now = new DateTime();
        return DateUtils.formatDateTime(this, now, DateUtils.FORMAT_SHOW_TIME);
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
