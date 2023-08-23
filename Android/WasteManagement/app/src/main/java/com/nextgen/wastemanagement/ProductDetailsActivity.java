package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class ProductDetailsActivity extends AppCompatActivity {

    private static String TAG ="ProductDetailsActivity";

    TextView pnameTV;
    TextView pdescTV;
    TextView priceTV;
    ImageView productIV;
    Button buyBT;

    private GlobalPreference globalPreference;
    private String ip;
    private String pid;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        pid = getIntent().getStringExtra("pid");

        //Toast.makeText(this, "product..."+pid, Toast.LENGTH_SHORT).show();

        pnameTV = findViewById(R.id.dProductTitleTextView);
        pdescTV = findViewById(R.id.dProductDescTextView);
        productIV = findViewById(R.id.dProductImageView);
        priceTV = findViewById(R.id.dProductPriceTextView);
        buyBT = findViewById(R.id.dBuyNowButton);

        loadProductDetails();

        buyBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProductDetailsActivity.this,CheckOutActivity.class);
                intent.putExtra("pid",pid);
                intent.putExtra("amount",price);
                startActivity(intent);
            }
        });
    }

    private void loadProductDetails() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ip+"/waste_management/api/productDetails.php?pid="+pid, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    Toast.makeText(ProductDetailsActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String pName = object.getString("pname");
                            String description = object.getString("description");
                                   price = object.getString("price");
                            String image = object.getString("image");

                            if (!image.equals("")) {
                                Glide.with(getApplicationContext())
                                        .load("http://" + ip + "/waste_management/products_tbl/uploads/" + image)
                                        .into(productIV);
                            }

                            pnameTV.setText(pName);
                            pdescTV.setText(description);
                            priceTV.setText("₹ "+price);


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