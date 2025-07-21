package com.example.groupproject.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.groupproject.R;
import com.example.groupproject.model.Request;
import com.example.groupproject.remote.ApiUtils;
import com.example.groupproject.remote.RequestService;
import com.example.groupproject.sharedpref.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestDetailActivity extends AppCompatActivity {

    TextView tvDetailUser, tvDetailItem, tvDetailAddress, tvDetailDate, tvDetailStatus, tvDetailNotes;
    Button btnCancel;
    int requestId, userId, itemId;
    String apiKey = "ea45aef4-913f-4dd2-84e2-66734d6b9296";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail);

        tvDetailUser = findViewById(R.id.tvDetailUser);
        tvDetailItem = findViewById(R.id.tvDetailItem);
        tvDetailAddress = findViewById(R.id.tvDetailAddress);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailStatus = findViewById(R.id.tvDetailStatus);
        tvDetailNotes = findViewById(R.id.tvDetailNotes);
        btnCancel = findViewById(R.id.btnCancel);

        // Get data from Intent
        requestId = getIntent().getIntExtra("request_id", -1);
        userId = getIntent().getIntExtra("user_id", 0);
        itemId = getIntent().getIntExtra("item_id", 0);

        tvDetailUser.setText("User ID: " + userId);
        tvDetailItem.setText("Item ID: " + itemId);
        tvDetailAddress.setText("Address: " + getIntent().getStringExtra("address"));
        tvDetailDate.setText("Date: " + getIntent().getStringExtra("request_date"));
        tvDetailStatus.setText("Status: " + getIntent().getStringExtra("status"));
        tvDetailNotes.setText("Notes: " + getIntent().getStringExtra("notes"));

        // Only show cancel button if status is pending
        if (!getIntent().getStringExtra("status").equalsIgnoreCase("pending")) {
            btnCancel.setVisibility(View.GONE);
        }

        btnCancel.setOnClickListener(v -> cancelRequest());
    }

    private void cancelRequest() {
        String token = SharedPrefManager.getInstance(this).getUser().getToken();
        int userId = SharedPrefManager.getInstance(this).getUser().getId();
        int itemId = getIntent().getIntExtra("item_id", -1); // passed from intent

        RequestService requestService = ApiUtils.getRequestService(token);

        Call<Request> call = requestService.updateRequestStatus(apiKey, requestId, "cancelled");


        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RequestDetailActivity.this, "Request cancelled successfully.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RequestDetailActivity.this, "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Toast.makeText(RequestDetailActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
