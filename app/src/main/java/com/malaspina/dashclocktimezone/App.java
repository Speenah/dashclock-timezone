package com.malaspina.dashclocktimezone;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Ryan on 2/24/2016.
 */
public class App extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        JodaTimeAndroid.init(context);
    }

    public static Context getContext() {
        return context;
    }
}
