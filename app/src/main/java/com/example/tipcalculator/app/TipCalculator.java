package com.example.tipcalculator.app;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;


public class TipCalculator extends Activity {
    private EditText etTotalAmount;
    private float lastChosenTipPercent;
    private int lastChosenNumWaysSplitting;
    static final String CHOSEN_TIP_PERCENT = "com.example.tipcalculator.app.chosentippercent";
    static final float DEFAULT_TIP_PERCENT = 0.15f;
    static final String CHOSEN_NUM_WAYS_SPLITTING = "com.example.tipcalculator.app.chosennumwayssplitting";
    static final int DEFAULT_NUM_WAYS_SPLITTING = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        etTotalAmount = (EditText) findViewById(R.id.etTotal);
        loadDefaultValues();
        setUpNumberPickers();
        addAmountChangedListener();
    }

    private void loadDefaultValues() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        lastChosenTipPercent = prefs.getFloat(CHOSEN_TIP_PERCENT, DEFAULT_TIP_PERCENT);
        lastChosenNumWaysSplitting = prefs.getInt(CHOSEN_NUM_WAYS_SPLITTING, DEFAULT_NUM_WAYS_SPLITTING);
    }

    private void setUpNumberPickers() {
        NumberPicker npPercent = (NumberPicker) findViewById(R.id.npPercent);
        npPercent.setMaxValue(50);
        npPercent.setMinValue(10);
        npPercent.setValue((int)(lastChosenTipPercent * 100f));
        npPercent.setWrapSelectorWheel(false);
        npPercent.setOnValueChangedListener( new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                lastChosenTipPercent = newVal / 100.0f;
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putFloat(CHOSEN_TIP_PERCENT, lastChosenTipPercent).commit();
                updateTipAmount();

            }
        });
        NumberPicker npNumWaysSplit = (NumberPicker) findViewById(R.id.npNumWaysSplit);
        npNumWaysSplit.setMaxValue(50);
        npNumWaysSplit.setMinValue(1);
        npNumWaysSplit.setValue(lastChosenNumWaysSplitting);
        npNumWaysSplit.setWrapSelectorWheel(false);
        npNumWaysSplit.setOnValueChangedListener( new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int
                    oldVal, int newVal) {
                lastChosenNumWaysSplitting = newVal;
                SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
                editor.putInt(CHOSEN_NUM_WAYS_SPLITTING, lastChosenNumWaysSplitting).commit();
                updateTipAmount();
            }
        });
    }

    private void addAmountChangedListener() {
        etTotalAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateTipAmount();
            }
        });
    }

    private void updateTipAmount() {
        if (etTotalAmount.getText().length() == 0) {
            return;
        }
        double total = 0;
        String totalString = etTotalAmount.getText().toString();
        try {
            total = Double.parseDouble(totalString);
        } catch (NumberFormatException e) {
            // alert the user
            Toast.makeText(this, "Please enter a number amount", Toast.LENGTH_SHORT).show();
            etTotalAmount.setText("");
            return;
        }
        double tip = 0;
        tip = total * lastChosenTipPercent / (double)lastChosenNumWaysSplitting;
        TextView tvTip = (TextView)findViewById(R.id.tvTip);
        DecimalFormat df = new DecimalFormat("####0.00");
        tvTip.setText("Tip is: $" + df.format(tip));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
