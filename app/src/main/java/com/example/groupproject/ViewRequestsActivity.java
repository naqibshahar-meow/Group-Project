package com.example.groupproject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.adapter.RequestAdapter;
import com.example.groupproject.model.Request;
import com.example.groupproject.remote.ApiUtils;
import com.example.groupproject.remote.RequestService;
import com.example.groupproject.sharedpref.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewRequestsActivity extends AppCompatActivity {

    //initialize
    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView tvNoData, tvUserInfo;
    RequestAdapter requestAdapter;
    RequestService requestService;
    String apiKey = "ea45aef4-913f-4dd2-84e2-66734d6b9296";
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        recyclerView = findViewById(R.id.recyclerRequests);
        progressBar = findViewById(R.id.progressBar);
        tvNoData = findViewById(R.id.tvNoData);
        tvUserInfo = findViewById(R.id.tvUserInfo);

        String username = SharedPrefManager.getInstance(this).getUser().getUsername();
        role = SharedPrefManager.getInstance(this).getUser().getRole();
        tvUserInfo.setText("Logged in as: " + username + " (" + role + ")");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        String token = SharedPrefManager.getInstance(this).getUser().getToken();
        requestService = ApiUtils.getRequestService(token);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Auto-refresh when activity is resumed
        if (role.equalsIgnoreCase("admin")) {
            loadAllRequests();
        } else {
            loadUserRequests();
        }
    }

    private void loadAllRequests() {
        progressBar.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);

        Call<List<Request>> call = requestService.getAllRequests(apiKey);
        call.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    requestAdapter = new RequestAdapter(response.body());
                    recyclerView.setAdapter(requestAdapter);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                Toast.makeText(ViewRequestsActivity.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }

    private void loadUserRequests() {
        progressBar.setVisibility(View.VISIBLE);
        tvNoData.setVisibility(View.GONE);

        Call<List<Request>> call = requestService.getAllRequests(apiKey);
        call.enqueue(new Callback<List<Request>>() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    int userId = SharedPrefManager.getInstance(ViewRequestsActivity.this).getUser().getId();
                    List<Request> userRequests = new ArrayList<>();

                    for (Request r : response.body()) {
                        if (r.getUser_id() == userId) {
                            userRequests.add(r);
                        }
                    }

                    if (!userRequests.isEmpty()) {
                        requestAdapter = new RequestAdapter(userRequests);
                        recyclerView.setAdapter(requestAdapter);
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                        recyclerView.setAdapter(null);
                    }
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                    recyclerView.setAdapter(null);
                }
            }

            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvNoData.setVisibility(View.VISIBLE);
                Toast.makeText(ViewRequestsActivity.this, "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("API_ERROR", t.getMessage());
            }
        });
    }
}
