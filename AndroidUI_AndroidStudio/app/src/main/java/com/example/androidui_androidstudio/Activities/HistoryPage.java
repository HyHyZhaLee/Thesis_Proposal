package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.example.androidui_androidstudio.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class HistoryPage extends AppCompatActivity {
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        setVariable();
        lineChart = findViewById(R.id.chartTemperature);

        // Remove description label
        lineChart.getDescription().setEnabled(false);

        // Customize the X Axis for time
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false); // No grid lines
        xAxis.setDrawAxisLine(false); // No axis line
        xAxis.setTextColor(Color.GRAY); // Set the color of labels
        // Set 12 time values from 9 PM to 8 AM
        final String[] times = new String[]{"9 PM", "10 PM", "11 PM", "12 AM", "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(times));
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(times.length);
        xAxis.setYOffset(10f); // Adjust this value to move the X-axis labels up or down


        // Customize the Y Axis for temperature
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(true); // No grid lines
        leftAxis.setDrawAxisLine(false); // No axis line
        leftAxis.setTextColor(Color.GRAY); // Set the color of labels
        leftAxis.setAxisMinimum(0f); // Minimum value for Y
        leftAxis.setAxisMaximum(100f); // Maximum value for Y
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Append °C to Y-axis labels
                return value + " °C";
            }
        });

        // Customize the Y Axis (Right)
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false); // No right axis

        // Set up the data points
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            // Add dummy data points
            entries.add(new Entry(i, (float) (Math.random() * 100))); // Random values for demonstration
        }

        // Customize the data set
        LineDataSet dataSet = new LineDataSet(entries, "Temperature");
        dataSet.setColor(Color.parseColor("#AA00FF")); // Line color
        dataSet.setLineWidth(2f); // Line width
        dataSet.setCircleColor(Color.parseColor("#AA00FF")); // Circle color
        dataSet.setCircleRadius(6f); // Circle radius
        dataSet.setCircleHoleRadius(3f); // Circle hole radius
        dataSet.setDrawCircleHole(true); // Draw circle holes
        dataSet.setDrawCircles(true); // Draw circles
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Set the line to be drawn cubic or linear
        dataSet.setCubicIntensity(0.2f); // Customize the cubic intensity

        // Adjust the Legend position
        lineChart.getLegend().setYOffset(5f); // Adjust this value to move the legend up or down

        // Adjust extra bottom offset to make space for X-axis labels and legend
        lineChart.setExtraBottomOffset(10f);

        // Set up the LineData object
        LineData data = new LineData(dataSet);
        lineChart.setData(data);
        lineChart.setVisibleXRangeMaximum(5); // Limit the number of visible entries
        lineChart.invalidate(); // Refresh the chart
    }

    private void setVariable() {
        ConstraintLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(v -> startActivity(new Intent(HistoryPage.this, Dashboard_main.class)));
    }
}
