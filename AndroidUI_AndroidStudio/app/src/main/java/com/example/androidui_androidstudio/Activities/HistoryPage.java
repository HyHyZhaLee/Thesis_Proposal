package com.example.androidui_androidstudio.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.example.androidui_androidstudio.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryPage extends AppCompatActivity {
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        setVariable();

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Retrieve the latest temperature data
        retrieveLatestTemperatureData();
        retrieveLatestHumidityData();
        retrieveLatestPM10Data();
        retrieveLatestPM25Data();
    }

    private void retrieveLatestPM25Data() {
        DatabaseReference pm25Ref = databaseReference.child("airmonitoringV2").child("PM25");
        LineChart lineChart = findViewById(R.id.chartPM25);
        pm25Ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Float> latestPM25Data = new HashMap<>();
                    List<Entry> entries = new ArrayList<>();
                    List<String> times = new ArrayList<>();
                    int index = 0;

                    for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                            String time = timeSnapshot.getKey();
                            String pm25String = timeSnapshot.getValue(String.class);
                            try {
                                Float pm25Value = Float.parseFloat(pm25String);
                                latestPM25Data.put(time, pm25Value);
                                times.add(time); // This list will hold time strings like "00:05", "00:10", etc.
                                entries.add(new Entry(index, pm25Value));
                                index++;
                            } catch (NumberFormatException e) {
                                // Handle the case where the string cannot be parsed as a float
                            }
                        }
                    }

                    // Update the chart on the main UI thread
                    runOnUiThread(() -> updateChart(times, entries, lineChart));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void retrieveLatestPM10Data() {
        DatabaseReference pm10Ref = databaseReference.child("airmonitoringV2").child("PM10");
        LineChart lineChart = findViewById(R.id.chartPM10);
        pm10Ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Float> latestPM10Data = new HashMap<>();
                    List<Entry> entries = new ArrayList<>();
                    List<String> times = new ArrayList<>();
                    int index = 0;

                    for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                            String time = timeSnapshot.getKey();
                            String pm10String = timeSnapshot.getValue(String.class);
                            try {
                                Float pm10Value = Float.parseFloat(pm10String);
                                latestPM10Data.put(time, pm10Value);
                                times.add(time); // This list will hold time strings like "00:05", "00:10", etc.
                                entries.add(new Entry(index, pm10Value));
                                index++;
                            } catch (NumberFormatException e) {
                                // Handle the case where the string cannot be parsed as a float
                            }
                        }
                    }

                    // Update the chart on the main UI thread
                    runOnUiThread(() -> updateChart(times, entries, lineChart));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });
    }

    private void retrieveLatestHumidityData() {
        DatabaseReference humidityRef = databaseReference.child("airmonitoringV2").child("Humidity");
        LineChart lineChart = findViewById(R.id.chartHumidity);
        humidityRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Map<String, Float> latestHumidities = new HashMap<>();
                    List<Entry> entries = new ArrayList<>();
                    List<String> times = new ArrayList<>();
                    int index = 0;

                    for (DataSnapshot dateSnapshot : dataSnapshot.getChildren()) {
                        for (DataSnapshot timeSnapshot : dateSnapshot.getChildren()) {
                            String time = timeSnapshot.getKey();
                            String humidityString = timeSnapshot.getValue(String.class);
                            try {
                                Float humidity = Float.parseFloat(humidityString);
                                latestHumidities.put(time, humidity);
                                times.add(time); // This list will hold time strings like "00:05", "00:10", etc.
                                entries.add(new Entry(index, humidity));
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

    private void updateChart(List<String> times, List<Entry> entries, LineChart lineChart) {
        // Calculate the maximum and minimum values from the entries
        float maxTemp = -Float.MAX_VALUE;
        float minTemp = Float.MAX_VALUE;
        for (Entry entry : entries) {
            maxTemp = Math.max(maxTemp, entry.getY());
            minTemp = Math.min(minTemp, entry.getY());
        }

        // Set the max and min for the Y-axis
        float yAxisMax = maxTemp + 1;
        float yAxisMin = minTemp - 1;

        // Prepare the entries for hourly data points
        List<Entry> hourlyEntries = new ArrayList<>();
        for (int i = 0; i < entries.size(); i++) {
            if(i == entries.size()-1 || i == 0) {
                hourlyEntries.add(entries.get(i));
            }
            else if(entries.get(i).getY() == maxTemp || entries.get(i).getY() == minTemp) {
                // Add entries that have maximum or minimum temperature
                hourlyEntries.add(entries.get(i));
            }
            else if(i%12 == 0){
                hourlyEntries.add(entries.get(i));
            }
            //Mark extreme value here
//            else {
//                float left = entries.get(i-1).getY();
//                float mid = entries.get(i).getY();
//                float right = entries.get(i+1).getY();
//                if(left<mid && mid > right || left>mid && mid < right) hourlyEntries.add(entries.get(i));
//            }
        }


        // Create the data set for all entries but disable the drawing of circles
        LineDataSet allDataSet = new LineDataSet(entries, "Temperature");
        allDataSet.setDrawCircles(false);
        allDataSet.setDrawValues(false); // Optionally disable the drawing of values
        allDataSet.setColor(Color.parseColor("#AA00FF"));

        // Customize the data set for hourly entries to draw circles
        LineDataSet hourlyDataSet = new LineDataSet(hourlyEntries, "Hourly Temperature");

        // Apply styling
        hourlyDataSet.setCircleColor(Color.parseColor("#AA00FF"));
        hourlyDataSet.setCircleRadius(6f);
        hourlyDataSet.setCircleHoleRadius(3f);
        hourlyDataSet.setDrawCircleHole(true);
        hourlyDataSet.setDrawCircles(true);
        hourlyDataSet.setDrawValues(true);
        hourlyDataSet.setLineWidth(0.2f);
        // Combine both data sets
        LineData data = new LineData();
        data.addDataSet(allDataSet);
        data.addDataSet(hourlyDataSet);

        // Set data to the chart
        lineChart.setData(data);

        // Customize the X Axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(times));
        xAxis.setGranularity(1f); // Only show labels for each entry
        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setYOffset(10f); // Adjust this value to move the X-axis labels up or down

        // Customize the Y Axis (Left)
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setAxisMinimum(yAxisMin);
        leftAxis.setAxisMaximum(yAxisMax);
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format("%.1f", value);
            }
        });
        // Customize the Y Axis (Right)
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);

        // Adjust the Legend position
        lineChart.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        lineChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        lineChart.getLegend().setDrawInside(false);
        lineChart.getLegend().setYOffset(5f); // Adjust this value to move the legend up or down

        // Adjust extra bottom offset to make space for X-axis labels and legend
        lineChart.setExtraBottomOffset(10f);

        // Other customizations like axis colors, line width, etc., remain the same...
        lineChart.getDescription().setEnabled(false);        // Remove description label

        // Refresh the chart
        lineChart.invalidate();
    }



    private void setVariable() {
        ConstraintLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(v -> startActivity(new Intent(HistoryPage.this, Dashboard_main.class)));
    }
}
