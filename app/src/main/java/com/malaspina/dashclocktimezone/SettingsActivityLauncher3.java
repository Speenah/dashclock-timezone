package com.malaspina.dashclocktimezone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Ryan on 6/6/2016.
 */
public class SettingsActivityLauncher3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT,
                SettingsActivity.SettingsFragment3.class.getName());
        startActivity(intent);

        finish();
    }
}
