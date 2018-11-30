package com.chowchow.os.chowchow.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Room;
import com.chowchow.os.chowchow.model.Specialty;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {
    private ArrayList<Room> mArrayList;

    public RoomAdapter(ArrayList<Room> arrayList) {
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public RoomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_room_img_item, viewGroup, false);
        return new RoomAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.ViewHolder viewHolder, int position) {
        String imgURL = mArrayList.get(position).getImgLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_room_detail_img);
        viewHolder.tv_room_name.setText(mArrayList.get(position).getRoomName().toUpperCase());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String price = "Giá: " + formatter.format(Integer.parseInt(mArrayList.get(position).getRoomPrice())) + " VND/Đêm" ;
        viewHolder.tv_room_price.setText(price);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_room_detail_img;
        private TextView tv_room_name, tv_room_price;

        public ViewHolder(View view) {
            super(view);
            iv_room_detail_img = (ImageView) view.findViewById(R.id.iv_room_detail_img);
            tv_room_name = (TextView) view.findViewById(R.id.tv_room_name);
            tv_room_price = (TextView) view.findViewById(R.id.tv_room_price);
        }

    }
}
