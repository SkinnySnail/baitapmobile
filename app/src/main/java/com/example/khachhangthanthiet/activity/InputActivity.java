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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khachhangthanthiet.MyAdapter;
import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.database.DatabaseHelper;
import com.example.khachhangthanthiet.model.Customer;

import java.util.List;

public class InputActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_input);

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
            String additionalPointsStr = etAdditionalPoints.getText().toString().trim();

            if (phoneNumber.isEmpty()) {
                Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                return;
            }

            if (additionalPointsStr.isEmpty()) {
                Toast.makeText(this, "Please enter additional points", Toast.LENGTH_SHORT).show();
                return;
            }

            int additionalPoints = Integer.parseInt(additionalPointsStr);

            boolean success = dbHelper.updatePoints(phoneNumber, additionalPoints);

            if (success) {
                int updatedPoints = dbHelper.getCurrentPoints(phoneNumber);
                tvCurrentPoints.setText("Current Points: " + updatedPoints);
                Toast.makeText(this, "Points updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to update points", Toast.LENGTH_SHORT).show();
            }
        });


        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, UseActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InputActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
