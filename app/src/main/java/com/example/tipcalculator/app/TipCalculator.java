package com.example.tipcalculator.app;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class TipCalculator extends Activity {
    private EditText etTotalAmount;
    private enum TipPercentOption {
        PERCENT10, PERCENT15, PERCENT20
    }
    private TipPercentOption lastChosenTipPercent;
    private Button bn10;
    private Button bn15;
    private Button bn20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
        etTotalAmount = (EditText) findViewById(R.id.etTotal);
        bn10 = (Button) findViewById(R.id.bn10);
        bn15 = (Button) findViewById(R.id.bn15);
        bn20 = (Button) findViewById(R.id.bn20);
        lastChosenTipPercent = TipPercentOption.PERCENT15; // as default
        addAmountChangedListener();

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
        if (lastChosenTipPercent == TipPercentOption.PERCENT10) {
            tip = total * 0.1;
        } else if (lastChosenTipPercent == TipPercentOption.PERCENT15) {
            tip = total * 0.15;
        } else if (lastChosenTipPercent == TipPercentOption.PERCENT20) {
            tip = total * 0.2;
        }
        TextView tvTip = (TextView)findViewById(R.id.tvTip);
        tvTip.setText("Tip is: $" + tip);
    }

    public void onPercentChosen(View view) {
        switch (view.getId()) {
            case R.id.bn10:
                lastChosenTipPercent = TipPercentOption.PERCENT10;
                break;
            case R.id.bn15:
                lastChosenTipPercent = TipPercentOption.PERCENT15;
                break;
            case R.id.bn20:
                lastChosenTipPercent = TipPercentOption.PERCENT20;
                break;
            default:
                break;
        }
        updateTipAmount();
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
