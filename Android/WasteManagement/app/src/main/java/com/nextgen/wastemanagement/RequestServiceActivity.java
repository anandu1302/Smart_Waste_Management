package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RequestServiceActivity extends  AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView dateTV;
    LinearLayout dateLL;
    Button sendRequestBT;


    Spinner timeSlotSpinner;
    String[] slots = new String[] {"9 AM - 12 PM","12 PM - 3 PM","3 PM - 6 PM"};
    private String timeSlot;

    private String requestedDate;

    private GlobalPreference globalPreference;
    private String ip,uid;

    private TextView appbarTV;
    private ImageView backIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        dateTV = findViewById(R.id.dateTextView);
        dateLL = findViewById(R.id.dateLinearLayout);
        timeSlotSpinner = findViewById(R.id.timeSlotSpinner);
        sendRequestBT = findViewById(R.id.sendRequestButton);

        appbarTV = findViewById(R.id.appBarTitle);
        backIV = findViewById(R.id.BackImageButton);

        appbarTV.setText("Request Service");
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bIntent = new Intent(RequestServiceActivity.this,WasteRequestActivity.class);
                startActivity(bIntent);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,slots);
        timeSlotSpinner.setAdapter(adapter);

        timeSlotSpinner.setOnItemSelectedListener(this);

        dateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Set the minimum date as the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RequestServiceActivity.this,
                        null,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)
                );

                // Set the minimum date as the current date
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        requestedDate = dateTV.getText().toString();

                    }
                });

                datePickerDialog.show();
            }
        });

        sendRequestBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestService();
            }
        });
    }

    private void requestService() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/requestService.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                
                if(response.equals("success")){
                    Toast.makeText(RequestServiceActivity.this, "Service Requested", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RequestServiceActivity.this,UserHomeActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RequestServiceActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RequestServiceActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("requestedDate",requestedDate);
                params.put("time",timeSlot);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RequestServiceActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id){
        switch (position){
            case 0:
                timeSlot = "9 AM - 12 PM";
                break;
            case 1:
                timeSlot = "12 PM - 3 PM";
                break;
            case 2:
                timeSlot = "3 PM - 6 PM";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){
        timeSlot = "";
    }
}