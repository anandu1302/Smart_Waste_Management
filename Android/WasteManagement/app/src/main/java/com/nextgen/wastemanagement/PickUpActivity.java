package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.TipsAdapter;
import com.nextgen.wastemanagement.Adapter.WorkerRequestAdapter;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;
import com.nextgen.wastemanagement.ModelClass.WorkerRequestModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PickUpActivity extends AppCompatActivity {

    private static String TAG ="TipsActivity";

    LinearLayout viewRequestLL;
    RecyclerView workerRequestsRV;
    ArrayList<WorkerRequestModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        workerRequestsRV = findViewById(R.id.workerRequestsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        workerRequestsRV.setLayoutManager(layoutManager);

        getRequests();

        viewRequestLL = findViewById(R.id.viewRequestsLL);
        viewRequestLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PickUpActivity.this,ViewRequestActivity.class);
                startActivity(intent);
            }
        });

        backIV = findViewById(R.id.pickUpBackImageView);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickUpActivity.this,WorkerHomeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getRequests() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getWasteRequests.php?wid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(PickUpActivity.this, "No Requests Pending", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String date = object.getString("date");
                            String time = object.getString("time");
                            String requested_date = object.getString("requested_date");
                            String location = object.getString("location");

                            list.add(new WorkerRequestModelClass(id,name,date,time,requested_date,location));

                        }

                        WorkerRequestAdapter adapter = new WorkerRequestAdapter(list,PickUpActivity.this);
                        workerRequestsRV.setAdapter(adapter);

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