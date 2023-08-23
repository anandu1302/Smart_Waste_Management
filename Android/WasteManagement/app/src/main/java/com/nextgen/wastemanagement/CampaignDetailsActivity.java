package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CampaignDetailsActivity extends AppCompatActivity {

    private static String TAG ="CampaignDetailsActivity";

    TextView campaignNameTV;
    TextView campaignDescTV;
    ImageView campaignIV;
    ImageView backIV;
    Button joinCampaignBT;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_details);

        cid = getIntent().getStringExtra("campaignId");

       // Toast.makeText(this, "campaign..."+cid, Toast.LENGTH_SHORT).show();

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        campaignNameTV = findViewById(R.id.dCampaignNameTextView);
        campaignDescTV = findViewById(R.id.dCampaignDescTextView);
        campaignIV = findViewById(R.id.dCampaignImageView);
        backIV = findViewById(R.id.dBackImageView);
        joinCampaignBT = findViewById(R.id.dJoinCampaignButton);

        loadCampaignDetails();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bintent = new Intent(CampaignDetailsActivity.this,UserHomeActivity.class);
                startActivity(bintent);
            }
        });

        joinCampaignBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinCampaign();
            }
        });

    }

    private void joinCampaign() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/joinCampaign.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Toast.makeText(CampaignDetailsActivity.this, "Joined SuccessFully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CampaignDetailsActivity.this,UserHomeActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(CampaignDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CampaignDetailsActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("cid",cid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(CampaignDetailsActivity.this);
        requestQueue.add(stringRequest);
    }

    private void loadCampaignDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ip+"/waste_management/api/getCampaignDetails.php?cid="+cid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    Toast.makeText(CampaignDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String description = object.getString("description");
                            String image = object.getString("image");

                            if (!image.equals("")) {
                                Glide.with(getApplicationContext())
                                        .load("http://" + ip + "/waste_management/campaign_tbl/uploads/" + image)
                                        .into(campaignIV);
                            }

                            campaignNameTV.setText(title);
                            campaignDescTV.setText(description);


                        }

                    } catch (JSONException e) {
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

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}