package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Attractions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Attractions> mArrayList;
    private ArrayList<Attractions> mFilteredList;

    public AttractionsAdapter(ArrayList<Attractions> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public AttractionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attractions_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String imgURL = mFilteredList.get(i).getAttrImage().get(0).getLink();
        final int radius = 5;
        final int margin = 5;
        Picasso.get().load(imgURL).centerCrop().resize(120, 90).transform(new RoundedCornersTransformation(5,5)).into(viewHolder.iv_attr_image);
        viewHolder.tv_attr_name.setText(mFilteredList.get(i).getAttrName());
        Log.d("tv_attr_address = ",mFilteredList.get(i).getAttrAddress());
        viewHolder.tv_attr_address.setText(mFilteredList.get(i).getAttrAddress());
        //viewHolder.btn_guide_map.setText(mFilteredList.get(i).getApi());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mArrayList;
                } else {

                    ArrayList<Attractions> filteredList = new ArrayList<>();

                    for (Attractions androidVersion : mArrayList) {

                        if (androidVersion.getAttrName().toLowerCase().contains(charString) || androidVersion.getAttrAddress().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Attractions>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_attr_name,tv_attr_address;
        private ImageView iv_attr_image;
        private AppCompatButton btn_guide_map;

        public ViewHolder(View view) {
            super(view);

            tv_attr_name = (TextView)view.findViewById(R.id.tv_attr_name);
            tv_attr_address = (TextView)view.findViewById(R.id.tv_attr_address);
            iv_attr_image = (ImageView) view.findViewById(R.id.iv_attr_image);
            btn_guide_map = (AppCompatButton) view.findViewById(R.id.btn_guide_map);

        }
    }
}
