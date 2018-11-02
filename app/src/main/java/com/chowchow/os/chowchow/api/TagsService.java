package com.chowchow.os.chowchow.api;

import com.chowchow.os.chowchow.model.TagsModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TagsService {
    @GET("/5bdafa2232000052003ad42c")
    Call<TagsModel> getTags();
}
