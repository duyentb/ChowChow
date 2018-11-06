package com.chowchow.os.chowchow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantModel {
    @SerializedName("list_restaurant")
    @Expose
    private List<Restaurant> listRestaurant = null;

    public List<Restaurant> getListRestaurant() {
        return listRestaurant;
    }

    public void setListRestaurant(List<Restaurant> listRestaurant) {
        this.listRestaurant = listRestaurant;
    }

}
