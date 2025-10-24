package com.example.khachhangthanthiet.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khachhangthanthiet.FileUtils;
import com.example.khachhangthanthiet.MyAdapter;
import com.example.khachhangthanthiet.R;
import com.example.khachhangthanthiet.database.DatabaseHelper;
import com.example.khachhangthanthiet.model.Customer;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button buttonInput;
    private Button buttonUse;
    private Button buttonList, buttonImport, buttonExport;
    private DatabaseHelper dbh;
    private static final int PICK_XML_FILE = 100;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Customer> customerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbh = new DatabaseHelper(this);

        buttonInput = findViewById(R.id.buttonInput);
        buttonUse = findViewById(R.id.buttonUse);
        buttonList = findViewById(R.id.buttonList);
        buttonImport = findViewById(R.id.buttonImport);
        buttonExport = findViewById(R.id.buttonExport);

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
        buttonExport.setOnClickListener(v -> {
            File xmlFile = dbh.exportToXML(MainActivity.this);
            if (xmlFile == null) {
                Toast.makeText(this, "Xuất XML thất bại", Toast.LENGTH_SHORT).show();
                return;
            }

            Uri fileUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    xmlFile
            );

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/xml");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Customer Export File");
            intent.putExtra(Intent.EXTRA_TEXT, "Đây là danh sách khách hàng.");
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(Intent.createChooser(intent, "Gửi email bằng..."));
        });

        buttonImport.setOnClickListener(v -> pickXMLFile());
    }
    private void pickXMLFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("text/xml");
        startActivityForResult(Intent.createChooser(intent, "Chọn file XML"), PICK_XML_FILE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_XML_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            File file = new File(FileUtils.getPath(this, uri));

            if (dbh.importFromXML(file)) {
                refreshList();
                Toast.makeText(this, "Nhập thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nhập thất bại!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void refreshList() {
        customerList = dbh.getAllCustomers();
        adapter = new MyAdapter(customerList, dbh);
        recyclerView.setAdapter(adapter);
    }



}