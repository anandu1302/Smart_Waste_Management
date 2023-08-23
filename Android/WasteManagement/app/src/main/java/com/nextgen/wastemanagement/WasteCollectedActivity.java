package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.RequestStatusAdapter;
import com.nextgen.wastemanagement.Adapter.WasteCollectedAdapter;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.ModelClass.RequestStatusModelClass;
import com.nextgen.wastemanagement.ModelClass.WasteCollectedModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WasteCollectedActivity extends AppCompatActivity {

    private static String TAG ="WasteCollectedActivity";

    RecyclerView wasteCollectedRV;
    ArrayList<WasteCollectedModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_collected);

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        wasteCollectedRV = findViewById(R.id.wasteCollectedRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wasteCollectedRV.setLayoutManager(layoutManager);

        appbarTV.setText("Waste Collected");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(WasteCollectedActivity.this,WasteRequestActivity.class);
                startActivity(bIntent);
            }
        });

        getCollectedStatus();
    }

    private void getCollectedStatus() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/wasteCollected.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(WasteCollectedActivity.this, "No Requests Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("worker_name");
                            String date = object.getString("date");
                            String time = object.getString("time");
                            String requested_date = object.getString("requested_date");
                            String wid = object.getString("wid");
                            String amount = object.getString("amount");

                            list.add(new WasteCollectedModelClass(id,name,date,time,requested_date,wid,amount));

                        }

                        WasteCollectedAdapter adapter = new WasteCollectedAdapter(list,WasteCollectedActivity.this);
                        wasteCollectedRV.setAdapter(adapter);

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