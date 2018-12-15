package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.ui.view.main.view.SuggestTourFragment;
import com.chowchow.os.chowchow.ui.view.main.view.tour.TourDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {
    private ArrayList<Tour> mArrayList;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context, ArrayList<Tour> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        int count = 0;
        if (mArrayList.size() > 0) {
            if (mArrayList.size() > 5) {
                count = 5;
            } else {
                count = mArrayList.size();
            }
        }
        return count;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        final TextView tv_tour_name = (TextView) imageLayout.findViewById(R.id.tv_tour_name);

        String imgURL = mArrayList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), TourDetailActivity.class);
                Log.d("DuyenTB position",""+ position);
                intent.putExtra(SuggestTourFragment.SUGGEST_TOUR_DETAIL_KEY, mArrayList.get(position));
                view.getContext().startActivity(intent);
            }
        });

        tv_tour_name.setText(mArrayList.get(position).getTourInfo().getTourName());

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
