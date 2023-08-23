package com.nextgen.wastemanagement;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Services.LocationService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ReportActivity extends AppCompatActivity {


    EditText descET;
    Button reportBT;
    ImageView chooseIV;

    private GlobalPreference globalPreference;
    private String ip,uid;
    private String encodeImage;
    private String latitude,longitude;

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    private Uri photoUri;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        globalPreference = new GlobalPreference(this);
        ip = globalPreference.getIP();
        uid = globalPreference.getID();
        latitude = globalPreference.getLatitude();
        longitude = globalPreference.getLongitude();

       // Toast.makeText(this, "loc..."+latitude+".."+longitude, Toast.LENGTH_SHORT).show();

        descET = findViewById(R.id.wasteDescEditText);
        reportBT = findViewById(R.id.reportButton);
        chooseIV = findViewById(R.id.chooseImageView);

        chooseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        reportBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(ReportActivity.this, "Uploading Image", "Please wait", true, true);

                uploadImage();

            }
        });


    }

    private void selectImage() {

        /* Capturing image and uploading
          code can be used to capture image and upload it in android 12 and above devices with specific
          permissions in manifest file as manage storage and adding a service provider to fetch file path
         */
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, "com.nextgen.anandu", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        return imageFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Image capture was successful, process the captured image
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                // Use the encoded image as needed
                chooseIV.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ ip +"/waste_management/api/reportWaste.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("success")){
                    progressDialog.dismiss();

                    Toast.makeText(ReportActivity.this, "Reported", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(ReportActivity.this,UserHomeActivity.class);
                    startActivity(intent);
                }else{
                    progressDialog.dismiss();

                    Toast.makeText(ReportActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ReportActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("image",encodeImage);
                params.put("uid",uid);
                params.put("description",descET.getText().toString());
                params.put("latitude",latitude);
                params.put("longitude",longitude);
                return params;

            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(ReportActivity.this);
        requestQueue.add(stringRequest);
    }


}