package com.chowchow.os.chowchow.ui.view.main.view.restaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.model.Specialty;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.chowchow.os.chowchow.ui.adapter.SpecialtyAdapter;
import com.chowchow.os.chowchow.ui.view.main.view.DirectionActivity;
import com.chowchow.os.chowchow.ui.view.main.view.MainActivity;
import com.chowchow.os.chowchow.ui.view.main.view.restaurant.RestaurantActivity;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantDetailActivity extends AppCompatActivity {
    private ImageView iv_rest_image, iv_back, imgAppName, iv_map;
    private TextView tv_rest_name, tv_rest_address, tv_working_time, tv_rest_detail, tv_rest_price;
    private RecyclerView rvAttrImage, rvSpecialty;
    private AttrImageAdapter attrImageAdapter;
    private SpecialtyAdapter specialtyAdapter;
    private ArrayList<AttrImage> arrAttrImage;
    private ArrayList<Specialty> arrSpecialty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Fetch views
        iv_rest_image = (ImageView) findViewById(R.id.iv_rest_image);
        tv_rest_name = (TextView) findViewById(R.id.tv_rest_name);
        tv_rest_address = (TextView) findViewById(R.id.tv_rest_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_rest_detail = (TextView) findViewById(R.id.tv_rest_detail);
        tv_rest_price = (TextView) findViewById(R.id.tv_rest_price);
        iv_map = (ImageView) findViewById(R.id.iv_map);

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

        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra(RestaurantActivity.RESTAURANT_DETAIL_KEY, restaurant);
                getApplicationContext().startActivity(intent);
            }
        });

        Picasso.get().load(restaurant.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_rest_image);
        tv_rest_name.setText(restaurant.getRestaurantName());
        tv_rest_address.setText(restaurant.getRestaurantAddress());

        String working_time, timeStart, timeEnd;
        timeStart = restaurant.getOpeningTimeStart();
        timeEnd = restaurant.getOpeningTimeEnd();
        if ("".equals(timeStart)  || "".equals(timeEnd)) {
            working_time = "24/24";
        } else {
            working_time = timeStart + " - " + timeEnd;
        }
        tv_working_time.setText(working_time);

        String price;
        if (!"".equals(restaurant.getPrice())) {
            price = CommonUtils.convertCost(restaurant.getPrice());
        } else {
            price = "Miễn phí";
        }
        tv_rest_price.setText(price);

        tv_rest_detail.setText(restaurant.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(restaurant.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        rvAttrImage.setAdapter(attrImageAdapter);

        arrSpecialty = new ArrayList<Specialty>(restaurant.getSpecialties()) ;
        specialtyAdapter = new SpecialtyAdapter(arrSpecialty);
        rvSpecialty.setAdapter(specialtyAdapter);

    }
}
