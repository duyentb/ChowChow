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
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsActivity;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsDetailActivity;
import com.chowchow.os.chowchow.ui.view.main.view.RestaurantActivity;
import com.chowchow.os.chowchow.ui.view.main.view.RestaurantDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Restaurant> mArrayList;
    private ArrayList<Restaurant> mFilteredList;
    private ItemClickListener itemClickListener;

    public RestaurantAdapter(ArrayList<Restaurant> arrayList, ItemClickListener listener) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_item, viewGroup, false);
        return new RestaurantAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder viewHolder, final int position) {
        String imgURL = mFilteredList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_restaurant_image);
        viewHolder.tv_restaurant_name.setText(mFilteredList.get(position).getRestaurantName());
        viewHolder.tv_restaurant_address.setText(mFilteredList.get(position).getRestaurantAddress());
        //viewHolder.btn_guide_map.setText(mFilteredList.get(i).getApi());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), RestaurantDetailActivity.class);
                intent.putExtra(RestaurantActivity.RESTAURANT_DETAIL_KEY, mFilteredList.get(position));
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

                    ArrayList<Restaurant> filteredList = new ArrayList<>();

                    for (Restaurant restaurant : mArrayList) {

                        if (restaurant.getRestaurantName().toLowerCase().contains(charString) || restaurant.getRestaurantAddress().toLowerCase().contains(charString)) {

                            filteredList.add(restaurant);
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
                mFilteredList = (ArrayList<Restaurant>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView tv_restaurant_name,tv_restaurant_address;
        private ImageView iv_restaurant_image;
        private AppCompatButton btn_guide_map;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tv_restaurant_name = (TextView)view.findViewById(R.id.tv_restaurant_name);
            tv_restaurant_address = (TextView)view.findViewById(R.id.tv_restaurant_address);
            iv_restaurant_image = (ImageView) view.findViewById(R.id.iv_restaurant_image);
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
