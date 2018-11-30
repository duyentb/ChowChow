package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.Product;
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.model.Specialty;
import com.chowchow.os.chowchow.model.TourDetail;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.chowchow.os.chowchow.ui.adapter.ProductAdapter;
import com.chowchow.os.chowchow.ui.adapter.SpecialtyAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItineraryDetailActivity extends AppCompatActivity {

    public static final String ITINERARY_DETAIL_KEY = "ITINERARY";

    private ImageView iv_itinerary_image, iv_back, imgAppName;
    private TextView tv_itinerary_name, tv_itinerary_address, tv_working_time, tv_itinerary_detail;
    private RelativeLayout rl_spec_list_itinerary, rl_product_list_itinerary;
    private RecyclerView rvAttrImage, rvSpecialty, rvProduct;
    private AttrImageAdapter attrImageAdapter;
    private SpecialtyAdapter specialtyAdapter;
    private ProductAdapter productAdapter;
    private ArrayList<AttrImage> arrAttrImage;
    private ArrayList<Specialty> arrSpecialty;
    private ArrayList<Product> arrProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary_detail);

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
        iv_itinerary_image = (ImageView) findViewById(R.id.iv_itinerary_image);
        tv_itinerary_name = (TextView) findViewById(R.id.tv_itinerary_name);
        tv_itinerary_address = (TextView) findViewById(R.id.tv_itinerary_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_itinerary_detail = (TextView) findViewById(R.id.tv_itinerary_detail);
        rl_spec_list_itinerary = (RelativeLayout) findViewById(R.id.rl_spec_list_itinerary);
        rl_product_list_itinerary = (RelativeLayout) findViewById(R.id.rl_product_list_itinerary);

        rvAttrImage = (RecyclerView)findViewById(R.id.list_itinerary_image);
        RecyclerView.LayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAttrImage.setHasFixedSize(true);
        rvAttrImage.setLayoutManager(layoutManagerImage);

        rvSpecialty = (RecyclerView)findViewById(R.id.list_itinerary_spec);
        RecyclerView.LayoutManager layoutManagerSpec = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvSpecialty.setHasFixedSize(true);
        rvSpecialty.setLayoutManager(layoutManagerSpec);

        // Use the attractions to populate the data into our views
        TourDetail tourDetail = (TourDetail) getIntent().getSerializableExtra(ItineraryDetailActivity.ITINERARY_DETAIL_KEY);

        Picasso.get().load(tourDetail.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_itinerary_image);
        tv_itinerary_name.setText(tourDetail.getAttrName());
        tv_itinerary_address.setText(tourDetail.getAttrAddress());

        String working_time = "";
        if (!("".equals(tourDetail.getOpeningTimeStart())  && "".equals(tourDetail.getOpeningTimeEnd()))) {
            working_time = tourDetail.getOpeningTimeStart() + " - " + tourDetail.getOpeningTimeEnd();
        }

        tv_working_time.setText(working_time);
        tv_itinerary_detail.setText(tourDetail.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(tourDetail.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        rvAttrImage.setAdapter(attrImageAdapter);

        arrSpecialty = new ArrayList<Specialty>(tourDetail.getSpecialties());
        Log.d("DuyenTB spec",""+arrSpecialty.size());
        if (arrSpecialty.size() > 0) {
            rl_spec_list_itinerary.setVisibility(View.VISIBLE);
            specialtyAdapter = new SpecialtyAdapter(arrSpecialty);
            rvSpecialty.setAdapter(specialtyAdapter);
        } else {
            rl_spec_list_itinerary.setVisibility(View.GONE);
        }

        arrProduct = new ArrayList<Product>(tourDetail.getProduct());
        Log.d("DuyenTB product",""+arrProduct.size());
        if (arrProduct.size() > 0) {
            rl_product_list_itinerary.setVisibility(View.VISIBLE);
            productAdapter = new ProductAdapter(arrProduct);
            rvProduct.setAdapter(productAdapter);
        } else {
            rl_product_list_itinerary.setVisibility(View.GONE);
        }

    }
}
