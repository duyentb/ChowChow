package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.model.TagsModel;
import com.chowchow.os.chowchow.model.ToursModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ChowChowService {
    @GET("5bdc1a843300004d0081348f")
    Call<AttractionsModel> getAttractions();

    @GET("/5bdafa2232000052003ad42c")
    Call<TagsModel> getTags();

    @GET("/5bd59532310000100041db28")
    Call<ToursModel> getTour();
}
