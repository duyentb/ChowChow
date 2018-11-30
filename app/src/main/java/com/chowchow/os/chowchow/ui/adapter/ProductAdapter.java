package com.chowchow.os.chowchow.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<Product> mArrayList;

    public ProductAdapter(ArrayList<Product> arrayList) {
        mArrayList = arrayList;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_product_img_item, viewGroup, false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder viewHolder, int position) {
        String imgURL = mArrayList.get(position).getImgLink();
        Picasso.get().load(imgURL).centerCrop().resize(360, 270).into(viewHolder.iv_shop_detail_img);
        viewHolder.tv_product_name.setText(mArrayList.get(position).getProductName().toUpperCase());
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String price = "Giá: " + formatter.format(Integer.parseInt(mArrayList.get(position).getProductPrice())) + " VND" ;
        viewHolder.tv_product_price.setText(price);
    }

    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_shop_detail_img;
        private TextView tv_product_name, tv_product_price;

        public ViewHolder(View view) {
            super(view);
            iv_shop_detail_img = (ImageView) view.findViewById(R.id.iv_shop_detail_img);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_price = (TextView) view.findViewById(R.id.tv_product_price);
        }

    }
}
