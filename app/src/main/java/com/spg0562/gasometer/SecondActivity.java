package com.spg0562.gasometer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity {

    private EditText distanceInput, fuelPriceInput;
    private Spinner vehicleTypeSpinner;
    private Map<String, String[]> vehicleTypeToModels;
    private Spinner carSpinner;
    private TextView resultText;
    private HashMap<String, Double> carEfficiency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ImageView gifView = findViewById(R.id.car);
        Glide.with(this)
                .asGif()
                .load(R.drawable.car)
                .into(gifView);
        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); // Hide default title
        ImageView backIcon = findViewById(R.id.backIcon);
        backIcon.setOnClickListener(v -> finish());

        vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner);
        distanceInput = findViewById(R.id.distanceInput);
        fuelPriceInput = findViewById(R.id.fuelPriceInput);
        carSpinner = findViewById(R.id.carSpinner);
        Button calcButton = findViewById(R.id.calcButton);
        resultText = findViewById(R.id.resultText);

        // Vehicle types and models
        vehicleTypeToModels = new LinkedHashMap<>();
        vehicleTypeToModels.put("Sedan", new String[]{"Proton Saga", "Perodua Bezza"});
        vehicleTypeToModels.put("Hatchback", new String[]{"Perodua Axia", "Perodua Myvi"});
        vehicleTypeToModels.put("SUV", new String[]{"Proton X70", "Honda HR-V"});
        vehicleTypeToModels.put("MPV", new String[]{"Toyota Alphard", "Nissan Serena"});
        vehicleTypeToModels.put("Pickup", new String[]{"Toyota Hilux", "Ford Ranger"});

        // Car efficiency data
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

        // Set up vehicle type spinner
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                new ArrayList<>(vehicleTypeToModels.keySet())
        );
        typeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(typeAdapter);

        // Set up car spinner with initial type
        String firstType = typeAdapter.getItem(0);
        ArrayAdapter<String> carAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                vehicleTypeToModels.get(firstType)
        );
        carAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        carSpinner.setAdapter(carAdapter);

        // Update car spinner when vehicle type changes
        vehicleTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = (String) parent.getItemAtPosition(position);
                String[] models = vehicleTypeToModels.get(selectedType);
                ArrayAdapter<String> newCarAdapter = new ArrayAdapter<>(
                        SecondActivity.this,
                        R.layout.spinner_item,
                        models
                );
                newCarAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                carSpinner.setAdapter(newCarAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

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
