package com.chowchow.os.chowchow.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.callback.ItemClickListener;
import com.chowchow.os.chowchow.model.TourDetail;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsActivity;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsDetailActivity;
import com.chowchow.os.chowchow.ui.view.main.view.DirectionActivity;
import com.chowchow.os.chowchow.ui.view.main.view.ItineraryDetailActivity;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TourAttrAdapter extends RecyclerView.Adapter<TourAttrAdapter.ViewHolder> {
    private ArrayList<TourDetail> mArrayList;
    private ItemClickListener itemClickListener;

    public TourAttrAdapter(ArrayList<TourDetail> arrayList) {
        mArrayList = arrayList;
        //itemClickListener = listener;
    }

    @NonNull
    @Override
    public TourAttrAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.day_item, viewGroup, false);
        return new TourAttrAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TourAttrAdapter.ViewHolder viewHolder, final int position) {
        String imgURL = mArrayList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_attr_image);
        viewHolder.tv_attr_name.setText(mArrayList.get(position).getAttrName());
        viewHolder.tv_attr_address.setText(mArrayList.get(position).getAttrAddress());
        viewHolder.tv_time.setText(mArrayList.get(position).getTime());

        String price = mArrayList.get(position).getPrice();
        if ("".equals(price)) {
            viewHolder.tv_attr_price.setText("Miễn phí");
        } else {
            viewHolder.tv_attr_price.setText(CommonUtils.convertCost(price));
        }

        viewHolder.ll_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItineraryDetailActivity.class);
                intent.putExtra(ItineraryDetailActivity.ITINERARY_DETAIL_KEY, mArrayList.get(position));
                view.getContext().startActivity(intent);
            }
        });

        viewHolder.ll_guide_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DirectionActivity.class);
                intent.putExtra(ItineraryDetailActivity.ITINERARY_DETAIL_KEY, mArrayList.get(position));
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView tv_attr_name, tv_attr_address, tv_time, tv_attr_price, tv_view_detail, tv_guide_map;
        private ImageView iv_attr_image;
        private LinearLayout ll_view_detail, ll_guide_map;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tv_attr_name = (TextView)view.findViewById(R.id.tv_attr_name);
            tv_attr_address = (TextView)view.findViewById(R.id.tv_attr_address);
            tv_attr_price = (TextView) view.findViewById(R.id.tv_attr_price);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_view_detail = (TextView) view.findViewById(R.id.tv_view_detail);
            tv_guide_map = (TextView) view.findViewById(R.id.tv_guide_map);
            iv_attr_image = (ImageView) view.findViewById(R.id.iv_attr_image);

            ll_view_detail = (LinearLayout) view.findViewById(R.id.ll_view_detail);
            ll_guide_map = (LinearLayout) view.findViewById(R.id.ll_guide_map);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            //itemClickListener.onClick(v, getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View v) {
            //itemClickListener.onClick(v, getAdapterPosition(),true);
            return true;
        }

    }
}
