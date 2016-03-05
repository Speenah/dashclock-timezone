package com.malaspina.dashclocktimezone;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

/**
 * Created by Ryan on 2/24/2016.
 */
public class ExtensionBase extends DashClockExtension {

    private String timezone_key;

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
}
