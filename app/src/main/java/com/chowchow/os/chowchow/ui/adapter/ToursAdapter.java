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
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsActivity;

import java.util.ArrayList;
import java.util.Locale;

public class ToursAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ToursModel> toursModelArrayList;


    public ToursAdapter(Context context, ArrayList<ToursModel> toursModelArrayList) {

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
            convertView = inflater.inflate(R.layout.attractions_item, null, true);

            holder.tvname = (TextView) convertView.findViewById(R.id.name);
            holder.iv = (ImageView) convertView.findViewById(R.id.imgView);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvname.setText(toursModelArrayList.get(position).getTourName());
        holder.iv.setImageResource(toursModelArrayList.get(position).getImage_drawable());

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        //AttractionsActivity.toursModelArrayList.clear();
        if (charText.length() == 0) {
            //AttractionsActivity.toursModelArrayList.addAll(toursModelArrayList);
        } else {
            for (ToursModel tm : toursModelArrayList) {
                if (tm.getTourName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    //AttractionsActivity.toursModelArrayList.add(tm);
                }
            }
        }
        notifyDataSetChanged();
    }

    private class ViewHolder {

        protected TextView tvname;
        private ImageView iv;

    }
}
