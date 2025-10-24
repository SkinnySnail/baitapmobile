package com.example.khachhangthanthiet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.database.DatabaseHelper;


public class UseActivity extends AppCompatActivity {
    private Button buttonInput;
    private Button buttonUse;
    private Button buttonList;
    private DatabaseHelper dbHelper;
    private EditText etPhoneNumber, etAdditionalPoints;
    private TextView tvCurrentPoints;
    private Button btnUpdatePoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use);

        // Initialize views
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAdditionalPoints = findViewById(R.id.etAdditionalPoints);
        tvCurrentPoints = findViewById(R.id.tvCurrentPoints);
        btnUpdatePoints = findViewById(R.id.btnUpdatePoints);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        //navigate
        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);

        //hiển thị điểm khi gõ
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String phoneNumber = s.toString().trim();

                // Chỉ tìm khi có đủ độ dài số điện thoại (tùy bạn quy định)
                if (phoneNumber.length() == 10) {
                    int currentPoints = dbHelper.getCurrentPoints(phoneNumber);

                    if (currentPoints != -1) {
                        tvCurrentPoints.setText("Current Points: " + currentPoints);
                    } else {
                        tvCurrentPoints.setText("Current Points: N/A");
                    }
                } else {
                    tvCurrentPoints.setText("Current Points: ");
                }
            }
        });


        // Set up the update button click listener
        btnUpdatePoints.setOnClickListener(v -> {
            String phoneNumber = etPhoneNumber.getText().toString().trim();
            String usePointsStr = etAdditionalPoints.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fetch current points
            int currentPoints = dbHelper.getCurrentPoints(phoneNumber);
            if (currentPoints == -1) {
                Toast.makeText(this, "Phone number not found", Toast.LENGTH_SHORT).show();
                return;
            }
            tvCurrentPoints.setText("Current Points: " + currentPoints);

            if (usePointsStr.isEmpty()) {
                Toast.makeText(this, "Please enter points to use", Toast.LENGTH_SHORT).show();
                return;
            }

            int usePoints = Integer.parseInt(usePointsStr);

            boolean success = dbHelper.usePoints(phoneNumber, usePoints);

            if (success) {
                int updatedPoints = dbHelper.getCurrentPoints(phoneNumber);
                tvCurrentPoints.setText("Current Points: " + updatedPoints);
                Toast.makeText(this, "Points deducted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Not enough points!", Toast.LENGTH_SHORT).show();
            }
        });


        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseActivity.this, InputActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

