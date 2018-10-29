package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.ToursModel;
import com.chowchow.os.chowchow.ui.adapter.ToursAdapter;

import java.util.ArrayList;

public class ToursActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    public static final String TOUR_DETAIL_KEY = "data";
    private ListView listView;
    private SearchView editsearch;
    private ToursAdapter toursAdapter;
    public static ArrayList<ToursModel> toursModelArrayList = new ArrayList<ToursModel>();
    private int[] myImageList = new int[]{R.drawable.cau_song_han1, R.drawable.cau_rong_1,
            R.drawable.ngu_hanh_son,R.drawable.dhc_marina
            ,R.drawable.cau_song_han1,R.drawable.cau_rong_1,
            R.drawable.ngu_hanh_son,R.drawable.dhc_marina};
    private String[] myImageNameList = new String[]{"Cầu Sông Hàn", "Cầu Rồng",
            "Ngũ Hành Sơn","Marina"
            ,"Cầu Sông Hàn","Cầu Rồng",
            "Ngũ Hành Sơn","Marina"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours);

        listView = (ListView) findViewById(R.id.listview);

        toursModelArrayList = populateList();
        Log.d("hjhjh",toursModelArrayList.size()+"");
        toursAdapter = new ToursAdapter(this,toursModelArrayList);
        listView.setAdapter(toursAdapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ToursActivity.this, toursModelArrayList.get(position).getTourName(), Toast.LENGTH_SHORT).show();
            }
        });

        setupTourSelectedListener();

        ImageView imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<ToursModel> populateList(){

        ArrayList<ToursModel> list = new ArrayList<>();

        for(int i = 0; i < myImageNameList.length; i++){
            ToursModel imageModel = new ToursModel();
            imageModel.setTourName(myImageNameList[i]);
            imageModel.setImage_drawable(myImageList[i]);
            list.add(imageModel);
        }

        return list;

    }

    public void setupTourSelectedListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                Intent intent = new Intent(ToursActivity.this, TourDetailActivity.class);
                intent.putExtra(TOUR_DETAIL_KEY, toursAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String text = query;
        toursAdapter.filter(text);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        toursAdapter.filter(text);
        return false;
    }
}
