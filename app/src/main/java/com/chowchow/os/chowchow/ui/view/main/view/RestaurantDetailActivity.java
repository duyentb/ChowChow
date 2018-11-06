package com.chowchow.os.chowchow.ui.view.main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.model.Specialty;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.chowchow.os.chowchow.ui.adapter.SpecialtyAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity {
    private ImageView iv_rest_image;
    private TextView tv_rest_name, tv_rest_address, tv_working_time, tv_rest_detail;
    private RecyclerView rvAttrImage, rvSpecialty;
    private AttrImageAdapter attrImageAdapter;
    private SpecialtyAdapter specialtyAdapter;
    private ArrayList<AttrImage> arrAttrImage;
    private ArrayList<Specialty> arrSpecialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        // Fetch views
        iv_rest_image = (ImageView) findViewById(R.id.iv_rest_image);
        tv_rest_name = (TextView) findViewById(R.id.tv_rest_name);
        tv_rest_address = (TextView) findViewById(R.id.tv_rest_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_rest_detail = (TextView) findViewById(R.id.tv_rest_detail);

        rvAttrImage = (RecyclerView)findViewById(R.id.list_rest_image);
        RecyclerView.LayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAttrImage.setHasFixedSize(true);
        rvAttrImage.setLayoutManager(layoutManagerImage);

        rvSpecialty = (RecyclerView)findViewById(R.id.list_rest_spec);
        RecyclerView.LayoutManager layoutManagerSpec = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSpecialty.setHasFixedSize(true);
        rvSpecialty.setLayoutManager(layoutManagerSpec);

        // Use the attractions to populate the data into our views
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra(RestaurantActivity.RESTAURANT_DETAIL_KEY);

        Picasso.get().load(restaurant.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_rest_image);
        tv_rest_name.setText(restaurant.getRestaurantName());
        tv_rest_address.setText(restaurant.getRestaurantAddress());

        String working_time = "";
        if (!("".equals(restaurant.getOpeningTimeStart())  && "".equals(restaurant.getOpeningTimeEnd()))) {
            working_time = restaurant.getOpeningTimeStart() + " - " + restaurant.getOpeningTimeEnd();
        }

        tv_working_time.setText(working_time);
        tv_rest_detail.setText(restaurant.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(restaurant.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        rvAttrImage.setAdapter(attrImageAdapter);

        arrSpecialty = new ArrayList<Specialty>(restaurant.getSpecialties()) ;
        specialtyAdapter = new SpecialtyAdapter(arrSpecialty);
        rvSpecialty.setAdapter(specialtyAdapter);

    }
}
