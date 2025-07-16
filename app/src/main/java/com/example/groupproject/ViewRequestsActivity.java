package com.example.groupproject;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewRequestsActivity extends AppCompatActivity {

    ListView listRequests;
    ArrayList<String> requestList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        listRequests = findViewById(R.id.listRequests);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requestList);
        listRequests.setAdapter(adapter);

        loadRequests(); // Load data from database or Firebase
    }

    private void loadRequests() {
        // Example mock data:
        requestList.add("Plastic Bottles - Pending");
        requestList.add("Cardboard - Completed");
        adapter.notifyDataSetChanged();
    }
}