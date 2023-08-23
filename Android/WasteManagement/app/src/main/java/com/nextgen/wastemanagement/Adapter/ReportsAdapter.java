package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
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
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.MyViewHolder>{

    ArrayList<ReportsModelClass> list;
    Context context;
    String ip;

    public ReportsAdapter(ArrayList<ReportsModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_reports,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReportsModelClass reportList = list.get(position);
        holder.dateTV.setText(reportList.getDate());
        holder.statusTV.setText(reportList.getStatus());
        holder.descTV.setText(reportList.getDesc());

        Glide.with(context).load("http://" + ip +"/waste_management/report_tbl/uploads/"+ reportList.getImage()).into(holder.reportIV);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView descTV;
        TextView statusTV;
        ImageView reportIV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            dateTV = itemview.findViewById(R.id.rDateTextView);
            descTV = itemview.findViewById(R.id.rDescTextView);
            statusTV = itemview.findViewById(R.id.rStatusTextView);
            reportIV = itemview.findViewById(R.id.reportImageView);

        }
    }
}
