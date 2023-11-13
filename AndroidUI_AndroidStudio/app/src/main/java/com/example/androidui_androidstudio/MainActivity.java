package com.example.androidui_androidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_temp = findViewById(R.id.button_temp_page);
        btn_temp.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,TempPage.class);
                startActivity(intent);
            }

        });
        Button btn_humid = findViewById(R.id.button_humid_page);
        btn_humid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,HumidPage.class);
                startActivity(intent);
            }
        });
        Button btn_pm10 = findViewById(R.id.button_pm10_page);
        btn_pm10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PM10Page.class);
                startActivity(intent);
            }
        });
        Button btn_pm25 = findViewById(R.id.button_pm25_page);
        btn_pm25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PM25Page.class);
                startActivity(intent);
            }
        });
        Button btn_co2 = findViewById(R.id.button_co2_page);
        btn_co2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,CO2Page.class);
                startActivity(intent);
            }
        });
        Button btn_so2 = findViewById(R.id.button_so2_page);
        btn_so2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SO2Page.class);
                startActivity(intent);
            }
        });
        Button btn_co = findViewById(R.id.button_co_page);
        btn_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,COPage.class);
                startActivity(intent);
            }
        });
        Button btn_o3 = findViewById(R.id.button_o3_page);
        btn_o3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,O3Page.class);
                startActivity(intent);
            }
        });
    }
}