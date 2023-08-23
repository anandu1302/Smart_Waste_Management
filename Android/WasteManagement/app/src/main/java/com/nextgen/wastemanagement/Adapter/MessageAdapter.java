package com.nextgen.wastemanagement.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgen.wastemanagement.GlobalPreference;
import com.nextgen.wastemanagement.ModelClass.MessageModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;
import com.nextgen.wastemanagement.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder>{

    ArrayList<MessageModelClass> list;
    Context context;
    String ip,uid;

    public MessageAdapter(ArrayList<MessageModelClass> list, Context context) {
        this.list = list;
        this.context = context;

        GlobalPreference globalPreference = new GlobalPreference(context);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_community_chat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MessageModelClass messageList = list.get(position);

        if (uid.equals(messageList.getUid())){

           // holder.otherMessageTextView.setVisibility(View.INVISIBLE);
            holder.otherMessageLL.setVisibility(View.GONE);
            holder.userMessageTextView.setText(messageList.getMessage());

        }else {

           // holder.otherMessageTextView.setVisibility(View.VISIBLE);
            holder.otherMessageLL.setVisibility(View.VISIBLE);
            holder.otherMessageTextView.setText(messageList.getMessage());
            holder.messageUserTextView.setText("User "+messageList.getUid());
            holder.userMessageTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView userMessageTextView;
        TextView otherMessageTextView;
        TextView messageUserTextView;
        LinearLayout otherMessageLL;


        public MyViewHolder(@NonNull View itemview) {
            super(itemview);

        userMessageTextView = itemview.findViewById(R.id.userMessageTextView);
        otherMessageTextView = itemview.findViewById(R.id.otherMessageTextView);
        messageUserTextView = itemview.findViewById(R.id.messagedUserNameTextView);
        otherMessageLL = itemview.findViewById(R.id.otherMessageLL);

        }
    }
}
