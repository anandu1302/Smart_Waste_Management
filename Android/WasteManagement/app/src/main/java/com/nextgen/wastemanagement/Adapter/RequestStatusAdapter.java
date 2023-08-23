package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.ModelClass.RequestStatusModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class RequestStatusAdapter extends RecyclerView.Adapter<RequestStatusAdapter.MyViewHolder>{

    ArrayList<RequestStatusModelClass> list;
    Context context;
    String ip,uid;

    public RequestStatusAdapter(ArrayList<RequestStatusModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_request_status,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RequestStatusModelClass requestStatusList = list.get(position);
        holder.dateTV.setText(requestStatusList.getDate());
        holder.collectionDateTV.setText(requestStatusList.getRequested_date());
        holder.timeSlotTV.setText(requestStatusList.getTime());
        holder.statusTV.setText(requestStatusList.getStatus());

        if (requestStatusList.getStatus().equals("accepted")){
            holder.workerLL.setVisibility(View.VISIBLE);
        }else{
            holder.workerLL.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView collectionDateTV;
        TextView timeSlotTV;
        TextView statusTV;
        TextView workerNameTV;
        LinearLayout workerLL;



        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            dateTV = itemview.findViewById(R.id.sRequestedDateTextView);
            collectionDateTV = itemview.findViewById(R.id.sCollectionDateTextView);
            timeSlotTV = itemview.findViewById(R.id.sTimeSlotTextView);
            statusTV = itemview.findViewById(R.id.sStatusTextView);
            workerNameTV = itemview.findViewById(R.id.sWorkerNameTextView);
            workerLL = itemview.findViewById(R.id.sWorkerLinearLayout);


        }
    }
}
