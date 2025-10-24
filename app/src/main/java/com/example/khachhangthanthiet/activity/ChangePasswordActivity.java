package com.example.khachhangthanthiet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.database.DatabaseHelper;

public class ChangePasswordActivity  extends AppCompatActivity {
    // Declare variables for each view
    private TextView mainTitle;
    private EditText oldPasswordInput, newPasswordInput, phoneNumberInput;
    private Button closeBtn, changePasswordBtn;
    private DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password); // your layout filename
        dbh = new DatabaseHelper(this);

        // Initialize views
        mainTitle = findViewById(R.id.mainTitle);
        phoneNumberInput = findViewById(R.id.phoneNumberInput);
        oldPasswordInput = findViewById(R.id.oldPasswordInput);
        newPasswordInput = findViewById(R.id.newPasswordInput);
        closeBtn = findViewById(R.id.closeBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);

        // Example usage
        closeBtn.setOnClickListener(v -> finish());

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changPassword();
            }
        });
    }

    private void changPassword() {
        String phone = phoneNumberInput.getText().toString().trim();
        String oldPassword = oldPasswordInput.getText().toString().trim();
        String newPassword = newPasswordInput.getText().toString().trim();
        if (phone.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Please enter phone number and password", Toast.LENGTH_SHORT).show();
        } else if (dbh.changeAdminPassword(phone, oldPassword, newPassword)) {
            Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Invalid phone number or password", Toast.LENGTH_SHORT).show();
        }
    }
}
