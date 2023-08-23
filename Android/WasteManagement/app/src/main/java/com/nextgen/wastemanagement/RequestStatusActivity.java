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
import com.nextgen.wastemanagement.Adapter.RequestAdapter;
import com.nextgen.wastemanagement.Adapter.RequestStatusAdapter;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.ModelClass.RequestStatusModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestStatusActivity extends AppCompatActivity {

    private static String TAG ="RequestStatusActivity";

    RecyclerView requestStatusRV;
    ArrayList<RequestStatusModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        requestStatusRV = findViewById(R.id.requestStatusRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        requestStatusRV.setLayoutManager(layoutManager);

        appbarTV.setText("Request Status");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(RequestStatusActivity.this,WasteRequestActivity.class);
                startActivity(bIntent);
            }
        });

        getRequests();
    }

    private void getRequests() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/requestStatus.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(RequestStatusActivity.this, "No Requests Available", Toast.LENGTH_SHORT).show();
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
                            String status = object.getString("status");

                            list.add(new RequestStatusModelClass(id,name,date,time,requested_date,status));

                        }

                        RequestStatusAdapter adapter = new RequestStatusAdapter(list,RequestStatusActivity.this);
                        requestStatusRV.setAdapter(adapter);

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