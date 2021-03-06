package com.chowchow.os.chowchow.ui.view.main.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.text.Html;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.ImageModel;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.model.TourModel;
import com.chowchow.os.chowchow.ui.adapter.SlidingImage_Adapter;
import com.chowchow.os.chowchow.ui.adapter.SuggestTourAdapter;
import com.viewpagerindicator.CirclePageIndicator;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.content.ContextCompat.getSystemService;

public class IntroSliderFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private ArrayList<AttrImage> imageModelArrayList;
    private SlidingImage_Adapter mAdapter;
    private APIService mService;
    private ArrayList<Tour> mArrayList;

    private OnFragmentInteractionListener mListener;

    public IntroSliderFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IntroSliderFragment newInstance(ArrayList<Tour> param) {
        IntroSliderFragment fragment = new IntroSliderFragment();
        Bundle args = new Bundle();
        args.putSerializable("Param1", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArrayList = (ArrayList<Tour>) getArguments().getSerializable("Param1");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_intro_slider, container, false);

        mService = ApiUtils.getToursService();

        init(view);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


    }

    private void init(View view) {
        mPager = (ViewPager) view.findViewById(R.id.vpPager);

        mAdapter = new SlidingImage_Adapter(getActivity(), mArrayList);
        mPager.setAdapter(mAdapter);

        CirclePageIndicator indicator = (CirclePageIndicator)
                view.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = mArrayList.size();
        //NUM_PAGES = 0;
        if (NUM_PAGES > 5) {
            NUM_PAGES = 5;
        }

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

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

