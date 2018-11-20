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
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.model.Event;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventDetailActivity extends AppCompatActivity {
    private ImageView iv_event_image, imgAppName, iv_map, iv_back;
    private TextView tv_event_name, tv_event_address, tv_working_time, tv_event_detail;
    private RecyclerView mRecyclerView;
    private AttrImageAdapter attrImageAdapter;
    private ArrayList<AttrImage> arrAttrImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

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
        iv_event_image = (ImageView) findViewById(R.id.iv_event_image);
        tv_event_name = (TextView) findViewById(R.id.tv_event_name);
        tv_event_address = (TextView) findViewById(R.id.tv_event_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_event_detail = (TextView) findViewById(R.id.tv_event_detail);
        iv_map = (ImageView) findViewById(R.id.iv_map);

        mRecyclerView = (RecyclerView)findViewById(R.id.list_event_image);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Use the attractions to populate the data into our views
        final Event event = (Event) getIntent().getSerializableExtra(EventActivity.EVENT_DETAIL_KEY);

        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra(EventActivity.EVENT_DETAIL_KEY, event);
                getApplicationContext().startActivity(intent);
            }
        });

        Picasso.get().load(event.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_event_image);
        tv_event_name.setText(event.getEventName());
        tv_event_address.setText(event.getEventAddress());

        String working_time = "";
        if (!("".equals(event.getOpeningTimeStart())  && "".equals(event.getOpeningTimeEnd()))) {
            working_time = event.getOpeningTimeStart() + " - " + event.getOpeningTimeEnd();
        }

        tv_working_time.setText(working_time);
        tv_event_detail.setText(event.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(event.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        mRecyclerView.setAdapter(attrImageAdapter);

    }
}
