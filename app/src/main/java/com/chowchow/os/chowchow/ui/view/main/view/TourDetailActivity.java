package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.ToursModel;
import com.chowchow.os.chowchow.ui.view.main.view.ToursActivity;

public class TourDetailActivity extends AppCompatActivity {
    private ImageView ivBookCover, ivAppName;
    private TextView tvTitle;
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
