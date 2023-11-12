package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button tempPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tempPage = (Button) findViewById(R.id.temp_page);
        tempPage.setOnClickListener((View.OnClickListener) this);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.temp_page:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
    }


}