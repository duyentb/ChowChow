package com.chowchow.os.chowchow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagsModel {
    @SerializedName("data")
    @Expose
    private List<Tag> data = null;

    public List<Tag> getData() {
        return data;
    }

    public void setData(List<Tag> data) {
        this.data = data;
    }
}
