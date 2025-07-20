package com.example.groupproject.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    /**
     * Return a new Retrofit instance for a given URL.
     * Does NOT reuse the instance to avoid baseUrl conflicts.
     * @param URL REST API URL
     * @return Retrofit instance
     */
    public static Retrofit getClient(String URL) {
        // Logging interceptor for debugging
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Attach logging to OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        // Create and return new Retrofit instance
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client) // attach logging-enabled client
                .build();
    }
}

