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
import com.chowchow.os.chowchow.model.Event;
import com.chowchow.os.chowchow.model.Shop;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsActivity;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsDetailActivity;
import com.chowchow.os.chowchow.ui.view.main.view.EventActivity;
import com.chowchow.os.chowchow.ui.view.main.view.EventDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Event> mArrayList;
    private ArrayList<Event> mFilteredList;
    private ItemClickListener itemClickListener;

    public EventAdapter(ArrayList<Event> arrayList, ItemClickListener listener) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        itemClickListener = listener;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int position) {
        String imgURL = mFilteredList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(120, 90).into(viewHolder.iv_event_image);
        viewHolder.tv_event_name.setText(mFilteredList.get(position).getEventName());
        viewHolder.tv_event_address.setText(mFilteredList.get(position).getEventAddress());

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(view.getContext(), EventDetailActivity.class);
                intent.putExtra(EventActivity.EVENT_DETAIL_KEY, mFilteredList.get(position));
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

                    ArrayList<Event> filteredList = new ArrayList<>();

                    for (Event event : mArrayList) {

                        if (event.getEventName().toLowerCase().contains(charString) || event.getEventAddress().toLowerCase().contains(charString)) {

                            filteredList.add(event);
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
                mFilteredList = (ArrayList<Event>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView tv_event_name, tv_event_address;
        private ImageView iv_event_image;
        private AppCompatButton btn_guide_map;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tv_event_name = (TextView)view.findViewById(R.id.tv_event_name);
            tv_event_address = (TextView)view.findViewById(R.id.tv_event_address);
            iv_event_image = (ImageView) view.findViewById(R.id.iv_event_image);
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
