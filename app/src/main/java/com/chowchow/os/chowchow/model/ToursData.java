package com.chowchow.os.chowchow.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ToursData {
    @SerializedName("tour_info")
    @Expose
    private TourInfo tourInfo;
    @SerializedName("tour_detail")
    @Expose
    private List<TourDetail> tourDetail = null;
    @SerializedName("attr_image")
    @Expose
    private List<AttrImage> attrImage = null;
    @SerializedName("numOfAttr")
    @Expose
    private String numOfAttr;
    @SerializedName("numberAttrEachDay")
    @Expose
    private List<Integer> numberAttrEachDay = null;

    public TourInfo getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(TourInfo tourInfo) {
        this.tourInfo = tourInfo;
    }

    public List<TourDetail> getTourDetail() {
        return tourDetail;
    }

    public void setTourDetail(List<TourDetail> tourDetail) {
        this.tourDetail = tourDetail;
    }

    public List<AttrImage> getAttrImage() {
        return attrImage;
    }

    public void setAttrImage(List<AttrImage> attrImage) {
        this.attrImage = attrImage;
    }

    public String getNumOfAttr() {
        return numOfAttr;
    }

    public void setNumOfAttr(String numOfAttr) {
        this.numOfAttr = numOfAttr;
    }

    public List<Integer> getNumberAttrEachDay() {
        return numberAttrEachDay;
    }

    public void setNumberAttrEachDay(List<Integer> numberAttrEachDay) {
        this.numberAttrEachDay = numberAttrEachDay;
    }
}
