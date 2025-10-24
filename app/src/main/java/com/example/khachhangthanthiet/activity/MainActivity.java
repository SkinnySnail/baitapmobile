package com.example.khachhangthanthiet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khachhangthanthiet.MyAdapter;
import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.database.DatabaseHelper;
import com.example.khachhangthanthiet.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button buttonInput;
    private Button buttonUse;
    private Button buttonList;
    private DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = new DatabaseHelper(this);

        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);

        // Load customer data and display points
        //loadCustomerData();

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCustomers);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        List<Customer> customerList = dbh.getAllCustomers();
        MyAdapter adapter = new MyAdapter(customerList, dbh);
        recyclerView.setAdapter(adapter);

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InputActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UseActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}