package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.ToursModel;
import com.chowchow.os.chowchow.ui.view.main.view.ToursActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SuggestTourAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ToursModel> toursModelArrayList;


    public SuggestTourAdapter(Context context, ArrayList<ToursModel> toursModelArrayList) {

        this.context = context;
        this.toursModelArrayList = toursModelArrayList;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return toursModelArrayList.size();
    }

    @Override
    public ToursModel getItem(int position) {
        return toursModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.lv_suggest_tour_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgView);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }
        int day = position + 1 ;
        holder.tvname.setText(toursModelArrayList.get(position).getTourInfo());
        holder.iv.setImageResource(toursModelArrayList.get(position).getImage_drawable());

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvname;
        private ImageView iv;

    }
}
