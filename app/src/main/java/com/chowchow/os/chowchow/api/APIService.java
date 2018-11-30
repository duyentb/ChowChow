package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.model.EventModel;
import com.chowchow.os.chowchow.model.HotelModel;
import com.chowchow.os.chowchow.model.RestaurantModel;
import com.chowchow.os.chowchow.model.ShoppingModel;
import com.chowchow.os.chowchow.model.TagsModel;
import com.chowchow.os.chowchow.model.TourModel;
import com.chowchow.os.chowchow.model.weather.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    @GET("attractions")
    Call<AttractionsModel> getAttractions();

    @GET("restaurant")
    Call<RestaurantModel> getRestaurant();

    @GET("shopping")
    Call<ShoppingModel> getShopping();

    @GET("event")
    Call<EventModel> getEvent();

    @GET("hotel")
    Call<HotelModel> getHotel();

    @GET("tags")
    Call<TagsModel> getTags();

    @GET("tour/suggest")
    Call<TourModel> getTour();

    @GET("data/2.5/weather")
    Call <WeatherModel> getWeatherByCity(@Query("q") String city, @Query("appid") String apiKey);
}
