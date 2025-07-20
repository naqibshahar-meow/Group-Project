package com.example.groupproject.remote;

import com.example.groupproject.model.RecyclableItems;

public class ApiUtils {
    public static final String BASE_URL = "https://codelah.my/2024916785/api/";

    // Return UserService instance
    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    public static RequestService getRequestService() {
        return RetrofitClient.getClient(BASE_URL).create(RequestService.class);
    }
    public static RecyclableItemService getRecyclableItemService() {
        return RetrofitClient.getClient(BASE_URL).create(RecyclableItemService.class);
    }


}
