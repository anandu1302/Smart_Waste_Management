package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.TipsAdapter;
import com.nextgen.wastemanagement.ModelClass.TipsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private static final String TAG = "FeedbackActivity";

    RatingBar wRatingBar;
    TextView wRatingScale;
    EditText feedbackET;
    Button submitBT;

    private Spinner workersDropDown;
    private List<String> workers = new ArrayList<>();

    GlobalPreference globalPreference;
    private String ip,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        wRatingBar = findViewById(R.id.ratingBar);
        wRatingScale = findViewById(R.id.ratingScaleTextView);
        feedbackET = findViewById(R.id.feedbackEditText);
        submitBT =  findViewById(R.id.submitButton);

        // Fetch the worker names and populate the workers list
        getWorkers();

        workersDropDown = findViewById(R.id.workersDropDown);

        wRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                wRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        wRatingScale.setText("Very bad");
                        break;
                    case 2:
                        wRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        wRatingScale.setText("Good");
                        break;
                    case 4:
                        wRatingScale.setText("Great");
                        break;
                    case 5:
                        wRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        wRatingScale.setText("");
                }
            }
        });



        submitBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String selectedWorker = "";
                if (workersDropDown.getSelectedItem() != null) {
                    selectedWorker = workersDropDown.getSelectedItem().toString();
                }

                Log.d(TAG, "Selected worker: " + selectedWorker);
               // Toast.makeText(FeedbackActivity.this, "Selected worker: " + selectedWorker, Toast.LENGTH_SHORT).show();
                check();
            }
        });
    }

    private void check() {

        final String ratingScale = String.valueOf(wRatingBar.getRating());

        if (workersDropDown.getSelectedItem().toString().equals("")){
            Toast.makeText(this, "Please Select a Worker", Toast.LENGTH_SHORT).show();
        }else if(feedbackET.getText().toString().equals("")){
            Toast.makeText(this, "Please Provide your feedback", Toast.LENGTH_SHORT).show();
        }else{
             sendRating();
        }
    }


    private void sendRating() {

        final String ratingScale = String.valueOf(wRatingBar.getRating());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://" + ip + "/waste_management/api/feedback.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("success")) {
                    Toast.makeText(FeedbackActivity.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FeedbackActivity.this,UserHomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(FeedbackActivity.this, "Failed to Submit Feedback" + response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FeedbackActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("workerId",workersDropDown.getSelectedItem().toString());
                params.put("rating",ratingScale);
                params.put("feedback",feedbackET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(FeedbackActivity.this);
        requestQueue.add(stringRequest);
    }

    private void getWorkers() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getWorkers.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponseWorkersList: "+response);

                if (response.equals("failed")){
                    Toast.makeText(FeedbackActivity.this, "No Workers Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name");
                            workers.add(name);

                            // Populate the spinner with worker names using ArrayAdapter
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(FeedbackActivity.this,
                                    android.R.layout.simple_spinner_item, workers);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            workersDropDown.setAdapter(adapter);
                        }

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