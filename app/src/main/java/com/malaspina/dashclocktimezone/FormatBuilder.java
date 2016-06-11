package com.malaspina.dashclocktimezone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Locale;

/**
 * Created by Ryan on 6/8/2016.
 *
 */
public class FormatBuilder extends AppCompatActivity {

    private TextView preview, advancedNote;
    private Spinner time, date;
    private EditText advancedEditText;
    private CheckBox useAdvanced;
    private Bundle intent;

    private String format;
    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.format_builder);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        intent = getIntent().getExtras();
        if (intent == null) {
            intent = new Bundle();
        }

        preview          = (TextView) findViewById(R.id.timeFormatBuilderLivePreviewTextView);
        time             = (Spinner)  findViewById(R.id.timeSpinner);
        date             = (Spinner)  findViewById(R.id.dateSpinner);
        advancedEditText = (EditText) findViewById(R.id.timeFormatBuilderCustomPatternEditText);
        useAdvanced      = (CheckBox) findViewById(R.id.advancedCheckBox);
        advancedNote     = (TextView) findViewById(R.id.advancedNote);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.time_format_builder_preset_entries));

        time.setAdapter(adapter);
        date.setAdapter(adapter);

        time.setOnItemSelectedListener(listener);
        date.setOnItemSelectedListener(listener);

        useAdvanced.setOnCheckedChangeListener(checkedChangeListener);
        advancedEditText.addTextChangedListener(advancedWatcher);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPrefs();
    }

    @Override
    protected void onPause() {
        savePrefs();
        super.onPause();
    }

    TextWatcher advancedWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            format = s.toString();
            System.out.println(format);
            try {
                preview.setText(DateTimeFormat.forPattern(s.toString())
                        .withLocale(Locale.getDefault())
                        .print(new DateTime(System.currentTimeMillis())));
            } catch (IllegalArgumentException e) {
                Toast error = Toast.makeText(FormatBuilder.this, e.getMessage() +
                        getString(R.string.time_format_builder_toast_message_illegal_argument_followup),
                        Toast.LENGTH_SHORT);
                error.setGravity(Gravity.CENTER, 0 ,0);
                error.show();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int visibility = isChecked ? View.VISIBLE : View.INVISIBLE;
            advancedEditText.setVisibility(visibility);
            advancedNote.setVisibility(visibility);
            date.setEnabled(!isChecked);
            time.setEnabled(!isChecked);
            updateFromSpinners();
            advancedEditText.setText(format);
        }
    };

    private void updateFromSpinners() {
        int timePos = time.getSelectedItemPosition();
        int datePos = date.getSelectedItemPosition();

        String[] vars = getResources().getStringArray(R.array.time_format_builder_preset_values);
        String style = vars[datePos] + vars[timePos];

        format = DateTimeFormat.patternForStyle(style, Locale.getDefault()).replaceAll(":ss?", "");

        if (timePos > 0) {
            format = format.replaceAll("h","hh");
        }
        DateTime now = new DateTime(System.currentTimeMillis());

        preview.setText(DateTimeFormat.forPattern(format).withLocale(Locale.getDefault())
                .print(now));
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateFromSpinners();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    private void savePrefs() {
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(intent.getString(Keys.KEY_USE_ADVANCED), useAdvanced.isChecked());
        editor.putString(intent.getString(Keys.KEY_FORMAT), format);
        editor.putInt(intent.getString(Keys.KEY_TIME_SPINNER_POS),
                time.getSelectedItemPosition());
        editor.putInt(intent.getString(Keys.KEY_DATE_SPINNER_POS),
                date.getSelectedItemPosition());

        editor.apply();
    }

    private void loadPrefs() {

        int time_position = preferences.getInt(intent.getString(Keys.KEY_TIME_SPINNER_POS), 0);
        time.setSelection(time_position);
        date.setSelection(preferences.getInt(intent.getString(Keys.KEY_DATE_SPINNER_POS), 0));

        useAdvanced.setChecked(preferences.getBoolean(intent.getString(Keys.KEY_USE_ADVANCED), false));

        format = preferences.getString(intent.getString(Keys.KEY_FORMAT),
                DateTimeFormat.patternForStyle("SS", Locale.getDefault()));
        advancedEditText.setText(format);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
