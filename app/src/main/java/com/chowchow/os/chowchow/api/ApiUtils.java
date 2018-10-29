package com.chowchow.os.chowchow.api;

public class ApiUtils {

    public static final String BASE_URL = "http://www.mocky.io/v2";

    public static ToursService getToursService() {
        return RetrofitClient.getClient(BASE_URL).create(ToursService.class);
    }
}
