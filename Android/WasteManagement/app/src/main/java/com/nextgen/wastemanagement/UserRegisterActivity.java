package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UserRegisterActivity extends AppCompatActivity {

    EditText nameET;
    EditText addressET;
    EditText locationET;
    EditText emailET;
    EditText numberET;
    EditText usernameET;
    EditText passwordET;
    Button registerBT;
    TextView signInTV;

    private GlobalPreference globalPreference;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        nameET = findViewById(R.id.uNameEditText);
        addressET = findViewById(R.id.uAddressEditText);
        locationET = findViewById(R.id.uLocationEditText);
        emailET = findViewById(R.id.uEmailEditText);
        numberET = findViewById(R.id.uNumberEditText);
        usernameET = findViewById(R.id.uUserNameEditText);
        passwordET = findViewById(R.id.uPasswordEditText);
        registerBT = findViewById(R.id.uRegisterButton);
        signInTV = findViewById(R.id.signInTextView);

        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 check();
                //register();
            }
        });

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserRegisterActivity.this,UserLoginActivity.class);
                startActivity(intent);

            }
        });
    }

    private void check() {
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
        else if (emailET.getText().toString().equals("")) {
            emailET.setError("Please Enter Email");
        }
        else if (!emailET.getText().toString().equals("")){

            String email = emailET.getText().toString();

            if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                // Toast.makeText(UserRegisterActivity.this,"Email Validated",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(UserRegisterActivity.this, "Invalid email", Toast.LENGTH_SHORT).show();

            }
        }

        else if (usernameET.getText().toString().equals("")) {
            usernameET.setError("Please Enter Username");
        }
         else if (passwordET.getText().equals("") || passwordET.getText().length() < 5) {
            passwordET.setError("Password Empty or It Does not contain 5 letters");
        } else{
             register();

        }

    }

    private void register() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){

                    Intent intent = new Intent(UserRegisterActivity.this,UserLoginActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(UserRegisterActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(UserRegisterActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",nameET.getText().toString());
                params.put("address",addressET.getText().toString());
                params.put("location",locationET.getText().toString());
                params.put("email",emailET.getText().toString());
                params.put("number",numberET.getText().toString());
                params.put("username",usernameET.getText().toString());
                params.put("password",passwordET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UserRegisterActivity.this);
        requestQueue.add(stringRequest);

    }
}