package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Intent;
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
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.callback.ItemClickListener;
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.model.RestaurantModel;
import com.chowchow.os.chowchow.ui.adapter.AttractionsAdapter;
import com.chowchow.os.chowchow.ui.adapter.RestaurantAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantActivity extends AppCompatActivity {
    private ImageView iv_back, imgAppName;
    private RecyclerView mRecyclerView;
    private SearchView editsearch;
    private AVLoadingIndicatorView avi;
    private RestaurantAdapter mAdapter;
    private APIService mService;
    private ArrayList<Restaurant> mArrayList;

    public static final String RESTAURANT_DETAIL_KEY = "RESTAURANT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        mService = ApiUtils.getRestaurantService();

        initViews();

        loadRestaurant();

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search_restaurant);
        editsearch.setIconified(false);
        editsearch.clearFocus();
        search(editsearch);

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

        // Init loading animation
        avi = (AVLoadingIndicatorView) findViewById(R.id.rest_loading_indicator);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.list_restaurant);
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
                String query = "";
                if (newText != null) {
                    query = newText;
                }
                mAdapter.getFilter().filter(query);
                return true;
            }
        });
    }

    public void loadRestaurant() {
        // Show loading indicator
//        startLoadingAnimation();

        mService.getRestaurant().enqueue(new Callback<RestaurantModel>() {
            @Override
            public void onResponse(Call<RestaurantModel> call, Response<RestaurantModel> response) {

                if(response.isSuccessful()) {
                    RestaurantModel jsonResponse = response.body();
                    mArrayList = new ArrayList<>(jsonResponse.getListRestaurant());
                    mAdapter = new RestaurantAdapter(mArrayList, new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Intent intent = new Intent(view.getContext(), DirectionActivity.class);
                            intent.putExtra(RestaurantActivity.RESTAURANT_DETAIL_KEY, mArrayList.get(position));
                            view.getContext().startActivity(intent);
                        }
                    });
                    mRecyclerView.setAdapter(mAdapter);

                    // Hide loading indicator
                    stopLoadingAnimation();
                    Log.d("RestaurantActivity", "posts loaded from API");
                } else {
                    int statusCode  = response.code();
                    stopLoadingAnimation();
                    eventBack();
                    Log.d("RestaurantActivity", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<RestaurantModel> call, Throwable t) {
                stopLoadingAnimation();
                eventBack();
                Log.d("Error",t.getMessage());
                Log.d("RestaurantActivity", "error loading from API");

            }
        });
    }

    public void startLoadingAnimation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avi.smoothToShow();
            }
        });
    }

    public void stopLoadingAnimation() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avi.smoothToHide();
            }
        });
    }

    public void eventBack() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onBackPressed();
            }
        });
    }

}
