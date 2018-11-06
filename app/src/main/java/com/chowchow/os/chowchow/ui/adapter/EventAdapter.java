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
import com.chowchow.os.chowchow.model.Event;
import com.chowchow.os.chowchow.model.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> implements Filterable {
    private Context context;
    private ArrayList<Event> mArrayList;
    private ArrayList<Event> mFilteredList;

    public EventAdapter(ArrayList<Event> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_item, viewGroup, false);
        return new EventAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.ViewHolder viewHolder, int i) {
        String imgURL = mFilteredList.get(i).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(120, 90).into(viewHolder.iv_event_image);
        viewHolder.tv_event_name.setText(mFilteredList.get(i).getEventName());
        viewHolder.tv_event_address.setText(mFilteredList.get(i).getEventAddress());
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_event_name, tv_event_address;
        private ImageView iv_event_image;
        private AppCompatButton btn_guide_map;

        public ViewHolder(View view) {
            super(view);

            tv_event_name = (TextView)view.findViewById(R.id.tv_event_name);
            tv_event_address = (TextView)view.findViewById(R.id.tv_event_address);
            iv_event_image = (ImageView) view.findViewById(R.id.iv_event_image);
            btn_guide_map = (AppCompatButton) view.findViewById(R.id.btn_guide_map);

        }
    }
}
