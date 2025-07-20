package com.example.groupproject.remote;

import com.example.groupproject.model.Request;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
public interface RequestService {
    @FormUrlEncoded
    @POST("requests/submitRequest")
    Call<Request> recycleRequest(
            @Header("api-key") String apiKey,
            @Field("user_id") int userId,
            @Field("item_id") int itemId,
            @Field("address") String address,
            @Field("request_date") String date,
            @Field("status") String status,
            @Field("notes") String notes


    );
}
