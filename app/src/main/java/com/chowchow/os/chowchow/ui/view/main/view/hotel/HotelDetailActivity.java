package com.chowchow.os.chowchow.ui.view.main.view.hotel;

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
import com.chowchow.os.chowchow.model.Hotel;
import com.chowchow.os.chowchow.model.Room;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.chowchow.os.chowchow.ui.adapter.RoomAdapter;
import com.chowchow.os.chowchow.ui.view.main.view.DirectionActivity;
import com.chowchow.os.chowchow.ui.view.main.view.MainActivity;
import com.chowchow.os.chowchow.ui.view.main.view.hotel.HotelActivity;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotelDetailActivity extends AppCompatActivity {

    private ImageView iv_hotel_image, iv_back, imgAppName, iv_map;
    private TextView tv_hotel_name, tv_hotel_address, tv_working_time, tv_hotel_detail, tv_hotel_price;
    private RecyclerView rvAttrImage, rvRoom;
    private AttrImageAdapter attrImageAdapter;
    private RoomAdapter roomAdapter;
    private ArrayList<AttrImage> arrAttrImage;
    private ArrayList<Room> arrRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);

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
        iv_hotel_image = (ImageView) findViewById(R.id.iv_hotel_image);
        tv_hotel_name = (TextView) findViewById(R.id.tv_hotel_name);
        tv_hotel_address = (TextView) findViewById(R.id.tv_hotel_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_hotel_detail = (TextView) findViewById(R.id.tv_hotel_detail);
        tv_hotel_price = (TextView) findViewById(R.id.tv_hotel_price);
        iv_map = (ImageView) findViewById(R.id.iv_map);

        rvAttrImage = (RecyclerView)findViewById(R.id.list_hotel_image);
        RecyclerView.LayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAttrImage.setHasFixedSize(true);
        rvAttrImage.setLayoutManager(layoutManagerImage);

        rvRoom = (RecyclerView)findViewById(R.id.list_hotel_room);
        RecyclerView.LayoutManager layoutManagerSpec = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRoom.setHasFixedSize(true);
        rvRoom.setLayoutManager(layoutManagerSpec);

        // Use the attractions to populate the data into our views
        Hotel hotel = (Hotel) getIntent().getSerializableExtra(HotelActivity.HOTEL_DETAIL_KEY);

        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra(HotelActivity.HOTEL_DETAIL_KEY, hotel);
                getApplicationContext().startActivity(intent);
            }
        });

        Picasso.get().load(hotel.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_hotel_image);
        tv_hotel_name.setText(hotel.getHotelName());
        tv_hotel_address.setText(hotel.getHotelAddress());

        String working_time, timeStart, timeEnd;
        timeStart = hotel.getOpeningTimeStart();
        timeEnd = hotel.getOpeningTimeEnd();
        if ("".equals(timeStart)  || "".equals(timeEnd)) {
            working_time = "24/24";
        } else {
            working_time = timeStart + " - " + timeEnd;
        }
        tv_working_time.setText(working_time);

        String price;
        if (!"".equals(hotel.getPrice())) {
            price = CommonUtils.convertCost(hotel.getPrice());
        } else {
            price = "Miễn phí";
        }
        tv_hotel_price.setText(price);

        tv_hotel_detail.setText(hotel.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(hotel.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        rvAttrImage.setAdapter(attrImageAdapter);

        arrRoom = new ArrayList<Room>(hotel.getRoom()) ;
        roomAdapter = new RoomAdapter(arrRoom);
        rvRoom.setAdapter(roomAdapter);

    }
}
