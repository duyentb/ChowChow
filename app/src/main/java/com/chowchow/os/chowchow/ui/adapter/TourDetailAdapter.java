package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.api.APIService;
import com.chowchow.os.chowchow.api.ApiUtils;
import com.chowchow.os.chowchow.constant.Constant;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.DistanceResponse;
import com.chowchow.os.chowchow.model.Element;
import com.chowchow.os.chowchow.model.Row;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.model.TourDetail;
import com.chowchow.os.chowchow.model.TourInfo;
import com.chowchow.os.chowchow.ui.view.main.view.tour.TourDayDetailActivity;
import com.chowchow.os.chowchow.ui.view.main.view.tour.TourDetailActivity;
import com.chowchow.os.chowchow.utils.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetailAdapter extends RecyclerView.Adapter<TourDetailAdapter.ViewHolder> {
    private ArrayList<Integer> mArrayList;
    private Tour mTour;
    private TourInfo tourInfo;
    private APIService mService;
    private ArrayList<TourDetail> tourDetail;
    private ArrayList<TourDetail> tourDetailDay;
    private ArrayList<AttrImage> arrAttrImage;
    private GridViewAdapter mGridViewAdapter;
    private Context context;
    private String currentDate;
    private ArrayList<String> arrDistance;
    private int totalDistance;

    public TourDetailAdapter(Context context, ArrayList<Integer> arrayList, Tour myTour) {
        this.context = context;
        mArrayList = arrayList;
        mTour = myTour;
    }

    @NonNull
    @Override
    public TourDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tour_day_item, viewGroup, false);
        return new TourDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourDetailAdapter.ViewHolder viewHolder, final int position) {
        int day = position + 1;
        mService = ApiUtils.getDistanceService();
        tourDetail = (ArrayList<TourDetail>) mTour.getTourDetail();
        tourInfo = (TourInfo) mTour.getTourInfo();

        tourDetailDay = new ArrayList<TourDetail>();
        arrAttrImage = new ArrayList<AttrImage>();
        arrDistance = new ArrayList<String>();
        for (TourDetail tour: tourDetail) {
            if (String.valueOf(day).equals(tour.getDay())) {
                tourDetailDay.add(tour);
                arrAttrImage.add(tour.getAttrImage().get(0));
                arrDistance.add(tour.getLat() + "," + tour.getLong());
            }
        }

        getDistanceInfo(viewHolder);

        if (day % 2 == 0) {
            viewHolder.ll_day_header.setBackgroundResource(R.color.evenPanelColor);
        } else {
            viewHolder.ll_day_header.setBackgroundResource(R.color.oddPanelColor);
        }

        final String tourDay;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        if (day == 1) {
            currentDate = dateFormat.format(CommonUtils.convertStringToDate(tourInfo.getTourDayStart()));
            Log.d("ChauNB day","1 " + currentDate);
        } else {
            Date nextDay = CommonUtils.getNextDay(CommonUtils.convertStringToDate(currentDate));
            currentDate = dateFormat.format(nextDay);
            Log.d("ChauNB day",day + " " + currentDate);

        }
        tourDay = "Ngày " + day + ", " + currentDate;
        viewHolder.tv_tour_day.setText(tourDay);

        viewHolder.tv_tour_time_start.setText(tourDetailDay.get(0).getTime());

        String location = CommonUtils.convertText(mArrayList.get(position).toString(), " địa điểm");
        viewHolder.tv_tour_location.setText(location);

        mGridViewAdapter = new GridViewAdapter(context, R.layout.grid_view_custom_item, arrAttrImage);
        viewHolder.grid_view_custom.setAdapter(mGridViewAdapter);

        viewHolder.tv_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TourDayDetailActivity.class);
                intent.putExtra(TourDetailActivity.TOUR_DETAIL_KEY, tourDetailDay);
                intent.putExtra("currentDate", tourDay);
                v.getContext().startActivity(intent);
            }
        });

    }

    private void getDistanceInfo(ViewHolder viewHolder) {
        // http://maps.googleapis.com/maps/api/distancematrix/json?destinations=Atlanta,GA|New+York,NY&origins=Orlando,FL&units=imperial
        Map<String, String> mapQuery = new HashMap<>();
        int size = arrDistance.size();
        StringBuilder destinations = new StringBuilder();
        if (size == 1) {
            destinations.append(arrDistance.get(0));
        } else {
            for (int i = 0; i < size - 1; i++) {
                destinations.append(arrDistance.get(i)).append("|");
            }
            destinations.append(arrDistance.get(size-1));
        }
        Log.d("ChauNB destinations",destinations.toString());
        mapQuery.put("units", "metric");
        mapQuery.put("origins", "16.0755258,108.1536513");
        mapQuery.put("destinations", destinations.toString());
        mapQuery.put("key", Constant.GOOGLE_API_KEY);

        Call<DistanceResponse> call = mService.getDistanceInfo(mapQuery);
        call.enqueue(new Callback<DistanceResponse>() {
            @Override
            public void onResponse(Call<DistanceResponse> call, Response<DistanceResponse> response) {
                DistanceResponse result = response.body();
                ArrayList<Element> arrElement = (ArrayList<Element>) result.getRows().get(0).getElements();
                totalDistance = 0;
                for (int i = 0; i < arrElement.size() ; i++) {
                    totalDistance += arrElement.get(i).getDistance().value;
                }
                Log.d("ChauNB totalDistance", totalDistance + " m");
                String distance = CommonUtils.convertDistance(totalDistance) + " km";
                Log.d("ChauNB96 distance",""+distance);
                viewHolder.tv_tour_distance.setText(distance);
            }

            @Override
            public void onFailure(Call<DistanceResponse> call, Throwable t) {
                String distance = "16,67 km";
                viewHolder.tv_tour_distance.setText(distance);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_attr_detail_img;
        private TextView tv_tour_day , tv_tour_time_start, tv_tour_distance, tv_tour_location, tv_view_detail;
        private GridView grid_view_custom;
        private LinearLayout ll_day_header;

        public ViewHolder(View view) {
            super(view);
            //iv_attr_detail_img = (ImageView) view.findViewById(R.id.iv_attr_detail_img);
            tv_tour_day = (TextView) view.findViewById(R.id.tv_tour_day);
            tv_tour_time_start = (TextView) view.findViewById(R.id.tv_tour_time_start);
            tv_tour_distance = (TextView) view.findViewById(R.id.tv_tour_distance);
            tv_tour_location = (TextView) view.findViewById(R.id.tv_tour_location);
            tv_view_detail = (TextView) view.findViewById(R.id.tv_view_detail);
            grid_view_custom = (GridView) view.findViewById(R.id.grid_view_custom);
            ll_day_header = (LinearLayout) view.findViewById(R.id.ll_day_header);
        }

    }
}
