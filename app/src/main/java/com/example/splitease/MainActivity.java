package com.example.splitease;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;


public class MainActivity extends AppCompatActivity {
private EditText t1,t2,t3,t4;
private RadioGroup radioGroup;
private TextView tx1;
LinearLayout l1,l2;
Button b1;
MaterialToolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.editTextBillAmount);
        t2 = findViewById(R.id.editTextNumber);
        t3 = findViewById(R.id.editTipAmount);
        t4 = findViewById(R.id.editTipPercent);
        tx1 = findViewById(R.id.TextviewNumberDecimal);
        l1 = findViewById(R.id.layoutAmountInput);
        l2 = findViewById(R.id.layoutPercentInput);
        b1 = findViewById(R.id.button);
        toolbar = findViewById(R.id.toolbar);

        // Bind Toolbar to Activity Action Bar
        setSupportActionBar(toolbar);

        radioGroup = findViewById(R.id.Radiogroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radioAmount){
                l1.setVisibility(View.VISIBLE);
                l2.setVisibility(View.GONE);
                t4.setText("");
            }else if(checkedId == R.id.radioPercent){
                l2.setVisibility(View.VISIBLE);
                l1.setVisibility(View.GONE);
                t3.setText("");
            }
        });

        TextWatcher sharedWatcher  = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s){
                calculate();
            }
        };
        t1.addTextChangedListener(sharedWatcher);
        t2.addTextChangedListener(sharedWatcher);
        t3.addTextChangedListener(sharedWatcher);
        t4.addTextChangedListener(sharedWatcher);

        b1.setOnClickListener(v -> {
            t1.setText("");
            t2.setText("");
            t3.setText("");
            t4.setText("");
            if (tx1.getHint() != null) {
                tx1.setText(tx1.getHint().toString());
            } else {
                tx1.setText("");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    /* @Override
    public Boolean onCreateOptionMenu(Menu menu){ // initialize menu during startUp
        getMenuInflater().inflate(R.menu,main_menu, menu); // getMenuInflater -> get the system tool to turn XML to clickable object,
        // inflate(R.menu,main_menu, menu) -> reads the main_menu.xml file so u can configure the menu option

        return true;
    }
    @Override
    public Boolean onOptionsItemSelected(MenuItem item){ // fires automatically when taped on any item inside toolbar menu
        if(item.getItemId() == R.id.action_settings){ //  checks id inside the menu toolbar to do the required action
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class); // create new intent to move to a new Screen
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item); // for not crashing the menu if condition doesn't satisfy aa it send to parent class AppCompatibility
    } */
    private void calculate(){
        int checkedId = radioGroup.getCheckedRadioButtonId();
        String input1 = t1.getText().toString().trim();
        String input2 = t2.getText().toString().trim();
        String input3 = t3.getText().toString().trim();
        String input4 = t4.getText().toString().trim();

        try {
            double inputBill = input1.isEmpty() ? 0 : Double.parseDouble(input1);
            int inputPerson = input2.isEmpty() ? 0 : Integer.parseInt(input2);
            double tipAmount = 0;

            if (checkedId == R.id.radioAmount) {
                tipAmount = input3.isEmpty() ? 0 : Double.parseDouble(input3);
            } else if (checkedId == R.id.radioPercent) {
                double tipPercent = input4.isEmpty() ? 0 : Double.parseDouble(input4);
                tipAmount = inputBill * (tipPercent / 100.0);
            }

            if (inputPerson != 0) {
                double total = inputBill + tipAmount;
                double perPerson = total / (double) inputPerson;
                tx1.setText(String.format("%.2f", perPerson));
            } else {
                tx1.setText("Invalid Input");
            }
        } catch (NumberFormatException e) {
            tx1.setText("Error");
        }

    }

}