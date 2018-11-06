package com.chowchow.os.chowchow.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShoppingModel {
    @SerializedName("list_shopping")
    @Expose
    private List<Shop> listShopping = null;

    public List<Shop> getListShopping() {
        return listShopping;
    }

    public void setListShopping(List<Shop> listShopping) {
        this.listShopping = listShopping;
    }
}
