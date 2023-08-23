package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.FeedbacksModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class FeedbacksAdapter extends RecyclerView.Adapter<FeedbacksAdapter.MyViewHolder>{

    ArrayList<FeedbacksModelClass> list;
    Context context;
    String ip;

    public FeedbacksAdapter(ArrayList<FeedbacksModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_feedback,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        FeedbacksModelClass feedbackList = list.get(position);
        holder.userNameTV.setText(feedbackList.getName());
        holder.feedbackTV.setText(feedbackList.getFeedback());

        holder.ratingBar.setRating(Float.valueOf(feedbackList.getRating()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userNameTV;
        TextView feedbackTV;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

          userNameTV = itemview.findViewById(R.id.wUserNameTextView);
          feedbackTV = itemview.findViewById(R.id.wFeedbackTextView);
          ratingBar = itemview.findViewById(R.id.workerRatingBar);

        }
    }
}
