package com.chowchow.os.chowchow.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HotelModel implements Serializable {
    @SerializedName("list_hotel")
    @Expose
    private List<Hotel> listHotel = null;

    public List<Hotel> getListHotel() {
        return listHotel;
    }

    public void setListHotel(List<Hotel> listHotel) {
        this.listHotel = listHotel;
    }
}
