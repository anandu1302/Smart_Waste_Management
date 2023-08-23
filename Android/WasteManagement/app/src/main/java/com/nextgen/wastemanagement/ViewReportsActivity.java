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
import com.nextgen.wastemanagement.Adapter.CampaignsAdapter;
import com.nextgen.wastemanagement.Adapter.ReportsAdapter;
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewReportsActivity extends AppCompatActivity {


    private static String TAG ="ViewReportsActivity";

    RecyclerView reportRV;
    ArrayList<ReportsModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        reportRV = findViewById(R.id.reportsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        reportRV.setLayoutManager(layoutManager);

        getReports();

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        appbarTV.setText("View Reports");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(ViewReportsActivity.this,UserHomeActivity.class);
                startActivity(bIntent);
            }
        });


    }

    private void getReports() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getReports.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(ViewReportsActivity.this, "No Reports Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String image = object.getString("image");
                            String desc = object.getString("description");
                            String date = object.getString("date");
                            String status = object.getString("status");

                            list.add(new ReportsModelClass(id,image,desc,date,status));

                        }

                        ReportsAdapter adapter = new ReportsAdapter(list,ViewReportsActivity.this);
                        reportRV.setAdapter(adapter);

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