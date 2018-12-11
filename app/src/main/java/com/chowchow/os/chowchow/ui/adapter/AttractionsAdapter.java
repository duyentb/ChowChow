package com.chowchow.os.chowchow.ui.adapter;

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
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.ui.view.main.view.attractions.AttractionsActivity;
import com.chowchow.os.chowchow.ui.view.main.view.attractions.AttractionsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AttractionsAdapter extends RecyclerView.Adapter<AttractionsAdapter.ViewHolder> implements Filterable {
    private ArrayList<Attractions> mArrayList;
    private ArrayList<Attractions> mFilteredList;
    private ItemClickListener itemClickListener;

    public AttractionsAdapter(ArrayList<Attractions> arrayList, ItemClickListener listener) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public AttractionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.attractions_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String imgURL = mFilteredList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_attr_image);
        viewHolder.tv_attr_name.setText(mFilteredList.get(position).getAttrName());
        viewHolder.tv_attr_address.setText(mFilteredList.get(position).getAttrAddress());
        //viewHolder.btn_guide_map.setText(mFilteredList.get(i).getApi());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), AttractionsDetailActivity.class);
                intent.putExtra(AttractionsActivity.ATTRACTIONS_DETAIL_KEY, mFilteredList.get(position));
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

                    ArrayList<Attractions> filteredList = new ArrayList<>();

                    for (Attractions attractions : mArrayList) {

                        if (attractions.getAttrName().toLowerCase().contains(charString) || attractions.getAttrAddress().toLowerCase().contains(charString)) {

                            filteredList.add(attractions);
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView tv_attr_name,tv_attr_address;
        private ImageView iv_attr_image;
        private AppCompatButton btn_guide_map;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tv_attr_name = (TextView)view.findViewById(R.id.tv_attr_name);
            tv_attr_address = (TextView)view.findViewById(R.id.tv_attr_address);
            iv_attr_image = (ImageView) view.findViewById(R.id.iv_attr_image);
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
