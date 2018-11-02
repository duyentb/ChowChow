package com.chowchow.os.chowchow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attractions {
    @SerializedName("attr_id")
    @Expose
    private String attrId;
    @SerializedName("attr_name")
    @Expose
    private String attrName;
    @SerializedName("attr_address")
    @Expose
    private String attrAddress;
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

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
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
}
