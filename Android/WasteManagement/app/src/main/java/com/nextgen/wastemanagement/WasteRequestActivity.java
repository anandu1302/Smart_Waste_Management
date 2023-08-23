package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WasteRequestActivity extends AppCompatActivity {

    CardView requestServiceCV;
    CardView viewRequestCV;
    CardView paymentCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_request);

        requestServiceCV = findViewById(R.id.card_requestService);
        viewRequestCV = findViewById(R.id.card_viewRequests);
        paymentCV = findViewById(R.id.card_payments);

        requestServiceCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WasteRequestActivity.this,RequestServiceActivity.class);
                startActivity(intent);
            }
        });

        viewRequestCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WasteRequestActivity.this,RequestStatusActivity.class);
                startActivity(intent);
            }
        });

        paymentCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WasteRequestActivity.this,WasteCollectedActivity.class);
                startActivity(intent);
            }
        });
    }
}