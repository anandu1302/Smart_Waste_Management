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
import com.nextgen.wastemanagement.CampaignDetailsActivity;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.BinModelClass;
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class BinAdapter extends RecyclerView.Adapter<BinAdapter.MyViewHolder>{

    ArrayList<BinModelClass> list;
    Context context;
    String ip;

    public BinAdapter(ArrayList<BinModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_bin,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BinModelClass binList = list.get(position);
        holder.titleTV.setText(binList.getName());
        holder.descTV.setText(binList.getDescription());

        Glide.with(context).load("http://" + ip +"/waste_management/bin_tbl/uploads/" + binList.getImage()).into(holder.binIV);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView descTV;
        ImageView binIV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            titleTV = itemview.findViewById(R.id.binTitleTextView);
            descTV = itemview.findViewById(R.id.binDescTextView);
            binIV = itemview.findViewById(R.id.binImageView);

        }
    }
}
