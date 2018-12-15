package com.chowchow.os.chowchow.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TourDetail implements Serializable {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("attr_name")
    @Expose
    private String attrName;
    @SerializedName("attr_id")
    @Expose
    private String attrId;
    @SerializedName("attr_address")
    @Expose
    private String attrAddress;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String _long;
    @SerializedName("opening_time_start")
    @Expose
    private String openingTimeStart;
    @SerializedName("opening_time_end")
    @Expose
    private String openingTimeEnd;
    @SerializedName("attr_duration")
    @Expose
    private String attrDuration;
    @SerializedName("best_time_start")
    @Expose
    private String bestTimeStart;
    @SerializedName("best_time_end")
    @Expose
    private String bestTimeEnd;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("attr_image")
    @Expose
    private List<AttrImage> attrImage = null;
    @SerializedName("specialties")
    @Expose
    private List<Specialty> specialties = null;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrAddress() {
        return attrAddress;
    }

    public void setAttrAddress(String attrAddress) {
        this.attrAddress = attrAddress;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLong() {
        return _long;
    }

    public void setLong(String _long) {
        this._long = _long;
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

    public String getAttrDuration() {
        return attrDuration;
    }

    public void setAttrDuration(String attrDuration) {
        this.attrDuration = attrDuration;
    }

    public String getBestTimeStart() {
        return bestTimeStart;
    }

    public void setBestTimeStart(String bestTimeStart) {
        this.bestTimeStart = bestTimeStart;
    }

    public String getBestTimeEnd() {
        return bestTimeEnd;
    }

    public void setBestTimeEnd(String bestTimeEnd) {
        this.bestTimeEnd = bestTimeEnd;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

}
