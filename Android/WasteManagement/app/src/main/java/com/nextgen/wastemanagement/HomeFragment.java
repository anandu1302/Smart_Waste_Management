package com.nextgen.wastemanagement;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nextgen.wastemanagement.Adapter.CampaignsAdapter;
import com.nextgen.wastemanagement.Adapter.ProductsAdapter;
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;
import com.nextgen.wastemanagement.ModelClass.ProductsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
   Home Fragment
 */
public class HomeFragment extends Fragment {

    private static String TAG ="HomeFragment";

    View view;
    CardView sellCV;
    CardView tipsCV;
    CardView communityCV;
    CardView requestCV;
    CardView binCV;
    CardView segregateCV;

    RecyclerView productsRV;
    ArrayList<ProductsModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;


    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalPreference = new GlobalPreference(getContext());
        ip = globalPreference.getIP();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        productsRV = view.findViewById(R.id.productsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        productsRV.setLayoutManager(layoutManager);

        getProducts();

        sellCV = view.findViewById(R.id.card_sell);
        tipsCV = view.findViewById(R.id.card_tips);
        communityCV = view.findViewById(R.id.card_community);
        requestCV = view.findViewById(R.id.card_request);
        binCV = view.findViewById(R.id.card_publicBin);
        segregateCV = view.findViewById(R.id.card_segregate);

        sellCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),AddProductsActivity.class);
                startActivity(intent);

            }
        });

        tipsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),TipsActivity.class);
                startActivity(intent);
            }
        });

        communityCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),CommunityActivity.class);
                startActivity(intent);
            }
        });

        requestCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),WasteRequestActivity.class);
                startActivity(intent);
            }
        });

        binCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),PublicBinsActivity.class);
                intent.putExtra("type","user");
                startActivity(intent);
            }
        });

        segregateCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),SegregationActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getProducts() {

        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getProducts.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(getContext(), "No Products Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String pname = object.getString("pname");
                            String desc = object.getString("description");
                            String image = object.getString("image");
                            String price = object.getString("price");

                            list.add(new ProductsModelClass(id,pname,desc,image,price));

                        }

                        ProductsAdapter adapter = new ProductsAdapter(list,getActivity());
                        productsRV.setAdapter(adapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

    }
}