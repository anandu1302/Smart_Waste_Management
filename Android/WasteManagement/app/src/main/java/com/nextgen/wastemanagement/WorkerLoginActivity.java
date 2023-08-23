package com.nextgen.wastemanagement;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WorkerLoginActivity extends AppCompatActivity {

    EditText emailET;
    EditText passwordET;
    Button loginBT;

    private GlobalPreference globalPreference;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_login);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();

        emailET = findViewById(R.id.emailEditText);
        passwordET = findViewById(R.id.passwordEditText);
        loginBT = findViewById(R.id.wLoginButton);

        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailET.getText().toString().isEmpty() || passwordET.getText().toString().isEmpty()){

                    Toast.makeText(WorkerLoginActivity.this, "Please Fill the Fields", Toast.LENGTH_SHORT).show();
                }else{
                    wLogin();
                }

               // wLogin();
            }
        });
    }

    private void wLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/wLogin.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                if(response.equals("failed")){
                    Toast.makeText(WorkerLoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String id = jsonObject.getString("id");
                        globalPreference.saveID(id);
                        String name = jsonObject.getString("name");
                        globalPreference.saveName(name);

                        Intent intent = new Intent(WorkerLoginActivity.this,WorkerHomeActivity.class);
                        startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:"+error);
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",emailET.getText().toString());
                params.put("password",passwordET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}