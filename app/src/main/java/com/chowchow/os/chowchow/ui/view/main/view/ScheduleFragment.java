package com.chowchow.os.chowchow.ui.view.main.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.model.Tag;
import com.chowchow.os.chowchow.model.TagsModel;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import com.nex3z.flowlayout.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleFragment extends Fragment {

    public static final String SCHEDULE_FAVORITE_KEY = "FAVORITE";
    public static final String SCHEDULE_DATE_FROM_KEY = "DATE_FROM";
    public static final String SCHEDULE_COST_KEY = "COST";
    public static final String SCHEDULE_DURATION_KEY = "DURATION";

    private String fromDate;
    private int cost = 0;
    private String duration;
    private ArrayList<String> listSelected;

    private EditText fromDateEtxt;

    private AppCompatButton btn_filter_schedule;

    private FlowLayout flowLayout;

    private ArrayList<Tag> mArrayList;

    private DatePickerDialog fromDatePickerDialog;

    private SimpleDateFormat dateFormatter;

    private OnFragmentInteractionListener mListener;

    private APIService mService;
    private Context context;

    public ScheduleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mService = ApiUtils.getTagsService();
        context = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        listSelected = new ArrayList<String>();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        findViewsById(view);
        setDateTimeField();
        fromDateEtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

        final String[] listScheduleDay = {"1 Ngày", "2 Ngày", "3 Ngày", "4 Ngày", "5 Ngày"};
        ArrayAdapter<String> arrAdapterScheduleDay = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, listScheduleDay);

        MaterialBetterSpinner mbsScheduleDay = (MaterialBetterSpinner)
                view.findViewById(R.id.cbbScheduleDay);
        mbsScheduleDay.setAdapter(arrAdapterScheduleDay);
        mbsScheduleDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                duration = adapterView.getItemAtPosition(position).toString().substring(0, 1);
            }
        });

        final Integer[] listCost = {500000, 1000000, 1500000, 2000000, 2500000, 3000000};
        ArrayAdapter<Integer> arrAdapterCost = new ArrayAdapter<Integer>(getActivity().getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line, listCost);
        MaterialBetterSpinner mdsCost = (MaterialBetterSpinner)
                view.findViewById(R.id.cbbCost);
        mdsCost.setAdapter(arrAdapterCost);
        mdsCost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                cost = (int) adapterView.getItemAtPosition(position);
            }
        });

        setFavoriteTags();

        btn_filter_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(fromDate) || fromDate == null) {
                    Toast.makeText(context, "Vui lòng chọn ngày khởi hành !", Toast.LENGTH_LONG).show();
                } else if ("".equals(duration) || duration == null) {
                    Toast.makeText(context, "Vui lòng chọn thời lượng !", Toast.LENGTH_LONG).show();
                } else if (cost == 0) {
                    Toast.makeText(context, "Vui lòng chọn chi phí !", Toast.LENGTH_LONG).show();
                } else if (listSelected.size() == 0) {
                    Toast.makeText(context, "Vui lòng chọn sở thích !", Toast.LENGTH_LONG).show();
                } else {
                    Bundle bundle = new Bundle();
                    Log.d("DuyenTB data put",""+listSelected.size() + " "+duration + " " + cost + " " + fromDate );
                    bundle.putStringArrayList(ScheduleFragment.SCHEDULE_FAVORITE_KEY, listSelected);
                    bundle.putString(ScheduleFragment.SCHEDULE_DURATION_KEY, duration);
                    bundle.putInt(ScheduleFragment.SCHEDULE_COST_KEY, cost);
                    bundle.putString(ScheduleFragment.SCHEDULE_DATE_FROM_KEY, fromDate);

                    SuggestTourFragment suggestTourFragment = new SuggestTourFragment();
                    suggestTourFragment.setArguments(bundle);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_container, suggestTourFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("DuyenTB Favorite size: ",""+listSelected.size());
        for (int i =0; i<listSelected.size();i++){
            Log.d("DuyenTB Favorite: ",""+listSelected.get(i));
        }
    }

    private void findViewsById(View view) {
        fromDateEtxt = (EditText) view.findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        btn_filter_schedule = (AppCompatButton) view.findViewById(R.id.btn_filter_schedule);

        flowLayout = (FlowLayout) view.findViewById(R.id.fl_favorite);
    }

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDate = dateFormatter.format(newDate.getTime());
                fromDateEtxt.setText(fromDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
    }

    private void setFavoriteTags() {
        mService.getTags().enqueue(new Callback<TagsModel>() {

            @Override
            public void onResponse(Call<TagsModel> call, Response<TagsModel> response) {
                if (response.isSuccessful()) {
                    TagsModel jsonResponse = response.body();
                    mArrayList = new ArrayList<Tag>(jsonResponse.getTags());
                    int size = mArrayList.size();
                    int currentGenresIndex = 0;
                    Tag tag;
                    Typeface typeface = Typeface.create("sans-serif", Typeface.NORMAL);
                    View.OnClickListener checkBoxOnClickLister = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox checkBox = ((CheckBox) v);
                            String currentTag = (String) checkBox.getTag();

                            if (checkBox.isChecked()) {
                                Log.i("ulog", "is checked " + currentTag );
                                checkBox.setTextColor(getResources().getColorStateList(R.color.white));
                                listSelected.add(currentTag);
                                for (int i =0; i<listSelected.size();i++){
                                    Log.i("ulog", "selected " + listSelected.get(i) );
                                }


                            } else {
                                Log.i("ulog", "not checked " + currentTag);
                                checkBox.setTextColor(getResources().getColorStateList(R.color.colorChipText));
                                listSelected.remove(currentTag);
                                for (int i =0; i<listSelected.size();i++){
                                    Log.i("ulog", "selected " + listSelected.get(i) );
                                }

                            }
                        }
                    };

                    for (int i = 0; i < size; i++) {
                        //tag = mArrayList.get(i);
                        CheckBox checkBox = new CheckBox(context);
                        checkBox.setPadding(CommonUtils.convertDpToPx(context,12),CommonUtils.convertDpToPx(context, 5),CommonUtils.convertDpToPx(context, 12),CommonUtils.convertDpToPx(context, 5));
                        checkBox.setBackground(getResources().getDrawable(R.drawable.selector_match_team));
                        checkBox.setTypeface(typeface);
                        checkBox.setText(mArrayList.get(i).getName());
                        checkBox.setTextColor(getResources().getColorStateList(R.color.colorChipText));
                        checkBox.setTag(mArrayList.get(i).getTagId());
                        checkBox.setButtonDrawable(new StateListDrawable());
                        checkBox.setGravity(Gravity.CENTER);
                        checkBox.setOnClickListener(checkBoxOnClickLister);

                        flowLayout.addView(checkBox);
                    }
                    Log.d("ScheduleFragment", "posts loaded from API");
                } else {
                    int statusCode = response.code();
                    Log.d("ScheduleFragment", "Call API response code " + statusCode);
                    // handle request errors depending on status code
                }

            }

            @Override
            public void onFailure(Call<TagsModel> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Log.d("ScheduleFragment", "error loading from API");

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
