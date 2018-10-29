package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.model.ToursModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ToursService {
    @GET("/5bd59532310000100041db28")
    Call<ToursModel> getTour();
}
