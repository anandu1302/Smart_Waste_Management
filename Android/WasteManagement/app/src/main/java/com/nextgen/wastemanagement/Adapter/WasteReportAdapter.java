package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;
import com.nextgen.wastemanagement.ModelClass.WasteReportModelClass;
import com.nextgen.wastemanagement.R;
import com.nextgen.wastemanagement.UserLoginActivity;
import com.nextgen.wastemanagement.UserRegisterActivity;
import com.nextgen.wastemanagement.WasteReportsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WasteReportAdapter extends RecyclerView.Adapter<WasteReportAdapter.MyViewHolder>{

    ArrayList<WasteReportModelClass> list;
    Context context;
    String ip,wid;

    public WasteReportAdapter(ArrayList<WasteReportModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        wid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_waste_reports,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        WasteReportModelClass wasteReportList = list.get(position);
        holder.dateTV.setText(wasteReportList.getDate());
        holder.descTV.setText(wasteReportList.getDesc());

        Glide.with(context).load("http://" + ip +"/waste_management/report_tbl/uploads/"+ wasteReportList.getImage()).into(holder.reportIV);

        holder.directionBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mintent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="+wasteReportList.getLatitude()+","+wasteReportList.getLongitude()));
                context.startActivity(mintent);

            }
        });

        holder.wasteCollectedBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateWasteStatus(wasteReportList.getId());
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView descTV;
        ImageView reportIV;
        Button directionBT;
        Button wasteCollectedBT;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            dateTV = itemview.findViewById(R.id.wDateTextView);
            descTV = itemview.findViewById(R.id.wDescTextView);
            reportIV = itemview.findViewById(R.id.wReportImageView);
            directionBT = itemview.findViewById(R.id.directionButton);
            wasteCollectedBT = itemview.findViewById(R.id.collectedButton);

        }
    }

    private void updateWasteStatus(String wasteId) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/wasteStatus.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Intent intent = new Intent(context, WasteReportsActivity.class);
                    context.startActivity(intent);


                }else{
                    Toast.makeText(context.getApplicationContext(), ""+response, Toast.LENGTH_SHORT).show();
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
                params.put("wid",wid);
                params.put("wasteId",wasteId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
