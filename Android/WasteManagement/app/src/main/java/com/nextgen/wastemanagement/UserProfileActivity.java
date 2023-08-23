package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class UserProfileActivity extends AppCompatActivity {

    TextView nameTV;
    TextView emailTV;
    TextView usernameTV;
    TextView phoneTV;
    TextView locationTV;
    TextView addressTV;
    ImageView backIV;
    ImageView editProfileIV;

    private GlobalPreference globalPreference;
    private String uid;
    private String ip;
    String userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        nameTV = findViewById(R.id.pNameTextView);
        emailTV = findViewById(R.id.pEmailTextView);
        usernameTV = findViewById(R.id.pUsernameTextView);
        phoneTV = findViewById(R.id.pPhoneTextView);
        addressTV = findViewById(R.id.pAddressTextView);
        locationTV = findViewById(R.id.pLocationTextView);
        backIV = findViewById(R.id.profileBackIV);
        editProfileIV = findViewById(R.id.editProfileImageView);

        getUserDetails();

        editProfileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,EditProfileActivity.class);
                intent.putExtra("userdata",userdata);
                startActivity(intent);
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfileActivity.this,UserHomeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getUserDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip +"/waste_management/api/userProfile.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                userdata = response;


                if (!response.equals("")) ;
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject data = jsonArray.getJSONObject(0);

                        String name = data.getString("name");
                        String email = data.getString("email");
                        String address = data.getString("address");
                        String phone = data.getString("number");
                        String location = data.getString("location");
                        String username = data.getString("username");



                        nameTV.setText(name);
                        emailTV.setText(email);
                        addressTV.setText(address);
                        phoneTV.setText(phone);
                        locationTV.setText(location);
                        usernameTV.setText(username);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserProfileActivity.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            @Nullable

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", uid);
                return params;


            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(UserProfileActivity.this);
        requestQueue.add(stringRequest);
    }
}