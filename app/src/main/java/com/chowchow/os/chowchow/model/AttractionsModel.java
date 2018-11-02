package com.chowchow.os.chowchow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttractionsModel {
    @SerializedName("list_attractions")
    @Expose
    private List<Attractions> listAttractions = null;

    public List<Attractions> getListAttractions() {
        return listAttractions;
    }

    public void setListAttractions(List<Attractions> listAttractions) {
        this.listAttractions = listAttractions;
    }

}
