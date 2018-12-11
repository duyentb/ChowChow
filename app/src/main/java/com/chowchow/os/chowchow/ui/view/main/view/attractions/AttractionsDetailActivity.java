package com.chowchow.os.chowchow.ui.view.main.view.attractions;

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
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.chowchow.os.chowchow.ui.view.main.view.DirectionActivity;
import com.chowchow.os.chowchow.ui.view.main.view.MainActivity;
import com.chowchow.os.chowchow.ui.view.main.view.attractions.AttractionsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AttractionsDetailActivity extends AppCompatActivity {
    private ImageView iv_attr_image, imgAppName, iv_map, iv_back;
    private TextView tv_attr_name, tv_attr_address, tv_working_time, tv_attr_detail, tv_attr_price;
    private RecyclerView mRecyclerView;
    private AttrImageAdapter attrImageAdapter;
    private ArrayList<AttrImage> arrAttrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_detail);

        imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( getApplicationContext(), MainActivity.class);
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
        iv_attr_image = (ImageView) findViewById(R.id.iv_attr_image);
        tv_attr_name = (TextView) findViewById(R.id.tv_attr_name);
        tv_attr_address = (TextView) findViewById(R.id.tv_attr_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_attr_detail = (TextView) findViewById(R.id.tv_attr_detail);
        tv_attr_price = (TextView) findViewById(R.id.tv_attr_price);
        iv_map = (ImageView) findViewById(R.id.iv_map);

        mRecyclerView = (RecyclerView)findViewById(R.id.list_attr_image);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Use the attractions to populate the data into our views
        final Attractions attractions = (Attractions) getIntent().getSerializableExtra(AttractionsActivity.ATTRACTIONS_DETAIL_KEY);

        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra(AttractionsActivity.ATTRACTIONS_DETAIL_KEY, attractions);
                getApplicationContext().startActivity(intent);
            }
        });

        Picasso.get().load(attractions.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_attr_image);
        tv_attr_name.setText(attractions.getAttrName());
        tv_attr_address.setText(attractions.getAttrAddress());

        String working_time, timeStart, timeEnd;
        timeStart = attractions.getOpeningTimeStart();
        timeEnd = attractions.getOpeningTimeEnd();
        if ("".equals(timeStart)  || "".equals(timeEnd)) {
            working_time = "24/24";
        } else {
            working_time = timeStart + " - " + timeEnd;
        }
        tv_working_time.setText(working_time);

        String price;
        if (!"".equals(attractions.getPrice())) {
            price = attractions.getPrice();
        } else {
            price = "Miễn phí";
        }
        tv_attr_price.setText(price);

        tv_attr_detail.setText(attractions.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(attractions.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        mRecyclerView.setAdapter(attrImageAdapter);

    }
}
