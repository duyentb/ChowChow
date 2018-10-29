package com.chowchow.os.chowchow.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttrImage {

    @SerializedName("attr_id")
    @Expose
    private String attrId;
    @SerializedName("img_id")
    @Expose
    private String imgId;
    @SerializedName("link")
    @Expose
    private String link;

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
