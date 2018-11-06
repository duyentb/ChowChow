package com.chowchow.os.chowchow.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event implements Serializable {
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_address")
    @Expose
    private String eventAddress;
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
    @SerializedName("event_time")
    @Expose
    private String eventTime;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("detail")
    @Expose
    private String detail;
    @SerializedName("attr_image")
    @Expose
    private List<AttrImage> attrImage = null;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
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

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
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
