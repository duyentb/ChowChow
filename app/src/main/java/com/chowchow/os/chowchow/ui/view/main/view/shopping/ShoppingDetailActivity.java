package com.chowchow.os.chowchow.ui.view.main.view.shopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.model.AttrImage;
import com.chowchow.os.chowchow.model.Product;
import com.chowchow.os.chowchow.model.Shop;
import com.chowchow.os.chowchow.ui.adapter.AttrImageAdapter;
import com.chowchow.os.chowchow.ui.adapter.ProductAdapter;
import com.chowchow.os.chowchow.ui.view.main.view.DirectionActivity;
import com.chowchow.os.chowchow.ui.view.main.view.MainActivity;
import com.chowchow.os.chowchow.ui.view.main.view.shopping.ShoppingActivity;
import com.chowchow.os.chowchow.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingDetailActivity extends AppCompatActivity {

    private ImageView iv_shop_image, iv_back, imgAppName, iv_map;
    private TextView tv_shop_name, tv_shop_address, tv_working_time, tv_shop_detail, tv_shop_price;
    private RecyclerView rvAttrImage, rvProduct;
    private AttrImageAdapter attrImageAdapter;
    private ProductAdapter productAdapter;
    private ArrayList<AttrImage> arrAttrImage;
    private ArrayList<Product> arrProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Fetch views
        iv_shop_image = (ImageView) findViewById(R.id.iv_shop_image);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_address = (TextView) findViewById(R.id.tv_shop_address);
        tv_working_time = (TextView) findViewById(R.id.tv_working_time);
        tv_shop_detail = (TextView) findViewById(R.id.tv_shop_detail);
        tv_shop_price = (TextView) findViewById(R.id.tv_shop_price);
        iv_map = (ImageView) findViewById(R.id.iv_map);

        rvAttrImage = (RecyclerView)findViewById(R.id.list_shop_image);
        RecyclerView.LayoutManager layoutManagerImage = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvAttrImage.setHasFixedSize(true);
        rvAttrImage.setLayoutManager(layoutManagerImage);

        rvProduct = (RecyclerView)findViewById(R.id.list_shop_product);
        RecyclerView.LayoutManager layoutManagerSpec = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvProduct.setHasFixedSize(true);
        rvProduct.setLayoutManager(layoutManagerSpec);

        // Use the attractions to populate the data into our views
        Shop shop = (Shop) getIntent().getSerializableExtra(ShoppingActivity.SHOPPING_DETAIL_KEY);

        iv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra(ShoppingActivity.SHOPPING_DETAIL_KEY, shop);
                getApplicationContext().startActivity(intent);
            }
        });

        Picasso.get().load(shop.getAttrImage().get(0).getLink()).centerCrop().resize(360, 270).into(iv_shop_image);
        tv_shop_name.setText(shop.getShopName());
        tv_shop_address.setText(shop.getShopAddress());

        String working_time, timeStart, timeEnd;
        timeStart = shop.getOpeningTimeStart();
        timeEnd = shop.getOpeningTimeEnd();
        if ("".equals(timeStart)  || "".equals(timeEnd)) {
            working_time = "24/24";
        } else {
            working_time = timeStart + " - " + timeEnd;
        }
        tv_working_time.setText(working_time);

        String price;
        if (!"".equals(shop.getPrice())) {
            price = CommonUtils.convertCost(shop.getPrice());
        } else {
            price = "Miễn phí";
        }
        tv_shop_price.setText(price);

        tv_shop_detail.setText(shop.getDetail());


        arrAttrImage = new ArrayList<AttrImage>(shop.getAttrImage()) ;
        attrImageAdapter = new AttrImageAdapter(arrAttrImage);
        rvAttrImage.setAdapter(attrImageAdapter);

        arrProduct = new ArrayList<Product>(shop.getProduct()) ;
        productAdapter = new ProductAdapter(arrProduct);
        rvProduct.setAdapter(productAdapter);

    }
}
