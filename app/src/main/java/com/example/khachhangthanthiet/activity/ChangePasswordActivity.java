package com.example.khachhangthanthiet.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khachhangthanthiet.R;

public class ChangePasswordActivity  extends AppCompatActivity {
    // Declare variables for each view
    private TextView mainTitle;
    private EditText oldPasswordInput, newPasswordInput, confirmNewPasswordInput;
    private Button closeBtn, changePasswordBtn;
    private LinearLayout noteLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password); // your layout filename

        // Initialize views
        mainTitle = findViewById(R.id.mainTitle);
        noteLayout = findViewById(R.id.noteLayout);
        oldPasswordInput = findViewById(R.id.oldPasswordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        confirmNewPasswordInput = findViewById(R.id.confirmNewPasswordInput);
        closeBtn = findViewById(R.id.closeBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);

        // Example usage
        closeBtn.setOnClickListener(v -> finish());
    }
}
