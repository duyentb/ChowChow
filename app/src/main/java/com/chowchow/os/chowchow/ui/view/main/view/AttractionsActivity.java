package com.chowchow.os.chowchow.ui.view.main.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.api.ChowChowService;
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.ui.adapter.AttractionsAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttractionsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SearchView editsearch;
    private AttractionsAdapter mAdapter;
    private ChowChowService mService;
    private ArrayList<Attractions> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions);

        mService = ApiUtils.getAttractionsService();

        initViews();

        loadAttractions();

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search_attractions);
        editsearch.clearFocus();
        search(editsearch);

        ImageView imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.list_attractions);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public void loadAttractions() {
        mService.getAttractions().enqueue(new Callback<AttractionsModel>() {
            @Override
            public void onResponse(Call<AttractionsModel> call, Response<AttractionsModel> response) {

                if(response.isSuccessful()) {
                    AttractionsModel jsonResponse = response.body();
                    Log.d("AttractionsActivity", "API response " + jsonResponse.getListAttractions().get(0).getAttrImage().get(0).getLink() + " item 0 Tham Quan");
                    Log.d("AttractionsActivity", "API response " + jsonResponse.getListAttractions().get(1).getAttrImage().get(0).getLink() + " item 1 Tham Quan");
                    mArrayList = new ArrayList<Attractions>(jsonResponse.getListAttractions());
                    mAdapter = new AttractionsAdapter(mArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.d("AttractionsActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    Log.d("AttractionsActivity", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<AttractionsModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Log.d("MainActivity", "error loading from API");

            }
        });
    }
}
