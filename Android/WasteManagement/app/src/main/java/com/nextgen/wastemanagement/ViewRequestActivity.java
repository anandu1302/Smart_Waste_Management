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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.RequestAdapter;
import com.nextgen.wastemanagement.Adapter.TipsAdapter;
import com.nextgen.wastemanagement.ModelClass.RequestModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewRequestActivity extends AppCompatActivity {

    private static String TAG ="ViewRequestActivity";

    RecyclerView wasteRequestsRV;
    ArrayList<RequestModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        wasteRequestsRV = findViewById(R.id.requestWasteRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wasteRequestsRV.setLayoutManager(layoutManager);

        getwasteRequests();

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        appbarTV.setText("View Requests");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(ViewRequestActivity.this,PickUpActivity.class);
                startActivity(bIntent);
            }
        });
    }

    private void getwasteRequests() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getRequests.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(ViewRequestActivity.this, "No Requests Available", Toast.LENGTH_SHORT).show();
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

                            list.add(new RequestModelClass(id,name,date,time,requested_date,location));

                        }

                        RequestAdapter adapter = new RequestAdapter(list,ViewRequestActivity.this);
                        wasteRequestsRV.setAdapter(adapter);

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