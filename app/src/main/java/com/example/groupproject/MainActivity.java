package com.example.groupproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnSubmitRecycle, btnViewRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure the XML file is named activity_main.xml

        btnSubmitRecycle = findViewById(R.id.btnSubmitRecycle);
        btnViewRequests = findViewById(R.id.btnViewRequests);

        btnSubmitRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to SubmitRecycleActivity
                Intent intent = new Intent(MainActivity.this, SubmitRecycleActivity.class);
                startActivity(intent);
            }
        });

        btnViewRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Go to ViewRequestsActivity
                Intent intent = new Intent(MainActivity.this, ViewRequestsActivity.class);
                startActivity(intent);
            }
        });
    }
}