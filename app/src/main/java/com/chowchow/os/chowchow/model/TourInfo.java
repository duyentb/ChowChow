package com.chowchow.os.chowchow.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourInfo {

    @SerializedName("tour_id")
    @Expose
    private String tourId;
    @SerializedName("tour_name")
    @Expose
    private String tourName;
    @SerializedName("tour_duration")
    @Expose
    private String tourDuration;
    @SerializedName("num_attr")
    @Expose
    private String numAttr;
    @SerializedName("tour_budget")
    @Expose
    private String tourBudget;

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

}
