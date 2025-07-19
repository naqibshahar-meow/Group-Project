package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminMainActivity extends AppCompatActivity {

    Button btnViewAllRequests, btnUpdateRequest, btnAddItem, btnUpdateItem, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        // Connect buttons with their IDs in XML
        btnViewAllRequests = findViewById(R.id.btnViewAllRequests);
        btnUpdateRequest = findViewById(R.id.btnUpdateRequest);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnUpdateItem = findViewById(R.id.btnUpdateItem);
        btnLogout = findViewById(R.id.btnLogout);

        // Set click listeners
       /* btnViewAllRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, ViewRequestsActivity.class);
                startActivity(intent);
            }
        });

        btnUpdateRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, UpdateRequestActivity.class);
                startActivity(intent);
            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMainActivity.this, UpdateItemActivity.class);
                startActivity(intent);
            }
        });*/

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Optional: Clear shared preferences / session here
                Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish(); // prevent returning to admin dashboard
            }
        });
    }
}