package com.malaspina.dashclocktimezone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Ryan on 6/8/2016.
 *
 */
public class FormatBuilder extends AppCompatActivity
        implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private TextView preview, advancedNote;
    private Spinner timeSpinner, dateSpinner;
    private EditText customPattern;
    private CheckBox useAdvanced;
    private Bundle bundle;

    private String format, prefPrefix;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.format_builder);
        setupActionBar();

        bundle = getIntent().getExtras();
        if (bundle == null) {
            bundle = new Bundle();
        }

        preview = (TextView) findViewById(R.id.timeFormatBuilderLivePreviewTextView);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        customPattern = (EditText) findViewById(R.id.timeFormatBuilderCustomPatternEditText);
        useAdvanced = (CheckBox) findViewById(R.id.advancedCheckBox);
        advancedNote = (TextView) findViewById(R.id.advancedNote);

        useAdvanced.setOnCheckedChangeListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        prefPrefix = bundle.getString("id");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.time_format_builder_preset_entries));

        timeSpinner.setAdapter(adapter);
        dateSpinner.setAdapter(adapter);

        customPattern.addTextChangedListener(customPatternWatcher);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    TextWatcher customPatternWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private loadPreferences() {
        useAdvanced.setChecked(preferences.getBoolean(prefPrefix +
                getString(R.string.prefs_use_advanced_checked_key_suffix), false));
    }
}
