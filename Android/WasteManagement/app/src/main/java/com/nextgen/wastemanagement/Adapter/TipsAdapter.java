package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.MyViewHolder> {

    ArrayList<TipsModelClass> list;
    Context context;
    String ip;

    public TipsAdapter(ArrayList<TipsModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_tips,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TipsModelClass tipsList = list.get(position);
        holder.titleTV.setText(tipsList.getTitle());
        holder.descTV.setText(tipsList.getDesc());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        TextView descTV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            titleTV = itemview.findViewById(R.id.tTitleTextView);
            descTV = itemview.findViewById(R.id.tDescTextView);

        }
    }


}
