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
import com.nextgen.wastemanagement.Adapter.ReportsAdapter;
import com.nextgen.wastemanagement.Adapter.WasteReportAdapter;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;
import com.nextgen.wastemanagement.ModelClass.WasteReportModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WasteReportsActivity extends AppCompatActivity {

    private static String TAG ="WasteReportsActivity";

    RecyclerView wasteReportRV;
    ArrayList<WasteReportModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_reports);

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        wasteReportRV = findViewById(R.id.wasteReportsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        wasteReportRV.setLayoutManager(layoutManager);

        appbarTV.setText("Waste Reports");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(WasteReportsActivity.this,WorkerHomeActivity.class);
                startActivity(bIntent);
            }
        });

        getReports();
    }

    private void getReports() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getWasteReports.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(WasteReportsActivity.this, "No Reports Available", Toast.LENGTH_SHORT).show();
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
                            String latitude = object.getString("latitude");
                            String longitude = object.getString("longitude");
                            String status = object.getString("status");

                            list.add(new WasteReportModelClass(id,image,desc,date,latitude,longitude,status));

                        }

                        WasteReportAdapter adapter = new WasteReportAdapter(list,WasteReportsActivity.this);
                        wasteReportRV.setAdapter(adapter);

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