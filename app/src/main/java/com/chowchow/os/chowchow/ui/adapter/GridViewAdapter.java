package com.chowchow.os.chowchow.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.Tour;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<AttrImage> data;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<AttrImage> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) row.findViewById(R.id.iv_tour_attr_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        String imgURL = data.get(position).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(100, 100).into(holder.image);

        return row;
    }

    static class ViewHolder {
        ImageView image;
    }

}
