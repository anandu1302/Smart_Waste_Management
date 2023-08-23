package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckOutActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";

    private RadioButton cardRadioButton;
    private TextView paymentTVButton,amountTextView;
    EditText accountNoET;
    EditText pinET;
    LinearLayout accountDetailsLL;


    private String uid;
    private String ip;
    private String ptype;
    private Intent intent;
    private TextView title;

    private ImageView BackButton;

    private GlobalPreference globalPreference;
    private String pid;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();

        pid = getIntent().getStringExtra("pid");
        amount = getIntent().getStringExtra("amount");

        //Toast.makeText(this, pid+"..."+amount, Toast.LENGTH_SHORT).show();

        title = findViewById(R.id.appBarTitle);
        title.setText("Payment Checkout");

        BackButton = (ImageView) findViewById(R.id.BackImageButton);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        iniit();

        amountTextView.setText("₹ "+amount);

        cardRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cardRadioButton.isChecked()) {
                    ptype = "Upi Payment";
                    accountDetailsLL.setVisibility(View.VISIBLE);

                }
                else{
                    cardRadioButton.setChecked(true);
                }
            }
        });


        paymentTVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: "+ptype);

                if(!cardRadioButton.isChecked())
                {
                    Toast.makeText(CheckOutActivity.this, "Please select a payment method ", Toast.LENGTH_SHORT).show();
                }
                else if(cardRadioButton.isChecked() && accountNoET.getText().toString().equals("") ||
                        cardRadioButton.isChecked() && pinET.getText().toString().equals(""))
                {
                    Toast.makeText(CheckOutActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    paynow();
                }

            }
        });

    }

    private void paynow() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/checkOut.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG,"onResponse: "+response);

               if (response.equals("failed")){
                   Toast.makeText(CheckOutActivity.this,"Your Account Does not have Enough Balance", Toast.LENGTH_LONG).show();
               }else if (response.equals("accerror")){
                   Toast.makeText(CheckOutActivity.this,"Account Does not exist", Toast.LENGTH_LONG).show();
               }else if (response.equals("pin")){
                   Toast.makeText(CheckOutActivity.this, "Incorrect Pin", Toast.LENGTH_LONG).show();
               }else if (response.equals("success")){
                   Toast.makeText(CheckOutActivity.this, "Payment Success", Toast.LENGTH_LONG).show();

                   Intent intent = new Intent(CheckOutActivity.this,UserHomeActivity.class);
                   startActivity(intent);
               }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "onErrorResponse: "+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",uid);
                params.put("pid",pid);
                params.put("accountNo",accountNoET.getText().toString());
                params.put("pin",pinET.getText().toString());
                params.put("amount",amount);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void iniit() {

        cardRadioButton = findViewById(R.id.radioCard);
        paymentTVButton = findViewById(R.id.paymentTextViewButton);
        amountTextView = findViewById(R.id.amountTextView);
        accountDetailsLL = findViewById(R.id.accountDetailsLL);
        accountNoET = findViewById(R.id.accountNumberEditText);
        pinET = findViewById(R.id.pinNumberEditText);

    }
}