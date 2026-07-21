package com.example.splitease;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
private EditText t1,t2,t3,t4;
private RadioGroup radioGroup;
private TextView tx1;
LinearLayout l1,l2;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.editTextNumberSigned);
        t2 = findViewById(R.id.editTextNumber);
        t3 = findViewById(R.id.editTipAmount);
        t4 = findViewById(R.id.editTipPercent);
        tx1 = findViewById(R.id.TextviewNumberDecimal);
        l1 = findViewById(R.id.layoutAmountInput);
        l2 = findViewById(R.id.layoutPercentInput);

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
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

            if (inputPerson > 0) {
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