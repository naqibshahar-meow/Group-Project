package com.example.groupproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupproject.model.RecyclableItems;
import com.example.groupproject.model.Request;
import com.example.groupproject.model.User;
import com.example.groupproject.remote.ApiUtils;
import com.example.groupproject.remote.RecyclableItemService;
import com.example.groupproject.remote.RequestService;
import com.example.groupproject.sharedpref.SharedPrefManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitRecycleActivity extends AppCompatActivity {

    Spinner spinnerItemType;
    EditText editAddress, editDate, editNotes;
    Button btnSubmit;

    RequestService requestService;
    RecyclableItemService recyclableItemService;
    User currentUser;

    private String token;

    List<RecyclableItems> itemList = new ArrayList<>();
    String apiKey = "128bd249-4fce-4701-a865-0dd8e159ad8d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_recycle);

        // Initialize UI components
        spinnerItemType = findViewById(R.id.spinnerItemType);
        editAddress = findViewById(R.id.editAddress);
        editDate = findViewById(R.id.editDate);
        editNotes = findViewById(R.id.editNotes);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Initialize API services and user data
        currentUser = SharedPrefManager.getInstance(this).getUser();
        token = currentUser.getToken();
        requestService = ApiUtils.getRequestService(token);
        recyclableItemService = ApiUtils.getRecyclableItemService(token);


        // Load items into spinner
        loadItemTypes();

        // Setup date picker
        editDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SubmitRecycleActivity.this,
                    (DatePicker view, int year1, int monthOfYear, int dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, monthOfYear + 1, dayOfMonth);
                        editDate.setText(selectedDate);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Submit button handler
        btnSubmit.setOnClickListener(v -> submitRequest());
    }

    private void loadItemTypes() {
        Call<List<RecyclableItems>> call = recyclableItemService.getAllItems(apiKey);

        call.enqueue(new Callback<List<RecyclableItems>>() {
            @Override
            public void onResponse(Call<List<RecyclableItems>> call, Response<List<RecyclableItems>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemList = response.body();
                    List<String> itemNames = new ArrayList<>();

                    for (RecyclableItems item : itemList) {
                        itemNames.add(item.getItem_name());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SubmitRecycleActivity.this,
                            android.R.layout.simple_spinner_item, itemNames);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerItemType.setAdapter(adapter);
                } else {
                    Toast.makeText(SubmitRecycleActivity.this, "Failed to load items. Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RecyclableItems>> call, Throwable t) {
                Toast.makeText(SubmitRecycleActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitRequest() {
        int selectedPosition = spinnerItemType.getSelectedItemPosition();
        String address = editAddress.getText().toString().trim();
        String date = editDate.getText().toString().trim();
        String notes = editNotes.getText().toString().trim();

        // Input validations
        if (selectedPosition < 0 || selectedPosition >= itemList.size()) {
            Toast.makeText(getApplicationContext(), "Please select an item type", Toast.LENGTH_LONG).show();
            return;
        }

        if (address.isEmpty()) {
            editAddress.setError("Address is required");
            editAddress.requestFocus();
            return;
        }

        if (date.isEmpty()) {
            editDate.setError("Date is required");
            editDate.requestFocus();
            return;
        }

        // Get item and user info
        int itemId = itemList.get(selectedPosition).getItem_id();
        int userId = currentUser.getId();
        String status = "Pending";

        // Debug log
        Log.d("SubmitDebug", "userId: " + userId + ", itemId: " + itemId);
        Log.d("SubmitDebug", "address: " + address + ", date: " + date + ", notes: " + notes);

        // Send API request
        Call<Request> call = requestService.recycleRequest(
                currentUser.getToken(),
                userId,
                itemId,
                address,
                date,
                status,
                notes
        );

        Log.d("RequestURL", call.request().url().toString());

        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                Log.d("SubmitRecycle", "Response: " + response.raw());

                if (response.code() == 201 || response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Recycle request submitted successfully.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error: " + response.message(), Toast.LENGTH_LONG).show();
                    Log.e("SubmitRecycle", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("SubmitRecycle", "Failure: " + t.getMessage(), t);
            }
        });
    }
}