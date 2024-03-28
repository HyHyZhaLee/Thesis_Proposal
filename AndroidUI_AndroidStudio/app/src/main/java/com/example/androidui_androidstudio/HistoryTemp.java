package com.example.androidui_androidstudio;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidui_androidstudio.Activities.Dashboard_main;
import com.example.androidui_androidstudio.Activities.HistoryPage;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryTemp extends Fragment {
    public static final String TITLE ="title";
    public HistoryTemp() {
        // Required empty public constructor
    }
    private DatabaseReference databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_temp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView)view.findViewById(R.id.textView).setTextDirection(getArguments().getString(TITLE)));
        setVariable();

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Retrieve the latest temperature data
        retrieveLatestTemperatureData();
    }
    private void retrieveLatestTemperatureData() {
        DatabaseReference tempRef = databaseReference.child("airmonitoringV2").child("Temperature");
        LineChart lineChart = findViewById(R.id.chartTemperature);
        tempRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Float> latestTemperatures = new HashMap<>();
                    List<Entry> entries = new ArrayList<>();
                    List<String> times = new ArrayList<>();
                    int index = 0;

                    for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                            String time = timeSnapshot.getKey();
                            String temperatureString = timeSnapshot.getValue(String.class);
                            try {
                                Float temperature = Float.parseFloat(temperatureString);
                                latestTemperatures.put(time, temperature);
                                times.add(time); // This list will hold time strings like "00:05", "00:10", etc.
                                entries.add(new Entry(index, temperature));
                                index++;
                            } catch (NumberFormatException e) {
                                // Handle the case where the string cannot be parsed as a float
                            }
                        }
                    }

                    // Update the chart on the main UI thread
                    runOnUiThread(() -> updateChart(times, entries,lineChart));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }
    private void setVariable() {
        ConstraintLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(v -> startActivity(new Intent(HistoryPage.this, Dashboard_main.class)));
    }
}