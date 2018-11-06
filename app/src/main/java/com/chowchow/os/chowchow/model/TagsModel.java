package com.chowchow.os.chowchow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TagsModel {
    @SerializedName("data")
    @Expose
    private List<Tag> data = null;

    public List<Tag> getTags() {
        return data;
    }

    public void setTags(List<Tag> data) {
        this.data = data;
    }
}
