package com.malaspina.dashclocktimezone;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

/**
 * Created by Ryan on 2/24/2016.
 */
public class ExtensionBase extends DashClockExtension {

    private String timezone_key;
    private String hour_format;
    private String ext_format;

    @Override
    protected void onInitialize(boolean isReconnect) {
        setUpdateWhenScreenOn(true);
        super.onInitialize(isReconnect);
    }

    @Override
    protected void onUpdateData(int reason) {
        ExtensionData data = new ExtensionData();

        data.visible(true);
        data.icon(R.mipmap.ic_stat);
        data.status("Hi!");
        data.expandedTitle(getTimezone_key());
        data.expandedBody("Hello World! (Again!)");

        publishUpdate(data);
    }

    public String getTimezone_key() {
        return timezone_key;
    }

    public void setTimezone_key(String timezone_key) {
        this.timezone_key = timezone_key;
    }

    public String getHour_format() {
        return hour_format;
    }

    public void setHour_format(String hour_format) {
        this.hour_format = hour_format;
    }

    public String getExt_format() {
        return ext_format;
    }

    public void setExt_format(String ext_format) {
        this.ext_format = ext_format;
    }
}
