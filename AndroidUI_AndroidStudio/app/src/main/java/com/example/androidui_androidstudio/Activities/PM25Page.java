package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.androidui_androidstudio.R;

public class PM25Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pm25_page);
        ImageButton btn_2 = findViewById(R.id.button_pm25_back);
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PM25Page.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}