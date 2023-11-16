package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.androidui_androidstudio.Adapters.HourlyAdapters;
import com.example.androidui_androidstudio.Domains.Hourly;
import com.example.androidui_androidstudio.R;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Dashboard_main extends AppCompatActivity {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;

    //For weather API
    EditText editSearch;
    TextView txtWeatherStatus, txtAQI, txtCity, txtAdvice;
    ImageView imgWeatherStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);
        updateCurrentTime(); // Add this line to update the time
        initRecycleViews();
        weatherRun();
    }

    private void weatherRun() {
        txtWeatherStatus = findViewById(R.id.textViewWeatherStatus);
        txtAQI = findViewById(R.id.textViewAQI);
        txtCity = findViewById(R.id.textViewCity);
        txtAdvice = findViewById(R.id.textViewAdvice);
    }

    private void initRecycleViews() {
        ArrayList<Hourly> items = new ArrayList<>();
        items.add(new Hourly("9 pm", 28, "cloudy"));
        items.add(new Hourly("10 pm", 29, "sunny"));
        items.add(new Hourly("11 pm", 30, "wind"));
        items.add(new Hourly("12 pm", 31, "rainy"));
        items.add(new Hourly("13 pm", 32, "storm"));

        // This is the correct way to initialize your RecyclerView
        recyclerView = findViewById(R.id.view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapterHourly = new HourlyAdapters(items);
        recyclerView.setAdapter(adapterHourly);
    }

    private void updateCurrentTime() {
        TextView textViewTime = findViewById(R.id.textViewTime); // Make sure you have a TextView with the id value_time in your layout
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        calendar.setTimeZone(tz);

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd '|' hh:mm a", Locale.ENGLISH);
        sdf.setTimeZone(tz);

        String currentTime = sdf.format(calendar.getTime());
        textViewTime.setText(currentTime);
    }

}