package com.nextgen.wastemanagement;

import android.os.Bundle;

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
import com.nextgen.wastemanagement.ModelClass.CampaignsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 CampaignFragment
 */
public class CampaignFragment extends Fragment {

    private static String TAG ="CampaignFragment";

    View view;
    RecyclerView campaignRV;
    ArrayList<CampaignsModelClass> list;

    private GlobalPreference globalPreference;
    private String ip;

    public CampaignFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CampaignFragment newInstance(String param1, String param2) {
        CampaignFragment fragment = new CampaignFragment();
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
        view = inflater.inflate(R.layout.fragment_campaign, container, false);

        campaignRV = view.findViewById(R.id.campaignRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        campaignRV.setLayoutManager(layoutManager);

        getCampaigns();

        return view;
    }

    private void getCampaigns() {
        list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://"+ ip +"/waste_management/api/getCampaigns.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                if (response.equals("failed")){
                    Toast.makeText(getContext(), "No Campaigns Available", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i=0; i< jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String title = object.getString("title");
                            String image = object.getString("image");

                            list.add(new CampaignsModelClass(id,title,image));

                        }

                        CampaignsAdapter adapter = new CampaignsAdapter(list,getActivity());
                        campaignRV.setAdapter(adapter);

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