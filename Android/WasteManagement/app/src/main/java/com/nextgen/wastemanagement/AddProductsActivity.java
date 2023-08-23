package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddProductsActivity extends AppCompatActivity {

    EditText pNameET;
    EditText pDescET;
    EditText priceET;
    ImageView productIV;
    Button postBT;

    private ImageView backIV;
    private TextView appBarTV;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private String encodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        backIV = findViewById(R.id.BackImageButton);
        appBarTV = findViewById(R.id.appBarTitle);

        appBarTV.setText("Sell Products");


        pNameET = findViewById(R.id.productNameEditText);
        pDescET = findViewById(R.id.productDescEditText);
        priceET = findViewById(R.id.priceEditText);
        productIV = findViewById(R.id.cProductImageView);
        postBT = findViewById(R.id.postButton);


        productIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),100);

            }
        });

        postBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                check();
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductsActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (resultCode == RESULT_OK){
            if (requestCode == 100){
                Uri filepath = data.getData();

                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte [] imageBytes = baos.toByteArray();
                    encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    productIV.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void check() {

        if (pNameET.getText().toString().equals("")){
            pNameET.setError("This Field is Empty");
        } else if(pDescET.getText().toString().equals("")){
            pDescET.setError("This Field is Empty");
        }else if (priceET.getText().toString().equals("")){
            priceET.setError("This Field is Empty");
        }else if (encodeImage != null && !encodeImage.isEmpty()){
           addProducts();
        }else{
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void addProducts() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/addProducts.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")){
                    Toast.makeText(AddProductsActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AddProductsActivity.this,""+response,Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddProductsActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("pname",pNameET.getText().toString());
                params.put("pdesc",pDescET.getText().toString());
                params.put("price",priceET.getText().toString());
                params.put("image",encodeImage);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(AddProductsActivity.this);
        requestQueue.add(stringRequest);
    }
}