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
import com.nextgen.wastemanagement.Adapter.PublicBinAdapter;
import com.nextgen.wastemanagement.Adapter.TipsAdapter;
import com.nextgen.wastemanagement.ModelClass.PublicBinModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PublicBinsActivity extends AppCompatActivity {

    private static String TAG ="PublicBinsActivity";

    RecyclerView publicBinRV;
    ArrayList<PublicBinModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;

    private TextView appbarTV;
    private ImageView backIV;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_bins);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        type = getIntent().getStringExtra("type");

        publicBinRV = findViewById(R.id.publicBinRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        publicBinRV.setLayoutManager(layoutManager);

        getBins();

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        appbarTV.setText("Public Bins");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("user")){
                    Intent bIntent = new Intent(PublicBinsActivity.this,UserHomeActivity.class);
                    startActivity(bIntent);
                }else{
                    Intent bIntent = new Intent(PublicBinsActivity.this,WorkerHomeActivity.class);
                    startActivity(bIntent);
                }

            }
        });

    }

    private void getBins() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getPublicBins.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(PublicBinsActivity.this, "No Bins Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String location = object.getString("location");
                            String latitude = object.getString("latitude");
                            String longitude = object.getString("longitude");

                            list.add(new PublicBinModelClass(id,location,latitude,longitude));

                        }

                        PublicBinAdapter adapter = new PublicBinAdapter(list,PublicBinsActivity.this);
                        publicBinRV.setAdapter(adapter);

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