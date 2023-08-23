package com.nextgen.wastemanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.nextgen.wastemanagement.Services.LocationService;

import java.util.ArrayList;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static String TAG ="UserHomeActivity";

    Fragment fragment = null;

    TextView nav_usernameTV;
    TextView nav_emailTV;

    String name,email;

    private GlobalPreference globalPreference;
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent lIntent = new Intent(UserHomeActivity.this, LocationService.class);
        startService(lIntent);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        name = globalPreference.getName();
        email = globalPreference.getEmail();

        displaySelectedScreen(R.id.nav_home);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView =  navigationView.getHeaderView(0);
        nav_usernameTV =hView.findViewById(R.id.txtuser);
        nav_emailTV = hView.findViewById(R.id.txtemail);



        nav_usernameTV.setText(name);
        nav_emailTV.setText(email);

    }

    private void displaySelectedScreen(int itemId) {

        switch (itemId) {
            case R.id.nav_profile:
                Intent pIntent = new Intent(UserHomeActivity.this,UserProfileActivity.class);
                startActivity(pIntent);
                break;

            case R.id.nav_home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_campaign:
                fragment = new CampaignFragment();
                break;

            case R.id.nav_rewards:
                Intent rIntent = new Intent(UserHomeActivity.this,PointsActivity.class);
                startActivity(rIntent);
                break;

            case R.id.nav_Report:
                fragment = new ReportFragment();
                break;

            case R.id.nav_recycling:

                Uri gmmIntentUri = Uri.parse("geo:0,0?q=recycling centers");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity (mapIntent);

                break;

            case R.id.nav_history:
                Intent hIntent = new Intent(UserHomeActivity.this,PurchaseHistoryActivity.class);
                startActivity(hIntent);
                break;

            case R.id.nav_feedback:
                Intent fIntent = new Intent(UserHomeActivity.this,FeedbackActivity.class);
                startActivity(fIntent);
                break;

            case R.id.nav_logout:
                 logout();
                break;

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        displaySelectedScreen(id);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        new AlertDialog.Builder(UserHomeActivity.this)
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UserHomeActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


}