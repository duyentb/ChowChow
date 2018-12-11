package com.chowchow.os.chowchow.ui.view.main.view.tour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.model.TourDetail;
import com.chowchow.os.chowchow.ui.adapter.GridViewAdapter;
import com.chowchow.os.chowchow.ui.adapter.TourDetailAdapter;
import com.chowchow.os.chowchow.ui.view.main.view.MainActivity;
import com.chowchow.os.chowchow.ui.view.main.view.SuggestTourFragment;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TourDetailActivity extends AppCompatActivity {
    public static final String TOUR_DETAIL_KEY = "TOUR_DETAIL";

    private ImageView iv_back, ivAppName;
    private CircleImageView civ_tour_image;
    private TextView tv_tour_name, tv_tour_duration, tv_tour_from, tv_tour_location, tv_tour_wheather, tv_tour_cost;
    private GridView gridView;
    private GridViewAdapter gridAdapter;
    private TourDetailAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<TourDetail> tourDetail = new ArrayList<TourDetail>();
    private ArrayList<Integer> numberDay = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

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
        tv_tour_name = (TextView) findViewById(R.id.tv_tour_name);
        tv_tour_duration = (TextView) findViewById(R.id.tv_tour_duration);
        tv_tour_from = (TextView) findViewById(R.id.tv_tour_from);
        tv_tour_location = (TextView) findViewById(R.id.tv_tour_location);
        tv_tour_wheather = (TextView) findViewById(R.id.tv_tour_wheather);
        tv_tour_cost = (TextView) findViewById(R.id.tv_tour_cost);
        civ_tour_image = (CircleImageView) findViewById(R.id.civ_tour_image);

        mRecyclerView = (RecyclerView)findViewById(R.id.list_attr_image);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

        // Use the book to populate the data into our views
        final Tour tour = (Tour) getIntent().getSerializableExtra(SuggestTourFragment.SUGGEST_TOUR_DETAIL_KEY);

        tv_tour_name.setText(tour.getTourInfo().getTourName());

        Picasso.get().load(tour.getAttrImage().get(0).getLink()).centerCrop().resize(120, 120).into(civ_tour_image);

        String duration = CommonUtils.convertText(tour.getTourInfo().getTourDuration(), "ngày");
        tv_tour_duration.setText(duration);

        String from = "Từ " + tour.getTourInfo().getTourDayStart();
        tv_tour_from.setText(from);

        String location = CommonUtils.convertText(tour.getTourInfo().getNumAttr(), "địa điểm");
        tv_tour_location.setText(location);

        String cost = CommonUtils.convertCost(tour.getTourInfo().getTourBudget());
        tv_tour_cost.setText(cost);

        tourDetail = (ArrayList<TourDetail>) tour.getTourDetail();
        numberDay = (ArrayList<Integer>) tour.getNumberAttrEachDay();

        mAdapter = new TourDetailAdapter(this, numberDay, tour);
        mRecyclerView.setAdapter(mAdapter);


    }

}
