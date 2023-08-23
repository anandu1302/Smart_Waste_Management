package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.PublicBinModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class PublicBinAdapter extends RecyclerView.Adapter<PublicBinAdapter.MyViewHolder> {

    ArrayList<PublicBinModelClass> list;
    Context context;
    String ip;

    public PublicBinAdapter(ArrayList<PublicBinModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_publicbins,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PublicBinModelClass binList = list.get(position);
        holder.locationTV.setText(binList.getLocation());
        holder.locationLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mintent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+binList.getLatitude()+","+binList.getLongitude()));
                context.startActivity(mintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView locationTV;
        LinearLayout locationLL;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            locationTV = itemview.findViewById(R.id.bLocationTextView);
            locationLL = itemview.findViewById(R.id.locationLL);

        }
    }
}
