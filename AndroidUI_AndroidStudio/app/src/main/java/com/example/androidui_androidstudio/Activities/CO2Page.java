package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;

import com.example.androidui_androidstudio.MQTTHelper;
import com.example.androidui_androidstudio.R;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import com.github.mikephil.charting.charts.LineChart;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;

public class CO2Page extends AppCompatActivity {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    MQTTHelper mqttHelper;

    LineGraphSeries<DataPoint> series;
    //Data from adafruit
    DataPoint[] adaData = new DataPoint[] {new DataPoint(0, 0)};

    double x = 0;
    int count=0;
    public CO2Page() {
        // Required empty public constructor
    }
    public static CO2Page getInstance(String param1, String param2 ){
        CO2Page AppCompatActivity = new CO2Page();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        AppCompatActivity.setArguments(args);
        return AppCompatActivity;
    }

    private void setArguments(Bundle args) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        startMQTT();
        @Override
        public void onResume() {
            super.onResume();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<count; i++) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                series.appendData(new DataPoint(x, adaData[count]),true, count);
                            }
                        });

                        try {
                            Thread.sleep(400);
                        } catch (InterruptedException e) {
                            //
                        }
                    }
                }
            }).start();
        }

        setContentView(R.layout.activity_co2_page);
        ImageButton btn_2 = findViewById(R.id.button_co2_back);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CO2Page.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private Object getArguments() {
        return null;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_temp, container, false);

//        View view = inflater.inflate(R.layout.fragment_temp, container, false);
//        GraphView graph = (GraphView) view.findViewById(R.id.graph);
//        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(adaData);
//        //Log.d("DATA onCreateView", adaData[0].toString());
//        graph.addSeries(series);
//        return view;

        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        GraphView graph = (GraphView) view.findViewById(R.id.graph);
        series = new LineGraphSeries<>();
        //Log.d("DATA onCreateView", adaData[0].toString());
        graph.addSeries(series);
        return view;

//        View view = inflater.inflate(R.layout.fragment_temp, container, false);
//        GraphView graph = (GraphView) view.findViewById(R.id.graph);
//            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new
//                    DataPoint[]{
//                    new DataPoint(0, 5),
//                    new DataPoint(1, 0),
//                    new DataPoint(2, 4),
//                    new DataPoint(3, 1),
//                    new DataPoint(4, 8)
//            });
//        graph.addSeries(series);
//        return view;
    }

}