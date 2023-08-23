package com.nextgen.wastemanagement;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 Home Fragment
 */
public class ReportFragment extends Fragment {

    private static String TAG ="HomeFragment";

    View view;

    CardView reportCV;
    CardView viewReportsCV;

    public ReportFragment() {
        // Required empty public constructor
    }


    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_report, container, false);

        reportCV = view.findViewById(R.id.card_report);
        viewReportsCV = view.findViewById(R.id.card_viewReports);

        reportCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ReportActivity.class);
                startActivity(intent);
            }
        });

        viewReportsCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),ViewReportsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}