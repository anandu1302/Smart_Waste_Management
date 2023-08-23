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
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.PickUpActivity;
import com.nextgen.wastemanagement.R;
import com.nextgen.wastemanagement.UserLoginActivity;
import com.nextgen.wastemanagement.UserRegisterActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder>{

    ArrayList<RequestModelClass> list;
    Context context;
    String ip,uid;

    public RequestAdapter(ArrayList<RequestModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_waste_requests,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RequestModelClass requestList = list.get(position);
        holder.nameTV.setText(requestList.getName());
        holder.locationTV.setText(requestList.getLocation());
        holder.dateTV.setText(requestList.getDate());
        holder.collectionDateTV.setText(requestList.getRequested_date());
        holder.timeSlotTV.setText(requestList.getTime());

        holder.acceptBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                acceptRequest(requestList.getId());
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;
        TextView locationTV;
        TextView dateTV;
        TextView collectionDateTV;
        TextView timeSlotTV;
        Button acceptBT;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            nameTV = itemview.findViewById(R.id.rwNameTextView);
            locationTV = itemview.findViewById(R.id.rwLocationTextView);
            dateTV = itemview.findViewById(R.id.rwDateTextView);
            collectionDateTV = itemview.findViewById(R.id.rwCollectionDateTextView);
            timeSlotTV = itemview.findViewById(R.id.rwTimeSlotTextView);
            acceptBT = itemview.findViewById(R.id.acceptButton);


        }
    }

    private void acceptRequest(String rid) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/acceptRequest.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Intent intent = new Intent(context, PickUpActivity.class);
                    context.startActivity(intent);

                }else{
                    Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("rid",rid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
