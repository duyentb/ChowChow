package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import java.net.URL;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.ToursModel;
import com.chowchow.os.chowchow.ui.view.main.view.ToursActivity;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLConnection;

public class TourDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover, ivAppName;
    private TextView tvTitle;
    private static final String URL = "http://www.mocky.io/v2/5bd59532310000100041db28";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        // Fetch views
        ivBookCover = (ImageView) findViewById(R.id.ivBookCover);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        // Use the book to populate the data into our views
        ToursModel tour = (ToursModel) getIntent().getSerializableExtra(ToursActivity.TOUR_DETAIL_KEY);

        tvTitle.setText(tour.getTourName());
        ivBookCover.setImageResource(tour.getImage_drawable());

        ivAppName = (ImageView) findViewById(R.id.image_app);
        ivAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent( getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
