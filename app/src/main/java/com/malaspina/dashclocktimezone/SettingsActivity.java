package com.malaspina.dashclocktimezone;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p/>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatPreferenceActivity {
    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener
            = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // Get selected position to set summary
                ListPreference tempListPreference = (ListPreference) preference;
                int index = tempListPreference.findIndexOfValue(stringValue);
                preference.setSummary(tempListPreference.getEntries()[index]);
            } else {
                // Set summary for all other preferences
                preference.setSummary(stringValue);
            }

            return true;
        }
    };

        /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     * Binds a preference's summary to its value. More specifically, when the
     * preference's value is changed, its summary (line of text below the
     * preference title) is updated to reflect the value. The summary is also
     * immediately updated upon calling this method. The exact display format is
     * dependent on the type of preference.
     *
     * @see #sBindPreferenceSummaryToValueListener
     */
    private static void bindPreferenceSummaryToValue(Preference preference, String default_value) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), default_value));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || SettingsFragment1.class.getName().equals(fragmentName)
                || SettingsFragment2.class.getName().equals(fragmentName)
                || SettingsFragment3.class.getName().equals(fragmentName);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class BaseSettingsFragment extends PreferenceFragment {

        private String timezone_key;
        private String hour_format_key;
        private String ext_format_key;
        private int prefs_xml;

        private Preference timeFormatBuilderLauncher;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(getPrefs_xml());

            Log.i("BaseSettings key", getTimezone_key());
            bindPreferenceSummaryToValue(findPreference(getTimezone_key()),
                    getString(R.string.default_timezone));
            bindPreferenceSummaryToValue(findPreference(getHour_format_key()),
                    getString(R.string.default_hour_format));
            bindPreferenceSummaryToValue(findPreference(getExt_format_key()),
                    getString(R.string.default_extended_format));

            timeFormatBuilderLauncher = findPreference(getString(
                    R.string.prefs_time_builder_key));
            timeFormatBuilderLauncher.setOnPreferenceClickListener(onPreferenceClickListener);
        }

        @Override
        public void onResume() {
            super.onResume();
            // FIXME: 6/12/2016 Find a better way to set custom format summary
            timeFormatBuilderLauncher.setSummary(PreferenceManager
                    .getDefaultSharedPreferences(getActivity())
                    .getString(getString(R.string.prefs_key_extended_format)
                            + getTFBSuffixFromTimezoneKey(getTimezone_key()), ""));
        }

        public String getTimezone_key() {
            return timezone_key;
        }

        public void setTimezone_key(String timezone_key) {
            this.timezone_key = timezone_key;
        }

        public int getPrefs_xml() {
            return prefs_xml;
        }

        public void setPrefs_xml(int prefs_xml) {
            this.prefs_xml = prefs_xml;
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

        public void setTitle(int title) {
            this.getActivity().setTitle(title);
        }

        private Preference.OnPreferenceClickListener onPreferenceClickListener
                = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals(getString(R.string.prefs_time_builder_key))) {
                    Intent i = new Intent(getActivity(), FormatBuilder.class);
                    i.putExtra("suffix", getTFBSuffixFromTimezoneKey(getTimezone_key()));
                    startActivity(i);
                    return true;
                }
                return false;
            }
        };

        private String getTFBSuffixFromTimezoneKey(String timezoneKey) {
            String lastLetter = timezoneKey.substring(timezoneKey.length() - 1);
            if (lastLetter.equals("e")) {
                return "";
            } else {
                return lastLetter;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SettingsFragment1 extends BaseSettingsFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            setTimezone_key(getString(R.string.prefs_select_timezone_key_1));
            setHour_format_key(getString(R.string.prefs_12_or_24_key_1));
            setExt_format_key(getString(R.string.prefs_system_or_custom_key_1));
            setPrefs_xml(R.xml.pref_base_1);
            setTitle(R.string.prefs_title_1);
            super.onCreate(savedInstanceState);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SettingsFragment2 extends BaseSettingsFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            setTimezone_key(getString(R.string.prefs_select_timezone_key_2));
            setHour_format_key(getString(R.string.prefs_12_or_24_key_2));
            setExt_format_key(getString(R.string.prefs_system_or_custom_key_2));
            setPrefs_xml(R.xml.pref_base_2);
            setTitle(R.string.prefs_title_2);
            super.onCreate(savedInstanceState);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class SettingsFragment3 extends BaseSettingsFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            setTimezone_key(getString(R.string.prefs_select_timezone_key_3));
            setHour_format_key(getString(R.string.prefs_12_or_24_key_3));
            setExt_format_key(getString(R.string.prefs_system_or_custom_key_3));
            setPrefs_xml(R.xml.pref_base_3);
            setTitle(R.string.prefs_title_3);
            super.onCreate(savedInstanceState);
        }
    }
}
