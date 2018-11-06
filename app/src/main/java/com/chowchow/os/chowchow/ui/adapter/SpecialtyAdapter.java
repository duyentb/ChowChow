package com.chowchow.os.chowchow.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Specialty;
import com.squareup.picasso.Picasso;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.ViewHolder> {
    private ArrayList<Specialty> mArrayList;

    public SpecialtyAdapter(ArrayList<Specialty> arrayList) {
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public SpecialtyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rest_spec_img_item, viewGroup, false);
        return new SpecialtyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialtyAdapter.ViewHolder viewHolder, int position) {
        String imgURL = mArrayList.get(position).getImgLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_rest_detail_img);
        viewHolder.tv_spec_name.setText(mArrayList.get(position).getFoodName().toUpperCase());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String price = "Giá: " + formatter.format(Integer.parseInt(mArrayList.get(position).getFoodPrice())) + " VND" ;
        viewHolder.tv_spec_price.setText(price);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_rest_detail_img;
        private TextView tv_spec_name, tv_spec_price;

        public ViewHolder(View view) {
            super(view);
            iv_rest_detail_img = (ImageView) view.findViewById(R.id.iv_rest_detail_img);
            tv_spec_name = (TextView) view.findViewById(R.id.tv_spec_name);
            tv_spec_price = (TextView) view.findViewById(R.id.tv_spec_price);
        }

    }
}
