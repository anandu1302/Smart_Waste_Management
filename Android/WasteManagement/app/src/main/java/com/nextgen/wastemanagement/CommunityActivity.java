package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.CommunityAdapter;
import com.nextgen.wastemanagement.Adapter.MyCommunitiesAdapter;
import com.nextgen.wastemanagement.Adapter.TipsAdapter;
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.ModelClass.CommunityModelClass;
import com.nextgen.wastemanagement.ModelClass.MyCommunitiesModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {

    private static String TAG ="CommunityActivity";

    /* Communities RV */
    RecyclerView communityRV;
    ArrayList<CommunityModelClass> list;

    /* My Communities RV */
    RecyclerView myCommunityRV;
    ArrayList<MyCommunitiesModelClass> mList;

    LinearLayout userCommunitiesLL;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        /* Communities RV */
        communityRV = findViewById(R.id.communityRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        communityRV.setLayoutManager(layoutManager);

        getCommunities();

        /* My Communities RV */
        myCommunityRV = findViewById(R.id.myCommunityRecyclerView);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        myCommunityRV.setLayoutManager(myLayoutManager);
        myCommunityRV.setItemAnimator(new DefaultItemAnimator());

        userCommunitiesLL = findViewById(R.id.userCommunitiesLL);

        myCommunities();

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        appbarTV.setText("Communities");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(CommunityActivity.this,UserHomeActivity.class);
                startActivity(bIntent);
            }
        });
    }

    private void myCommunities() {

        mList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/userCommunities.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(CommunityActivity.this, "No Communities Available", Toast.LENGTH_SHORT).show();

                    userCommunitiesLL.setVisibility(View.INVISIBLE);
                }
                else{
                    try{
                        userCommunitiesLL.setVisibility(View.VISIBLE);

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String image = object.getString("image");

                            mList.add(new MyCommunitiesModelClass(id,title,image));

                        }

                        MyCommunitiesAdapter userAdapter = new MyCommunitiesAdapter(mList,CommunityActivity.this);
                        myCommunityRV.setAdapter(userAdapter);

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

    private void getCommunities() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getCommunities.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(CommunityActivity.this, "No Communities Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String image = object.getString("image");

                            list.add(new CommunityModelClass(id,title,image));

                        }

                        CommunityAdapter adapter = new CommunityAdapter(list,CommunityActivity.this);
                        communityRV.setAdapter(adapter);

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
}