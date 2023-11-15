package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.androidui_androidstudio.R;

public class TempPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_page2);
        ImageButton btn_1 = findViewById(R.id.button_temp_back);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TempPage.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}