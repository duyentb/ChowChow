package com.chowchow.os.chowchow.api;

public class ApiUtils {

    public static final String BASE_URL = "http://www.mocky.io/v2/";
    public static final String API_WEATHER_URL = "https://api.openweathermap.org/";

    public static APIService getToursService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getTagsService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getAttractionsService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getRestaurantService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getShoppingService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getEventService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
