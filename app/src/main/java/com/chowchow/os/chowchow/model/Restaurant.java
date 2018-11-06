package com.chowchow.os.chowchow.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant implements Serializable {
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant_name")
    @Expose
    private String restaurantName;
    @SerializedName("restaurant_address")
    @Expose
    private String restaurantAddress;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("opening_time_start")
    @Expose
    private String openingTimeStart;
    @SerializedName("opening_time_end")
    @Expose
    private String openingTimeEnd;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("attr_image")
    @Expose
    private List<AttrImage> attrImage = null;
    @SerializedName("specialties")
    @Expose
    private List<Specialty> specialties = null;

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getOpeningTimeStart() {
        return openingTimeStart;
    }

    public void setOpeningTimeStart(String openingTimeStart) {
        this.openingTimeStart = openingTimeStart;
    }

    public String getOpeningTimeEnd() {
        return openingTimeEnd;
    }

    public void setOpeningTimeEnd(String openingTimeEnd) {
        this.openingTimeEnd = openingTimeEnd;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public List<AttrImage> getAttrImage() {
        return attrImage;
    }

    public void setAttrImage(List<AttrImage> attrImage) {
        this.attrImage = attrImage;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(List<Specialty> specialties) {
        this.specialties = specialties;
    }
}
