package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.constant.Constant;

public class ApiUtils {
    public static final String BASE_URL = "https://demo3537906.mockable.io/";
    public static final String API_WEATHER_BASE_URL = "http://api.openweathermap.org/";

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

    public static APIService getHotelService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static APIService getWeatherService() {
        return RetrofitClientWeather.getClient(API_WEATHER_BASE_URL).create(APIService.class);
    }
}
