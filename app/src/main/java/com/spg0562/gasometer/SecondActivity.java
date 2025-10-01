package com.spg0562.gasometer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {

    private EditText distanceInput, fuelPriceInput;
    private Spinner carSpinner;
    private TextView resultText;

    // Car models with their average fuel efficiency (km/L)
    private HashMap<String, Double> carEfficiency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        distanceInput = findViewById(R.id.distanceInput);
        fuelPriceInput = findViewById(R.id.fuelPriceInput);
        carSpinner = findViewById(R.id.carSpinner);
        Button calcButton = findViewById(R.id.calcButton);
        resultText = findViewById(R.id.resultText);

        // Car efficiency data (approximate real-world values for Malaysia)
        carEfficiency = new HashMap<>();
        carEfficiency.put("Perodua Axia", 20.0);
        carEfficiency.put("Perodua Myvi", 18.0);
        carEfficiency.put("Proton Saga", 15.0);
        carEfficiency.put("Perodua Bezza", 20.8);
        carEfficiency.put("Proton X70", 12.0);
        carEfficiency.put("Honda HR-V", 14.0);
        carEfficiency.put("Toyota Alphard", 9.0);
        carEfficiency.put("Nissan Serena", 11.0);
        carEfficiency.put("Toyota Hilux", 12.0);
        carEfficiency.put("Ford Ranger", 11.0);

        // Spinner setup
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                carEfficiency.keySet().toArray(new String[0])
        );
        carSpinner.setAdapter(adapter);

        // Button click listener
        calcButton.setOnClickListener(v -> calculateFuelCost());
    }

    private void calculateFuelCost() {
        String distanceStr = distanceInput.getText().toString();
        String fuelPriceStr = fuelPriceInput.getText().toString();

        if (distanceStr.isEmpty() || fuelPriceStr.isEmpty()) {
            resultText.setText("⚠️ Please enter both distance and fuel price.");
            return;
        }

        double distance = Double.parseDouble(distanceStr);
        double fuelPrice = Double.parseDouble(fuelPriceStr);

        String selectedCar = carSpinner.getSelectedItem().toString();
        double efficiency = carEfficiency.get(selectedCar); // km per litre

        // Calculate litres required and cost
        double litresNeeded = distance / efficiency;
        double totalCost = litresNeeded * fuelPrice;

        resultText.setText(
                "🚗 Car: " + selectedCar +
                        "\n📏 Distance: " + distance + " km" +
                        "\n⛽ Efficiency: " + efficiency + " km/L" +
                        "\n🛢️ Fuel Needed: " + String.format("%.2f", litresNeeded) + " L" +
                        "\n💰 Estimated Cost: RM " + String.format("%.2f", totalCost)
        );
    }
}
