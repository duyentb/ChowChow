package com.chowchow.os.chowchow.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop implements Serializable {
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("shop_address")
    @Expose
    private String shopAddress;
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
    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}
