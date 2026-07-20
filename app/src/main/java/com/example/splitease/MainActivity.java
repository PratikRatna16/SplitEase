package com.example.splitease;

import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
EditText t1,t2;
TextView tx1;
RadioButton rd1,rd2;
LinearLayout l1,l2;
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        t1 = findViewById(R.id.editTextNumberSigned);
        t2 = findViewById(R.id.editTextNumber);
        tx1 = findViewById(R.id.TextviewNumberDecimal);
        rd1 = findViewById(R.id.radioAmount);
        rd2 = findViewById(R.id.radioPercent);
        l1 = findViewById(R.id.layoutAmountInput);
        l2 = findViewById(R.id.layoutPercentInput);

        if(rd1.isChecked()){
            l1.setVisibility(View.VISIBLE);
            l2.setVisibility(View.GONE);
        }else if(rd2.isChecked()){
            l2.setVisibility(View.VISIBLE);
            l1.setVisibility(View.GONE);
        }
        t2.addTextChangedListener( new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s){
                String input1 = t1.getText().toString();
                String input2 = t2.getText().toString();
                if(input1.isEmpty() || input2.isEmpty()){
                    tx1.setText("");
                    return;
                }

                try {
                    double inputBill = Double.parseDouble(input1);
                    int inputPerson = Integer.parseInt(input2);
                    if(inputPerson > 0){
                        double PerPerson = inputBill / (double) inputPerson;
                        tx1.setText(String.valueOf((int)Math.ceil(PerPerson)));
                    }else{
                        tx1.setText("Invalid Input");
                    }
                }catch(NumberFormatException e){
                    tx1.setText(" ");
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}