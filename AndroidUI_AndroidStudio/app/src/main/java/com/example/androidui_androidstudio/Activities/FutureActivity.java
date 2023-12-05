package com.example.androidui_androidstudio.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.androidui_androidstudio.Adapters.FutureAdapter;
import com.example.androidui_androidstudio.Domains.FutureDomain;
import com.example.androidui_androidstudio.R;

import java.util.ArrayList;

public class FutureActivity extends AppCompatActivity {
private RecyclerView.Adapter adapterTomorrow;
private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future);
        setVariable();
        initRecycleView();
    }

    private void initRecycleView() {
        ArrayList<FutureDomain> items = new ArrayList<>();
        items.add(new FutureDomain("Sat","storm","Storm",25,15));
        items.add(new FutureDomain("Sun","cloudy","Cloudy",20,14));
        items.add(new FutureDomain("Mon","windy","Windy",21,13));
        items.add(new FutureDomain("Tue","cloudy_sunny","Cloudy sunny",22,10));
        items.add(new FutureDomain("Wen","sunny","Sunny",23,11));
        items.add(new FutureDomain("Thu","rainy","Rainy",24,12));

        recyclerView=findViewById(R.id.viewTomorrow);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterTomorrow = new FutureAdapter(items);
        recyclerView.setAdapter(adapterTomorrow);
    }

    private void setVariable() {
        ConstraintLayout backBtn = findViewById(R.id.back_btn);
        backBtn.setOnClickListener(v -> startActivity(new Intent(FutureActivity.this,Dashboard_main.class)));
    }
}