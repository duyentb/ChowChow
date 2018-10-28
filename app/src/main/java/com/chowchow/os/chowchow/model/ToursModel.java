package com.chowchow.os.chowchow.model;

import java.io.Serializable;

public class ToursModel  implements  Serializable {
    private int image_drawable;
    private  String tourName;
    private  String tourInfo;

    public int getImage_drawable() {
        return image_drawable;
    }

    public void setImage_drawable(int image_drawable) {
        this.image_drawable = image_drawable;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getTourInfo() {
        return tourInfo;
    }

    public void setTourInfo(String tourInfo) {
        this.tourInfo = tourInfo;
    }
}
