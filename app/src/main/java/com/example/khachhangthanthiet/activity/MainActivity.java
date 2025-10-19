package com.example.khachhangthanthiet.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.model.Customer;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView pointsTextView;
    private Button addPointsButton;
    private Button usePointsButton;
    private Button exportButton;
    private Button importButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pointsTextView = findViewById(R.id.textViewTitle);
        addPointsButton = findViewById(R.id.buttonAddPoints);
        usePointsButton = findViewById(R.id.buttonUsePoints);
        exportButton = findViewById(R.id.buttonExport);
        importButton = findViewById(R.id.buttonImport);

        // Load customer data and display points
        //loadCustomerData();

        addPointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to add points
                // Show dialog to input phone and points to add
            }
        });

        usePointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to use points
                // Show dialog to input phone and points to use
            }
        });

        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to export customer data
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to import customer data
            }
        });
    }

}