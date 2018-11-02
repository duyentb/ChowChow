package com.chowchow.os.chowchow.api;

public class ApiUtils {

    public static final String BASE_URL = "http://www.mocky.io/v2/";
    public static final String API_WEATHER_URL = "https://api.openweathermap.org/";

    public static ChowChowService getToursService() {
        return RetrofitClient.getClient(BASE_URL).create(ChowChowService.class);
    }

    public static ChowChowService getTagsService() {
        return RetrofitClient.getClient(BASE_URL).create(ChowChowService.class);
    }

    public static ChowChowService getAttractionsService() {
        return RetrofitClient.getClient(BASE_URL).create(ChowChowService.class);
    }
}