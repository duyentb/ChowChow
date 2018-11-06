package com.chowchow.os.chowchow.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.AttrImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AttrImageAdapter extends RecyclerView.Adapter<AttrImageAdapter.ViewHolder> {
    private ArrayList<AttrImage> mArrayList;

    public AttrImageAdapter(ArrayList<AttrImage> arrayList) {
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public AttrImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attr_detail_img_item, viewGroup, false);
        return new AttrImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttrImageAdapter.ViewHolder viewHolder, int position) {
        String imgURL = mArrayList.get(position).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_attr_detail_img);

    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_attr_detail_img;

        public ViewHolder(View view) {
            super(view);
            iv_attr_detail_img = (ImageView) view.findViewById(R.id.iv_attr_detail_img);
        }

    }
}
