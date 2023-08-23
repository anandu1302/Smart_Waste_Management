package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.MessageAdapter;
import com.nextgen.wastemanagement.Adapter.TipsAdapter;
import com.nextgen.wastemanagement.ModelClass.MessageModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommunitiesChatActivity extends AppCompatActivity {

    private static String TAG ="CommunitiesChatActivity";

    RecyclerView messageRV;
    ArrayList<MessageModelClass> list;

    EditText messageET;
    ImageView sendIV;

    private String communityId,communityTitle;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communities_chat);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        communityId = getIntent().getStringExtra("communityID");
        communityTitle = getIntent().getStringExtra("communityTitle");

        //Toast.makeText(this, "communityID"+communityId, Toast.LENGTH_SHORT).show();

        iniit();

        appbarTV.setText(communityTitle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        messageRV.setLayoutManager(layoutManager);

        getMessages();

        sendIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getMessages();
            }
        },1000);


    }



    private void sendMessage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/sendMessage.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    messageET.setText("");

                    getMessages();

                }else{
                    Toast.makeText(CommunitiesChatActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error fetching messages: " + error.toString());

                Toast.makeText(CommunitiesChatActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("communityId",communityId);
                params.put("message",messageET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CommunitiesChatActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getMessages() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getMessages.php?cid="+communityId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);



                if (response.equals("failed")){
                    Toast.makeText(CommunitiesChatActivity.this, "No Messages yet", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String uid = object.getString("uid");
                            String message = object.getString("message");

                            // Scroll to the last item in the list
                            if (list.size() > 0) {
                                messageRV.scrollToPosition(list.size() - 1);
                            }

                            list.add(new MessageModelClass(id,uid,message));



                        }

                        MessageAdapter adapter = new MessageAdapter(list,CommunitiesChatActivity.this);
                        messageRV.setAdapter(adapter);

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void iniit() {
        messageRV = findViewById(R.id.messageRecyclerView);
        messageET = findViewById(R.id.messageEditText);
        sendIV = findViewById(R.id.sendImageView);
        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);
    }
}