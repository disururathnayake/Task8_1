package com.example.myfirstandroidapp;



import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] units = {"Inch", "Foot", "Yard", "Mile", "Pound", "Ounce", "Ton", "Celsius", "Fahrenheit", "Kelvin"};
    Spinner sourceSpinner, destSpinner;
    EditText inputValue;
    Button convertButton;
    TextView resultText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceSpinner = findViewById(R.id.source);
        destSpinner = findViewById(R.id.dest);
        inputValue = findViewById(R.id.inputValue);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, units);
        sourceSpinner.setAdapter(adapter);
        destSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        try {
            String sourceUnit = sourceSpinner.getSelectedItem().toString();
            String destUnit = destSpinner.getSelectedItem().toString();
            double input = Double.parseDouble(inputValue.getText().toString());
            if (sourceUnit.equals(destUnit)) {
                resultText.setText("Source and destination units are the same.");
                return;
            }
            double result = convertUnits(sourceUnit, destUnit, input);
            if(result==-1){
                resultText.setText("Conversion is not valid");
            }else {
                resultText.setText(result + " " + destUnit);
            }
        } catch (NumberFormatException e) {
            resultText.setText("Please enter a valid number.");
        }
    }

    private double convertUnits(String source, String dest, double value) {
        // Length conversions
        if (source.equals("Inch")) {
            if (dest.equals("Foot")) return value * 2.54 / 30.48;
            else if (dest.equals("Yard")) return value * 2.54 / 91.44;
            else if (dest.equals("Mile")) return value * 2.54 / 160934;
        } else if (source.equals("Foot")) {
            if (dest.equals("Inch")) return value * 30.48 / 2.54;
            else if (dest.equals("Yard")) return value * 30.48 / 91.44;
            else if (dest.equals("Mile")) return value * 30.48 / 160934;
        } else if (source.equals("Yard")) {
            if (dest.equals("Inch")) return value * 91.44 / 2.54;
            else if (dest.equals("Foot")) return value * 91.44 / 30.48;
            else if (dest.equals("Mile")) return value * 91.44 / 160934;
        } else if (source.equals("Mile")) {
            if (dest.equals("Inch")) return value * 160934 / 2.54;
            else if (dest.equals("Foot")) return value * 160934 / 30.48;
            else if (dest.equals("Yard")) return value * 160934 / 91.44;
        }

        // Weight conversions
        if (source.equals("Pound")) {
            if (dest.equals("Kilogram")) return value * 0.453592;
            else if (dest.equals("Ounce")) return value * 16;
            else if (dest.equals("Ton")) return value * 0.453592 / 907.185;
        } else if (source.equals("Ounce")) {
            if (dest.equals("Pound")) return value / 16;
            else if (dest.equals("Kilogram")) return value * 28.3495 / 1000;
            else if (dest.equals("Ton")) return value * 28.3495 / 907185;
        } else if (source.equals("Ton")) {
            if (dest.equals("Pound")) return value * 907.185 / 0.453592;
            else if (dest.equals("Ounce")) return value * 907.185 * 16;
            else if (dest.equals("Kilogram")) return value * 907.185;
        }

        // Temperature conversions
        if (source.equals("Celsius")) {
            if (dest.equals("Fahrenheit")) return value * 1.8 + 32;
            else if (dest.equals("Kelvin")) return value + 273.15;
        } else if (source.equals("Fahrenheit")) {
            if (dest.equals("Celsius")) return (value - 32) / 1.8;
            else if (dest.equals("Kelvin")) return (value - 32) / 1.8 + 273.15;
        } else if (source.equals("Kelvin")) {
            if (dest.equals("Celsius")) return value - 273.15;
            else if (dest.equals("Fahrenheit")) return (value - 273.15) * 1.8 + 32;
        }

        return -1; // Return the original value if no valid conversion found
    }

}
