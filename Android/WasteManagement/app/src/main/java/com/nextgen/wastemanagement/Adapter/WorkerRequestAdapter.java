package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.ModelClass.WorkerRequestModelClass;
import com.nextgen.wastemanagement.PickUpActivity;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkerRequestAdapter extends RecyclerView.Adapter<WorkerRequestAdapter.MyViewHolder>{

    ArrayList<WorkerRequestModelClass> list;
    Context context;
    String ip;

    EditText amountET;

    public WorkerRequestAdapter(ArrayList<WorkerRequestModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_requests,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerRequestAdapter.MyViewHolder holder, int position) {

        WorkerRequestModelClass wasteCollectionList = list.get(position);
        holder.nameTV.setText(wasteCollectionList.getName());
        holder.locationTV.setText(wasteCollectionList.getLocation());
        holder.dateTV.setText(wasteCollectionList.getDate());
        holder.collectionDateTV.setText(wasteCollectionList.getRequested_date());
        holder.timeSlotTV.setText(wasteCollectionList.getTime());

        holder.collectBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertBox(wasteCollectionList.getId());
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
        Button collectBT;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            nameTV = itemview.findViewById(R.id.aNameTextView);
            locationTV = itemview.findViewById(R.id.aLocationTextView);
            dateTV = itemview.findViewById(R.id.aDateTextView);
            collectionDateTV = itemview.findViewById(R.id.aCollectionDateTextView);
            timeSlotTV = itemview.findViewById(R.id.aTimeSlotTextView);
            collectBT = itemview.findViewById(R.id.collectButton);


        }
    }

    private void showAlertBox(String wid) {
        LayoutInflater layoutInflater = LayoutInflater.from(context.getApplicationContext());
        View mview = layoutInflater.inflate(R.layout.raw_amount,null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(mview);

        amountET = mview.findViewById(R.id.amountEditText);
        Button submitBT = mview.findViewById(R.id.aSubmitButton);

        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               addAmount(wid,amountET.getText().toString());
            }
        });


        AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
    }

    private void addAmount(String wid,String amount) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/updateAmount.php", new Response.Listener<String>() {
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
                params.put("wid",wid);
                params.put("amount",amount);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
