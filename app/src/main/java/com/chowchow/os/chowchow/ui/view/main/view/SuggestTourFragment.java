package com.chowchow.os.chowchow.ui.view.main.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.ToursModel;
import com.chowchow.os.chowchow.ui.adapter.SuggestTourAdapter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SuggestTourFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SuggestTourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuggestTourFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listView;
    private SuggestTourAdapter suggestTourAdapter;
    public static ArrayList<ToursModel> toursModelArrayList = new ArrayList<ToursModel>();
    private int[] myImageList = new int[]{R.drawable.ngu_hanh_son, R.drawable.cau_rong_1, R.drawable.ngu_hanh_son, R.drawable.cau_rong_1};
    private String[] myImageNameList = new String[]{"Ngũ Hành Sơn", "Cầu Rồng", "Ngũ Hành Sơn", "Cầu Rồng"};
    private String[] tourInfoList = new String[]{"Tour 1 Ngày", "Tour 2 Ngày 1 Đêm", "Tour 3 Ngày 2 Đêm", "Tour 1 Ngày"};
    private OnFragmentInteractionListener mListener;

    public SuggestTourFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SuggestTourFragment.
     */
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

        listView = (ListView) view.findViewById(R.id.lvSuggestTour);

        toursModelArrayList = populateList();
        Log.d("hjhjh",toursModelArrayList.size()+"");
        suggestTourAdapter = new SuggestTourAdapter(getActivity().getApplicationContext(),toursModelArrayList);
        listView.setAdapter(suggestTourAdapter);

        return view;
    }

    private ArrayList<ToursModel> populateList(){

        ArrayList<ToursModel> list = new ArrayList<>();

        for(int i = 0; i < myImageNameList.length; i++){
            ToursModel imageModel = new ToursModel();
            imageModel.setTourName(myImageNameList[i]);
            imageModel.setImage_drawable(myImageList[i]);
            imageModel.setTourInfo(tourInfoList[i]);
            list.add(imageModel);
        }

        return list;

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
