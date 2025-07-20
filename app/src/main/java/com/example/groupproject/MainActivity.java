package com.example.groupproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupproject.sharedpref.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    Button btnSubmitRecycle, btnViewRequests, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Make sure the XML file is named activity_main.xml

        btnSubmitRecycle = findViewById(R.id.btnSubmitRecycle);
        btnViewRequests = findViewById(R.id.btnViewRequests);
        btnLogout = findViewById(R.id.btnLogout);

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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Optional: clear session or shared prefs here if needed
                SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                preferences.edit().clear().apply();

                // Intent to LoginActivity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                // Clear back stack so user can't return using back button
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish(); // finish current activity
            }
        });
    }
}