package com.example.androidui_androidstudio.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidui_androidstudio.HistoryTemp;

public class HistoryTempAdapter extends FragmentStateAdapter{

    public HistoryTempAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new HistoryTemp();
        Bundle args = new Bundle();
        args.putString(HistoryTemp.TITLE, "Temperature "+(position+1));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 9;
    }
}
