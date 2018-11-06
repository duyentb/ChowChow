package com.chowchow.os.chowchow.ui.view.main.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.model.Event;
import com.chowchow.os.chowchow.model.EventModel;
import com.chowchow.os.chowchow.ui.adapter.EventAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private SearchView editsearch;
    private EventAdapter mAdapter;
    private APIService mService;
    private ArrayList<Event> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mService = ApiUtils.getEventService();

        initViews();

        loadEvent();

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search_event);
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
        mRecyclerView = (RecyclerView)findViewById(R.id.list_event);
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

    public void loadEvent() {
        mService.getEvent().enqueue(new Callback<EventModel>() {
            @Override
            public void onResponse(Call<EventModel> call, Response<EventModel> response) {

                if(response.isSuccessful()) {
                    EventModel jsonResponse = response.body();
                    mArrayList = new ArrayList<Event>(jsonResponse.getListEvent());
                    mAdapter = new EventAdapter(mArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.d("EventActivity", "posts loaded from API");
                }else {
                    int statusCode = response.code();
                    Log.d("EventActivity", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<EventModel> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Log.d("EventActivity", "error loading from API");

            }
        });
    }
}
