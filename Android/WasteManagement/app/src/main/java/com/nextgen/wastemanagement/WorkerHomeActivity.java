package com.nextgen.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nextgen.wastemanagement.databinding.ActivityWorkerHomeBinding;

public class WorkerHomeActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    /* Google map */
    private GoogleMap mMap;
    private ActivityWorkerHomeBinding binding;

    private LocationManager locationManager;
    private Location currentLocation;
    private boolean first = true;
    private LatLng origin;

    TextView nameTV;
    CardView reportsCV;
    CardView pickUpCV;
    CardView publicBinsCV;
    CardView feedbackCV;
    ImageView backIV;

    private GlobalPreference globalPreference;
    private String ip,wid,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        wid = globalPreference.getID();
        name = globalPreference.getName();


        binding = ActivityWorkerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iniit();

        nameTV.setText(name);

        reportsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerHomeActivity.this,WasteReportsActivity.class);
                startActivity(intent);
            }
        });

        pickUpCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerHomeActivity.this,PickUpActivity.class);
                startActivity(intent);
            }
        });

        publicBinsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerHomeActivity.this,PublicBinsActivity.class);
                intent.putExtra("type","worker");
                startActivity(intent);
            }
        });

        feedbackCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkerHomeActivity.this,ViewFeedbackActivity.class);
                startActivity(intent);
            }
        });

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }



    /**
     map fragment
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {


                currentLocation = location;
                if(first) {
                    origin = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(
                            origin).zoom(15).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    first = false;

                }
            }
        });

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

   /*
    map fragment end
    */

    private void logout() {

        new AlertDialog.Builder(WorkerHomeActivity.this)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(WorkerHomeActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void iniit() {
        nameTV = findViewById(R.id.wNameTextView);
        reportsCV = findViewById(R.id.card_reports);
        pickUpCV = findViewById(R.id.card_pickup);
        publicBinsCV = findViewById(R.id.card_publicBins);
        feedbackCV = findViewById(R.id.card_feedbacks);
        backIV = findViewById(R.id.workerBackImageView);
    }

}