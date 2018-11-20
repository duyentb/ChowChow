package com.chowchow.os.chowchow.ui.view.main.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.api.ApiClient;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.model.TourModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private APIService mService;
    private ArrayList<Tour> mArrayList;
    private ProgressDialog progressDialog;
    private Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        context = getContext();
        mService = ApiUtils.getToursService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = ProgressDialog.show(context, "Vui lòng đợi...",
                "Đang tải..!", true);
        mService.getTour().enqueue(new Callback<TourModel>() {

            @Override
            public void onResponse(Call<TourModel> call, Response<TourModel> response) {
                if (response.isSuccessful()) {
                    TourModel jsonResponse = response.body();
                    mArrayList = new ArrayList<Tour>(jsonResponse.getData());
                    Fragment dashboardFragment, sliderFragment;
                    dashboardFragment = new DashboardFragment();
                    sliderFragment = IntroSliderFragment.newInstance(mArrayList);
                    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    transaction.add(R.id.frame_slider, sliderFragment);
                    transaction.add(R.id.frame_dashboard, dashboardFragment);
                    transaction.commit();
                    Log.d("IntroSliderFragment", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("IntroSliderFragment", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<TourModel> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("Error", t.getMessage());
                Log.d("IntroSliderFragment", "error loading from API");

            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
