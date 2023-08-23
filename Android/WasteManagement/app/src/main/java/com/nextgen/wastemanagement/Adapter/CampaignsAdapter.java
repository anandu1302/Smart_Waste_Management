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
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class CampaignsAdapter extends RecyclerView.Adapter<CampaignsAdapter.MyViewHolder>{

    ArrayList<CampaignsModelClass> list;
    Context context;
    String ip;

    public CampaignsAdapter(ArrayList<CampaignsModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_campaigns,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CampaignsModelClass campaignList = list.get(position);
        holder.campaignTV.setText(campaignList.getTitle());

        Glide.with(context).load("http://" + ip +"/waste_management/campaign_tbl/uploads/" + campaignList.getImage()).into(holder.campaignIV);

        holder.campaignCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CampaignDetailsActivity.class);
                intent.putExtra("campaignId",campaignList.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView campaignTV;
        ImageView campaignIV;
        CardView campaignCV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

         campaignTV = itemview.findViewById(R.id.campaignTextView);
         campaignIV = itemview.findViewById(R.id.campaignImageView);
         campaignCV = itemview.findViewById(R.id.card_campaign);

        }
    }
}
