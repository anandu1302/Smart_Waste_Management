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
import com.nextgen.wastemanagement.Adapter.PurchaseHistoryAdapter;
import com.nextgen.wastemanagement.Adapter.ReportsAdapter;
import com.nextgen.wastemanagement.ModelClass.PurchaseHistoryModelClass;
import com.nextgen.wastemanagement.ModelClass.ReportsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PurchaseHistoryActivity extends AppCompatActivity {

    private static String TAG ="ViewReportsActivity";

    RecyclerView historyRV;
    ArrayList<PurchaseHistoryModelClass> list;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_history);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        historyRV = findViewById(R.id.historyRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        historyRV.setLayoutManager(layoutManager);

        getHistory();

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        appbarTV.setText("Purchase History");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(PurchaseHistoryActivity.this,UserHomeActivity.class);
                startActivity(bIntent);
            }
        });
    }

    private void getHistory() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getHistory.php?uid="+uid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(PurchaseHistoryActivity.this, "No History Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String image = object.getString("image");
                            String price = object.getString("price");
                            String orderid = object.getString("orderid");

                            list.add(new PurchaseHistoryModelClass(id,name,image,price,orderid));

                        }

                        PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(list,PurchaseHistoryActivity.this);
                        historyRV.setAdapter(adapter);

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