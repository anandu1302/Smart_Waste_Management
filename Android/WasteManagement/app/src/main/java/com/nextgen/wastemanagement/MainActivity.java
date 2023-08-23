package com.nextgen.wastemanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nextgen.wastemanagement.Services.LocationService;

public class MainActivity extends AppCompatActivity {

    CardView userCV;
    CardView workerCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        userCV = findViewById(R.id.card_user);
        workerCV = findViewById(R.id.card_worker);

        userCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,UserLoginActivity.class);
                startActivity(intent);
            }
        });

        workerCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,WorkerLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}