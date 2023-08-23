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
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nextgen.wastemanagement.CampaignDetailsActivity;
import com.nextgen.wastemanagement.CommunityActivity;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.ModelClass.CommunityModelClass;
import com.nextgen.wastemanagement.R;
import com.nextgen.wastemanagement.UserHomeActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.MyViewHolder>{

    ArrayList<CommunityModelClass> list;
    Context context;
    String ip,uid;

    public CommunityAdapter(ArrayList<CommunityModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_communities,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CommunityModelClass communityList = list.get(position);
        holder.titleTV.setText(communityList.getTitle());

        Glide.with(context).load("http://" + ip +"/waste_management/community_tbl/uploads/" + communityList.getImage()).into(holder.communityIconIV);

        holder.joinBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, ""+communityList.getId(), Toast.LENGTH_SHORT).show();
                joinCommunity(communityList.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titleTV;
        ImageView communityIconIV;
        Button joinBT;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            titleTV = itemview.findViewById(R.id.cTitleTextView);
            communityIconIV = itemview.findViewById(R.id.communityIconImageView);
            joinBT = itemview.findViewById(R.id.cJoinButton);

        }
    }

    private void joinCommunity(String communityId) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/joinCommunity.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Toast.makeText(context, "Joined SuccessFully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, CommunityActivity.class);
                    context.startActivity(intent);

                }else{
                    Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context.getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("cid",communityId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
