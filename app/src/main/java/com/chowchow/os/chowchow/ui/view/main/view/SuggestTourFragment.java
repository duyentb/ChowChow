package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.callback.ItemClickListener;
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.model.AttractionsModel;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.model.TourModel;
import com.chowchow.os.chowchow.ui.adapter.AttractionsAdapter;
import com.chowchow.os.chowchow.ui.adapter.SuggestTourAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestTourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String SUGGEST_TOUR_DETAIL_KEY = "SUGGEST_TOUR";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private SuggestTourAdapter suggestTourAdapter;
    private RecyclerView mRecyclerView;
    private SearchView editsearch;
    private AVLoadingIndicatorView avi;
    private SuggestTourAdapter mAdapter;
    private APIService mService;
    private ArrayList<Tour> mArrayList;
    private OnFragmentInteractionListener mListener;

    public SuggestTourFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SuggestTourFragment newInstance(String param1, String param2) {
        SuggestTourFragment fragment = new SuggestTourFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_suggest_tour, container, false);
        mService = ApiUtils.getToursService();

        initViews(view);

        loadTour();

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) view.findViewById(R.id.search_tour);
        editsearch.clearFocus();
        search(editsearch);
        editsearch.setVisibility(View.GONE);

        // Init loading animation
        avi = (AVLoadingIndicatorView) view.findViewById(R.id.tour_loading_indicator);

        return view;
    }

    private void initViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_suggest_tour);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
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

    public void loadTour() {
        // Show loading indicator
//        startLoadingAnimation();

        mService.getTour().enqueue(new Callback<TourModel>() {
            @Override
            public void onResponse(Call<TourModel> call, Response<TourModel> response) {

                if (response.isSuccessful()) {
                    TourModel jsonResponse = response.body();
                    mArrayList = new ArrayList<Tour>(jsonResponse.getData());
                    mAdapter = new SuggestTourAdapter(mArrayList);
                    mRecyclerView.setAdapter(mAdapter);
                    Log.d("SuggestTourFragment", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    eventBack();
                    Log.d("SuggestTourFragment", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }

                // Hide loading indicator
                stopLoadingAnimation();
            }

            @Override
            public void onFailure(Call<TourModel> call, Throwable t) {
                // Hide loading indicator
                stopLoadingAnimation();
                eventBack();
                Log.d("Error", t.getMessage());
                Log.d("AttractionsActivity", "error loading from API");

            }
        });
    }

    public void startLoadingAnimation() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avi.smoothToShow();
            }
        });
    }

    public void stopLoadingAnimation() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                avi.smoothToHide();
            }
        });
    }

    public void eventBack() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
        mListener = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
