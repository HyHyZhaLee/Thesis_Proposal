package com.example.androidui_androidstudio.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidui_androidstudio.Adapters.HourlyAdapters;
import com.example.androidui_androidstudio.Domains.Hourly;
import com.example.androidui_androidstudio.PermissionHelper;
import com.example.androidui_androidstudio.R;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Dashboard_main extends AppCompatActivity {

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnable;

    //For weather API
    TextView txtWeatherStatus, txtAQI, txtCity, txtAdvice, txtTemperature, txtHumidity, txtAirPressure;
    ImageView imgWeatherStatus;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("AppSharedPrefs", MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_main);

        PermissionHelper.LoopcheckNetworkConnection(Dashboard_main.this);
        PermissionHelper.checkGPSPermission(Dashboard_main.this);
        runnable = new Runnable() {
            @Override
            public void run() {
                updateCurrentTime();
                weatherRun();
                handler.postDelayed(this, 10000); // updates every minute
            }
        };
        // Start the initial runnable task by posting through the handler
        handler.post(runnable);
        initRecycleViews();
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); // stop the handler when activity not visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionHelper.LoopcheckNetworkConnection(Dashboard_main.this);
        handler.post(runnable); // restart the handler when activity is back
    }
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }




    private void weatherRun() {
        txtWeatherStatus = findViewById(R.id.textViewWeatherStatus);
        txtAQI = findViewById(R.id.textViewAQI);
        txtCity = findViewById(R.id.textViewCity);
        txtAdvice = findViewById(R.id.textViewAdvice);
        imgWeatherStatus = findViewById(R.id.imageViewWeatherStatus);

        txtAirPressure = findViewById(R.id.textViewAirPressure);
        txtTemperature = findViewById(R.id.textViewTemperature);
        txtHumidity = findViewById(R.id.textViewHumidity);
        //Asking for permission and get the lat + long value
        getCurrentLocation();

        float latitude = sharedPreferences.getFloat("Latitude", 0.0f); // 0.0f is the default value
        float longitude = sharedPreferences.getFloat("Longitude", 0.0f);
        if(latitude == 0.0f || longitude == 0.0f) return;
        String APIKEY = "a1d63f18c12b440415"+ "d0791d25cea7e4";
        String url ="https://api.openweathermap.org/data/2.5/weather?lat="
                + latitude
                + "&lon=" + longitude
                + "&appid=" + APIKEY
                + "&units=metric";
        RequestQueue requestQueue = Volley.newRequestQueue(Dashboard_main.this);
        @SuppressLint("SetTextI18n") StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("Testing_log","Result for current weather:" + response);
                    try {
                        //JSONObject for result
                        JSONObject jsonObject_respone = new JSONObject(response);
                        //Set city name
                        txtCity.setText(jsonObject_respone.getString("name"));
                        //Set weather
                        JSONArray jsonArray_weather = jsonObject_respone.getJSONArray("weather");
                        JSONObject jsonObject_weather = jsonArray_weather.getJSONObject(0);
                        txtWeatherStatus.setText(capitalizeFirstLetterOfEachWord(
                                jsonObject_weather.getString("description")
                            )
                        );
                        //Update icon
                        String icon = jsonObject_weather.getString("icon");
                        //Set air pressure
                        JSONObject jsonObject_main = jsonObject_respone.getJSONObject("main");
                        txtAirPressure.setText(jsonObject_main.getString("pressure") + " pHa");
                        txtTemperature.setText(jsonObject_main.getString("temp") + " °C");
                        txtHumidity.setText(jsonObject_main.getString("humidity") + " %");



                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                , error -> {
            Log.d("Testing_log","FAILED TO REQUEST GET ON URL!!! ERROR:" + error);
        });
        requestQueue.add(stringRequest);
    }

    @SuppressLint({"MissingPermission", "VisibleForTests"})
    private void getCurrentLocation(){
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationServices.getFusedLocationProviderClient(Dashboard_main.this)
                .requestLocationUpdates(locationRequest, new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(Dashboard_main.this)
                                .removeLocationUpdates(this);
                        if(locationResult != null && locationResult.getLocations().size() > 0){
                            int lastLocationIndex = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(lastLocationIndex).getLatitude();
                            double longitude = locationResult.getLocations().get(lastLocationIndex).getLongitude();
                            // Saving the location data
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putFloat("Latitude", (float) latitude);
                            editor.putFloat("Longitude", (float) longitude);
                            editor.apply();
                        }
                    }
                }, Looper.getMainLooper());
    }

    private void initRecycleViews() {
        ArrayList<Hourly> items = new ArrayList<>();
        items.add(new Hourly("9 pm", 28, "cloudy"));
        items.add(new Hourly("10 pm", 29, "sunny"));
        items.add(new Hourly("11 pm", 30, "wind"));
        items.add(new Hourly("12 pm", 31, "rainy"));
        items.add(new Hourly("13 pm", 32, "storm"));

        // This is the correct way to initialize your RecyclerView
        RecyclerView recyclerView = findViewById(R.id.view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        RecyclerView.Adapter<HourlyAdapters.viewholder> adapterHourly = new HourlyAdapters(items);
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

    public String capitalizeFirstLetterOfEachWord(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder capitalizedString = new StringBuilder();
        String[] words = input.split(" ");
        for (String word : words) {
            String firstLetter = word.substring(0, 1).toUpperCase();
            String remainingLetters = word.substring(1).toLowerCase();
            capitalizedString.append(firstLetter).append(remainingLetters).append(" ");
        }

        return capitalizedString.toString().trim(); // trim to remove the last unnecessary space
    }
}