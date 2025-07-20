package com.example.groupproject.remote;

import android.content.ClipData;

import com.example.groupproject.model.RecyclableItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RecyclableItemService {
    @GET("recyclable_items")
    Call<List<RecyclableItems>> getAllItems(@Header("api-key") String apiKey);
}
