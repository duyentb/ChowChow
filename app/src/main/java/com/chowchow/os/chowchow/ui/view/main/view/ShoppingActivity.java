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
import com.chowchow.os.chowchow.model.Shop;
import com.chowchow.os.chowchow.model.ShoppingModel;
import com.chowchow.os.chowchow.ui.adapter.ShoppingAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingActivity extends AppCompatActivity {
    private ImageView iv_back, imgAppName;
    private RecyclerView mRecyclerView;
    private SearchView editsearch;
    private AVLoadingIndicatorView avi;
    private ShoppingAdapter mAdapter;
    private APIService mService;
    private ArrayList<Shop> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        mService = ApiUtils.getShoppingService();

        initViews();

        loadShopping();

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search_shopping);
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
        avi = (AVLoadingIndicatorView) findViewById(R.id.shop_loading_indicator);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initViews(){
        mRecyclerView = (RecyclerView)findViewById(R.id.list_shopping);
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

    public void loadShopping() {
        // Show loading indicator
        //startLoadingAnimation();

        mService.getShopping().enqueue(new Callback<ShoppingModel>() {
            @Override
            public void onResponse(Call<ShoppingModel> call, Response<ShoppingModel> response) {

                if(response.isSuccessful()) {
                    ShoppingModel jsonResponse = response.body();
                    mArrayList = new ArrayList<Shop>(jsonResponse.getListShopping());
                    mAdapter = new ShoppingAdapter(mArrayList);
                    mRecyclerView.setAdapter(mAdapter);

                    Log.d("ShoppingActivity", "posts loaded from API");
                } else {
                    int statusCode  = response.code();
                    eventBack();
                    Log.d("ShoppingActivity", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }

                // Hide loading indicator
                stopLoadingAnimation();
            }

            @Override
            public void onFailure(Call<ShoppingModel> call, Throwable t) {
                // Hide loading indicator
                stopLoadingAnimation();
                eventBack();
                Log.d("Error",t.getMessage());
                Log.d("ShoppingActivity", "error loading from API");
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
