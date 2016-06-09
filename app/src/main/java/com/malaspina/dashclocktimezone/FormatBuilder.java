package com.malaspina.dashclocktimezone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Ryan on 6/8/2016.
 *
 */
public class FormatBuilder extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView preview, advancedNote;
    private Spinner timeSpinner, dateSpinner;
    private EditText customPattern;
    private CheckBox useAdvanced;
    private Bundle intent;

    private String format;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.format_builder);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
