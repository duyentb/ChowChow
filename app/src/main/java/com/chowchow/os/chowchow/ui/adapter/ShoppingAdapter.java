package com.chowchow.os.chowchow.ui.adapter;

import android.content.Context;
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
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Shop> mArrayList;
    private ArrayList<Shop> mFilteredList;

    public ShoppingAdapter(ArrayList<Shop> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shopping_item, viewGroup, false);
        return new ShoppingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingAdapter.ViewHolder viewHolder, int i) {
        String imgURL = mFilteredList.get(i).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(120, 90).into(viewHolder.iv_shop_image);
        viewHolder.tv_shop_name.setText(mFilteredList.get(i).getShopName());
        viewHolder.tv_shop_address.setText(mFilteredList.get(i).getShopAddress());
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

                    ArrayList<Shop> filteredList = new ArrayList<>();

                    for (Shop shop : mArrayList) {

                        if (shop.getShopName().toLowerCase().contains(charString) || shop.getShopAddress().toLowerCase().contains(charString)) {

                            filteredList.add(shop);
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
                mFilteredList = (ArrayList<Shop>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_shop_name,tv_shop_address;
        private ImageView iv_shop_image;
        private AppCompatButton btn_guide_map;

        public ViewHolder(View view) {
            super(view);

            tv_shop_name = (TextView)view.findViewById(R.id.tv_shop_name);
            tv_shop_address = (TextView)view.findViewById(R.id.tv_shop_address);
            iv_shop_image = (ImageView) view.findViewById(R.id.iv_shop_image);
            btn_guide_map = (AppCompatButton) view.findViewById(R.id.btn_guide_map);

        }
    }
}
