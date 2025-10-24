package com.example.khachhangthanthiet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.khachhangthanthiet.database.DatabaseHelper;
import com.example.khachhangthanthiet.model.Customer;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<Customer> customerList;
    private DatabaseHelper dbHelper; // Reference to the database helper

    public MyAdapter(List<Customer> customerList, DatabaseHelper dbHelper) {
        this.customerList = customerList;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        // Bind data to the TextViews
        holder.tvPhoneNumber.setText(customer.getPhoneNumber());
        holder.tvPoints.setText(String.valueOf(customer.getPoints()));
        holder.tvCreatedDate.setText(customer.getCreatedDate());
        holder.tvLastUpdatedDate.setText(customer.getLastUpdatedDate());

        // Check if the customer is an admin
        if (dbHelper.isAdmin(customer.getPhoneNumber())) {
            // Hide the delete button for admin
            holder.btnDelete.setVisibility(View.GONE);
        } else {
            // Show the delete button for non-admin users
            holder.btnDelete.setVisibility(View.VISIBLE);

            // Handle delete button click
            holder.btnDelete.setOnClickListener(v -> {
                // Delete the customer from the database
                dbHelper.deleteCustomer(customer.getPhoneNumber());

                // Remove the customer from the list and notify the adapter
                customerList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, customerList.size());
            });
        }
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPhoneNumber, tvPoints, tvCreatedDate, tvLastUpdatedDate;
        Button btnDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvPoints = itemView.findViewById(R.id.tvPoints);
            tvCreatedDate = itemView.findViewById(R.id.tvCreatedDate);
            tvLastUpdatedDate = itemView.findViewById(R.id.tvLastUpdatedDate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}