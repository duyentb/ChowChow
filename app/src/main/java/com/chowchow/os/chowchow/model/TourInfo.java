package com.chowchow.os.chowchow.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TourInfo implements Serializable {

    @SerializedName("tour_id")
    @Expose
    private String tourId;
    @SerializedName("tour_name")
    @Expose
    private String tourName;
    @SerializedName("tour_duration")
    @Expose
    private String tourDuration;
    @SerializedName("tour_day_start")
    @Expose
    private String tourDayStart;
    @SerializedName("num_attr")
    @Expose
    private String numAttr;
    @SerializedName("tour_budget")
    @Expose
    private String tourBudget;
    @SerializedName("tour_favorite")
    @Expose
    private List<Tag> tourFavorite = null;

    public String getTourId() {
        return tourId;
    }

    public void setTourId(String tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourDayStart() {
        return tourDayStart;
    }

    public void setTourDayStart(String tourDayStart) {
        this.tourDayStart = tourDayStart;
    }

    public String getTourDuration() {
        return tourDuration;
    }

    public void setTourDuration(String tourDuration) {
        this.tourDuration = tourDuration;
    }

    public String getNumAttr() {
        return numAttr;
    }

    public void setNumAttr(String numAttr) {
        this.numAttr = numAttr;
    }

    public String getTourBudget() {
        return tourBudget;
    }

    public void setTourBudget(String tourBudget) {
        this.tourBudget = tourBudget;
    }

    public List<Tag> getTourFavorite() {
        return tourFavorite;
    }

    public void setTourFavorite(List<Tag> tourFavorite) {
        this.tourFavorite = tourFavorite;
    }

}
