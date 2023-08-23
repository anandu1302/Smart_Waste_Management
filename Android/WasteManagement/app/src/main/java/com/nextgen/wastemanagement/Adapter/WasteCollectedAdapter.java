package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.wastemanagement.CheckOutActivity;
import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.RequestStatusModelClass;
import com.nextgen.wastemanagement.ModelClass.WasteCollectedModelClass;
import com.nextgen.wastemanagement.ModelClass.WasteReportModelClass;
import com.nextgen.wastemanagement.PaymentActivity;
import com.nextgen.wastemanagement.ProductDetailsActivity;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class WasteCollectedAdapter extends RecyclerView.Adapter<WasteCollectedAdapter.MyViewHolder> {

    ArrayList<WasteCollectedModelClass> list;
    Context context;
    String ip,wid;

    public WasteCollectedAdapter(ArrayList<WasteCollectedModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        wid = globalPreference.getID();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_waste_collected,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        WasteCollectedModelClass collectedList = list.get(position);
        holder.dateTV.setText(collectedList.getDate());
        holder.collectionDateTV.setText(collectedList.getRequested_date());
        holder.timeSlotTV.setText(collectedList.getTime());
        holder.workerNameTV.setText(collectedList.getName());
        holder.amountTV.setText("₹ "+collectedList.getAmount());

        holder.paymentBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("cid",collectedList.getId());
                intent.putExtra("amount",collectedList.getAmount());
                context.startActivity(intent);
            }
        });



    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dateTV;
        TextView collectionDateTV;
        TextView timeSlotTV;
        TextView amountTV;
        TextView workerNameTV;
        Button paymentBT;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

            dateTV = itemview.findViewById(R.id.cRequestedDateTextView);
            collectionDateTV = itemview.findViewById(R.id.cCollectionDateTextView);
            timeSlotTV = itemview.findViewById(R.id.cTimeSlotTextView);
            amountTV = itemview.findViewById(R.id.cAmountTextView);
            workerNameTV = itemview.findViewById(R.id.cWorkerNameTextView);
            paymentBT = itemview.findViewById(R.id.cMakePaymentButton);

        }
    }

}
