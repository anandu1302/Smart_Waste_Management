package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.ModelClass.ProductsModelClass;
import com.nextgen.wastemanagement.ProductDetailsActivity;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder>{

    ArrayList<ProductsModelClass> list;
    Context context;
    String ip;

    public ProductsAdapter(ArrayList<ProductsModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_products,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.MyViewHolder holder, int position) {

        ProductsModelClass productList = list.get(position);
        holder.productNameTV.setText(productList.getPname());
        holder.descTV.setText(productList.getDesc());
        holder.priceTV.setText("₹ "+productList.getPrice());

        Glide.with(context).load("http://" + ip +"/waste_management/products_tbl/uploads/" + productList.getImage()).into(holder.productIV);

        holder.productsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("pid",productList.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTV;
        TextView descTV;
        TextView priceTV;
        ImageView productIV;
        CardView productsCV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            productNameTV = itemview.findViewById(R.id.pProductNameTextView);
            priceTV = itemview.findViewById(R.id.pPriceTextView);
            descTV = itemview.findViewById(R.id.pDescTextView);
            productIV = itemview.findViewById(R.id.productImageView);
            productsCV = itemview.findViewById(R.id.card_products);

        }
    }
}
