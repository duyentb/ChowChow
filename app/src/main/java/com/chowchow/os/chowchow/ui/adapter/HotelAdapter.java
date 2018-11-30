package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.callback.ItemClickListener;
import com.chowchow.os.chowchow.model.Hotel;
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.ui.view.main.view.HotelActivity;
import com.chowchow.os.chowchow.ui.view.main.view.HotelDetailActivity;
import com.chowchow.os.chowchow.ui.view.main.view.RestaurantActivity;
import com.chowchow.os.chowchow.ui.view.main.view.RestaurantDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Hotel> mArrayList;
    private ArrayList<Hotel> mFilteredList;
    private ItemClickListener itemClickListener;

    public HotelAdapter(ArrayList<Hotel> arrayList, ItemClickListener listener) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public HotelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hotel_item, viewGroup, false);
        return new HotelAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelAdapter.ViewHolder viewHolder, final int position) {
        String imgURL = mFilteredList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_hotel_image);
        viewHolder.tv_hotel_name.setText(mFilteredList.get(position).getHotelName());
        viewHolder.tv_hotel_address.setText(mFilteredList.get(position).getHotelAddress());
        //viewHolder.btn_guide_map.setText(mFilteredList.get(i).getApi());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), HotelDetailActivity.class);
                intent.putExtra(HotelActivity.HOTEL_DETAIL_KEY, mFilteredList.get(position));
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.btn_guide_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                itemClickListener.onClick(v, position, false);
            }
        });
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

                    ArrayList<Hotel> filteredList = new ArrayList<>();

                    for (Hotel hotel : mArrayList) {

                        if (hotel.getHotelName().toLowerCase().contains(charString) || hotel.getHotelAddress().toLowerCase().contains(charString)) {

                            filteredList.add(hotel);
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
                mFilteredList = (ArrayList<Hotel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView tv_hotel_name,tv_hotel_address;
        private ImageView iv_hotel_image;
        private AppCompatButton btn_guide_map;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tv_hotel_name = (TextView)view.findViewById(R.id.tv_hotel_name);
            tv_hotel_address = (TextView)view.findViewById(R.id.tv_hotel_address);
            iv_hotel_image = (ImageView) view.findViewById(R.id.iv_hotel_image);
            btn_guide_map = (AppCompatButton) view.findViewById(R.id.btn_guide_map);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(),true);
            return true;
        }
    }
}
