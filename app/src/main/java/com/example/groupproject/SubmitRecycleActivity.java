package com.example.groupproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SubmitRecycleActivity extends AppCompatActivity {

    EditText editItemType, editAddress, editDate, editNotes;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_recycle);

        editItemType = findViewById(R.id.editItemType);
        editAddress = findViewById(R.id.editAddress);
        editDate = findViewById(R.id.editDate);
        editNotes = findViewById(R.id.editNotes);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(view -> {
            String itemType = editItemType.getText().toString();
            String address = editAddress.getText().toString();
            String date = editDate.getText().toString();
            String notes = editNotes.getText().toString();

            // Here you would insert into SQLite, Firebase, or API
            saveRequest(itemType, address, date, notes, "Pending");

            Toast.makeText(this, "Request submitted!", Toast.LENGTH_SHORT).show();
            finish(); // go back to main or view
        });
    }

    private void saveRequest(String itemType, String address, String date, String notes, String status) {
        // Save into database (SQLite or Firebase)
    }
}