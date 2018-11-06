package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.model.EventModel;
import com.chowchow.os.chowchow.model.RestaurantModel;
import com.chowchow.os.chowchow.model.ShoppingModel;
import com.chowchow.os.chowchow.model.TagsModel;
import com.chowchow.os.chowchow.model.ToursModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("5bdc1a843300004d0081348f")
    Call<AttractionsModel> getAttractions();

    @GET("5bdc4791330000e31f813541")
    Call<RestaurantModel> getRestaurant();

    @GET("5bde61233200006a008c635a")
    Call<ShoppingModel> getShopping();

    @GET("5bde81b931000060009e3f33")
    Call<EventModel> getEvent();

    @GET("5bdafa2232000052003ad42c")
    Call<TagsModel> getTags();

    @GET("5bd59532310000100041db28")
    Call<ToursModel> getTour();
}
