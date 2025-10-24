package com.example.khachhangthanthiet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonChangePassword;

    private DatabaseHelper dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbh = new DatabaseHelper(this);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonChangePassword = findViewById(R.id.changePasswordBtn);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        buttonChangePassword.setOnClickListener(v -> changePassword());
    }
    // hàm đăng nhập
    private void loginUser() {
        String phone = editTextPhone.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter phone number and password", Toast.LENGTH_SHORT).show();
        }
        else if (dbh.checkAdminLogin(phone,password)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid phone number or password", Toast.LENGTH_SHORT).show();
        }
    }
    //navigate tới màn hình đổi mật khẩu
    private void changePassword() {
        Intent intent = new Intent(LoginActivity.this, ChangePasswordActivity.class);
        startActivity(intent);
    }
}
