package com.example.groupproject.remote;

public class ApiUtils {

    public static final String BASE_URL = "https://codelah.my/2024916785/api/";

    // return UserService instance
    public static UserService getUserService() {
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

    /*public static RequestService getEventService() {
        return RetrofitClient.getClient(BASE_URL).create(RequestService.class);
    }

    public static RecyclableItemService getParticipationService() {
        return RetrofitClient.getClient(BASE_URL).create(RecyclableItemService.class);
    }*/
}
