package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.model.EventModel;
import com.chowchow.os.chowchow.model.RestaurantModel;
import com.chowchow.os.chowchow.model.ShoppingModel;
import com.chowchow.os.chowchow.model.TagsModel;
import com.chowchow.os.chowchow.model.TourModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("attractions")
    Call<AttractionsModel> getAttractions();

    @GET("restaurant")
    Call<RestaurantModel> getRestaurant();

    @GET("shopping")
    Call<ShoppingModel> getShopping();

    @GET("event")
    Call<EventModel> getEvent();

    @GET("tags")
    Call<TagsModel> getTags();

    @GET("tour/suggest")
    Call<TourModel> getTour();
}
