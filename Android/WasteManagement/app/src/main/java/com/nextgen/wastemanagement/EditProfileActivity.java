package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    EditText nameET;
    EditText emailET;
    EditText usernameET;
    EditText numberET;
    EditText addressET;
    EditText locationET;
    TextView submitTV;
    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private Intent intent;
    String intentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        intent = getIntent();
        intentResponse = intent.getStringExtra("userdata");

        initial();

        setData(intentResponse);

        submitTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               checkValues();
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileActivity.this,UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void checkValues() {

        if (nameET.getText().toString().equals("")) {
            nameET.setError("Please Enter name");
        }
        else if (addressET.getText().toString().equals("")) {
            addressET.setError("Please Enter Address");
        }
        else if (locationET.getText().toString().equals("")) {
            locationET.setError("Please Enter Location");
        }
        else if (emailET.getText().toString().equals("")) {
            emailET.setError("Please Enter Email");
        }
        else if (numberET.getText().equals("") || numberET.getText().length() > 10 || numberET.getText().length() < 10) {
            numberET.setError("Invalid Phone number ");
        }
        else if (!emailET.getText().toString().equals("")){

            String email = emailET.getText().toString();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Toast.makeText(UserRegisterActivity.this,"Email Validated",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditProfileActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

            }
        }else if (usernameET.getText().toString().equals("")) {
            usernameET.setError("Please Enter Username");
        }else{
            updateData();
        }
    }

    private void updateData() {
        String URL = "http://"+ ip +"/waste_management/api/editUserProfile.php";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditProfileActivity.this,""+response,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this,UserProfileActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditProfileActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();

                params.put("name",nameET.getText().toString());
                params.put("email",emailET.getText().toString());
                params.put("username",usernameET.getText().toString());
                params.put("number",numberET.getText().toString());
                params.put("address",addressET.getText().toString());
                params.put("location",locationET.getText().toString());
                params.put("uid",uid);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


    private void setData(String response) {
        try{
            JSONObject obj = new JSONObject(response);
            JSONArray array = obj.getJSONArray("data");
            JSONObject data = array.getJSONObject(0);

            String name = data.getString("name");
            String email = data.getString("email");
            String username = data.getString("username");
            String number = data.getString("number");
            String address = data.getString("address");
            String location = data.getString("location");


            nameET.setText(name);
            emailET.setText(email);
            usernameET.setText(username);
            numberET.setText(number);
            addressET.setText(address);
            locationET.setText(location);

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    private void initial() {
        nameET = findViewById(R.id.eNameEditText);
        emailET = findViewById(R.id.eEmailEditText);
        usernameET = findViewById(R.id.eUserNameEditText);
        numberET = findViewById(R.id.eNumberEditText);
        addressET = findViewById(R.id.eAddressEditText);
        locationET = findViewById(R.id.eLocationEditText);
        submitTV = findViewById(R.id.eSubmitTextView);
        backIV = findViewById(R.id.editProfileBackIV);
    }
}