package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.TourDetail;
import com.chowchow.os.chowchow.ui.adapter.TourAttrAdapter;

import java.util.ArrayList;

public class TourDayDetailActivity extends AppCompatActivity {
    private ImageView iv_back, ivAppName;
    private TextView tv_tour_day;
    private RecyclerView mRecyclerView;
    private TourAttrAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail_day);

        ivAppName = (ImageView) findViewById(R.id.image_app);
        ivAppName.setOnClickListener(new View.OnClickListener() {
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
        tv_tour_day = (TextView) findViewById(R.id.tv_tour_day);

        mRecyclerView = (RecyclerView)findViewById(R.id.list_attr_in_day);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Use the attractions to populate the data into our views
        final ArrayList<TourDetail> tourDetail = (ArrayList<TourDetail>) getIntent().getSerializableExtra(TourDetailActivity.TOUR_DETAIL_KEY);
        final String tourDay = (String) getIntent().getStringExtra("currentDate");

        tv_tour_day.setText(tourDay);

        mAdapter = new TourAttrAdapter(tourDetail);
        mRecyclerView.setAdapter(mAdapter);
    }
}
