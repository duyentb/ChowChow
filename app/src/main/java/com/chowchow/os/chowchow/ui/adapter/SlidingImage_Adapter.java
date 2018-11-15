package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.ImageModel;
import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Tour;
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
        int i = 0;
        //i = mArrayList.size();
        return i;
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        String imgURL = mArrayList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(imageView);

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
