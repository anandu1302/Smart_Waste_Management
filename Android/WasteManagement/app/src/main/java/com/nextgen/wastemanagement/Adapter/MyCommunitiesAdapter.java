package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nextgen.wastemanagement.CommunitiesChatActivity;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.CommunityModelClass;
import com.nextgen.wastemanagement.ModelClass.MyCommunitiesModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class MyCommunitiesAdapter extends RecyclerView.Adapter<MyCommunitiesAdapter.MyViewHolder>{

    ArrayList<MyCommunitiesModelClass> mList;
    Context context;
    String ip,uid;

    public MyCommunitiesAdapter(ArrayList<MyCommunitiesModelClass> list, Context context) {
        this.mList = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_my_communities,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyCommunitiesModelClass userCommunityList = mList.get(position);
        holder.titleTV.setText(userCommunityList.getTitle());

        Glide.with(context).load("http://" + ip +"/waste_management/community_tbl/uploads/" + userCommunityList.getImage()).into(holder.communityIconIV);

        holder.chatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommunitiesChatActivity.class);
                intent.putExtra("communityID",userCommunityList.getId());
                intent.putExtra("communityTitle",userCommunityList.getTitle());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        ImageView communityIconIV;
        ImageView chatIV;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            titleTV = itemview.findViewById(R.id.myTitleTextView);
            communityIconIV = itemview.findViewById(R.id.myCommunityIconImageView);
            chatIV = itemview.findViewById(R.id.mChatImageView);

        }
    }


}
