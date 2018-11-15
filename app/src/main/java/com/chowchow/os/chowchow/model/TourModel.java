package com.chowchow.os.chowchow.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TourModel implements Serializable {
    @SerializedName("data")
    @Expose
    private List<Tour> data = null;

    public List<Tour> getData() {
        return data;
    }

    public void setData(List<Tour> data) {
        this.data = data;
    }
}
