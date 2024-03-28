package com.example.androidui_androidstudio;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.androidui_androidstudio.Adapters.HistoryTempAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class History extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_page);

        View tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.pager);

        HistoryTempAdapter historyTempAdapter = new HistoryTempAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(historyTempAdapter);

        new TabLayoutMediator((TabLayout) tabLayout, viewPager2, (tab, position) ->  {
            tab.setText("Temperature"+(position+1));
        }).attach();
    }
}
