package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.example.androidui_androidstudio.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HistoryPage extends AppCompatActivity {
    private LineChart lineChart;
    private List<String> xValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);
        setVariable();
        lineChart = findViewById(R.id.chartTemperature);
        Description description = new Description();
        description.setPosition(150f,15f);
        description.setText("Temperature History");
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList("value", "value","value","value","value","value");

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(4);
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(100f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> entries1 = new ArrayList<>();
        entries1.add(new Entry(0,10f));
        entries1.add(new Entry(1,10f));
        entries1.add(new Entry(2,15f));
        entries1.add(new Entry(3,45f));

        List<Entry> entries2 = new ArrayList<>();
        entries2.add(new Entry(0,5f));
        entries2.add(new Entry(1,15f));
        entries2.add(new Entry(2,25f));
        entries2.add(new Entry(3,30f));

        LineDataSet dataSet1 = new LineDataSet(entries1, "Maths");
        dataSet1.setColor(Color.BLUE);

        LineDataSet dataSet2 = new LineDataSet(entries2, "Maths");
        dataSet2.setColor(Color.RED);

        LineData lineData = new LineData(dataSet1, dataSet2);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void setVariable() {
        ConstraintLayout backbtn = findViewById(R.id.back_btn);
        backbtn.setOnClickListener(v -> startActivity(new Intent(HistoryPage.this,Dashboard_main.class)));
    }
}