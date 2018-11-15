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
import com.chowchow.os.chowchow.model.Tag;
import com.chowchow.os.chowchow.model.Tour;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsActivity;
import com.chowchow.os.chowchow.ui.view.main.view.AttractionsDetailActivity;
import com.chowchow.os.chowchow.ui.view.main.view.SuggestTourFragment;
import com.chowchow.os.chowchow.ui.view.main.view.TourDetailActivity;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SuggestTourAdapter extends RecyclerView.Adapter<SuggestTourAdapter.ViewHolder> implements Filterable {
    private ArrayList<Tour> mArrayList;
    private ArrayList<Tour> mFilteredList;

    public SuggestTourAdapter(ArrayList<Tour> arrayList) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public SuggestTourAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.suggest_tour_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        String imgURL = mFilteredList.get(position).getAttrImage().get(0).getLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_tour_image);

        viewHolder.tv_tour_name.setText(mFilteredList.get(position).getTourInfo().getTourName());

        String favorite = CommonUtils.getTourFavorite(mFilteredList.get(position).getTourInfo().getTourFavorite());
        viewHolder.tv_tour_favorite.setText(favorite);

        String duration = CommonUtils.convertText(mFilteredList.get(position).getTourInfo().getTourDuration(), "Ngày");
        viewHolder.tv_tour_duration.setText(duration);

        String cost = CommonUtils.convertCost(mFilteredList.get(position).getTourInfo().getTourBudget());
        viewHolder.tv_tour_cost.setText(cost);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
            Intent intent = new Intent(view.getContext(), TourDetailActivity.class);
            intent.putExtra(SuggestTourFragment.SUGGEST_TOUR_DETAIL_KEY, mFilteredList.get(position));
            view.getContext().startActivity(intent);
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

                    ArrayList<Tour> filteredList = new ArrayList<>();
                    String favorite = "";
                    for (Tour tour : mArrayList) {
                        favorite = CommonUtils.getTourFavorite(tour.getTourInfo().getTourFavorite());

                        if (tour.getTourInfo().getTourName().toLowerCase().contains(charString) || favorite.toLowerCase().contains(charString)) {

                            filteredList.add(tour);
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
                mFilteredList = (ArrayList<Tour>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener {
        private TextView tv_tour_name, tv_tour_favorite, tv_tour_duration, tv_tour_cost;
        private ImageView iv_tour_image;
        private ItemClickListener itemClickListener;

        public ViewHolder(View view) {
            super(view);

            tv_tour_name = (TextView)view.findViewById(R.id.tv_tour_name);
            tv_tour_favorite = (TextView)view.findViewById(R.id.tv_tour_favorite);
            tv_tour_duration = (TextView)view.findViewById(R.id.tv_tour_duration);
            tv_tour_cost = (TextView)view.findViewById(R.id.tv_tour_cost);
            iv_tour_image = (ImageView) view.findViewById(R.id.iv_tour_image);

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
