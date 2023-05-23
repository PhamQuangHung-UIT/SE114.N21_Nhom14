package com.example.splus.my_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.splus.my_fragment.ClassFragment;
import com.example.splus.my_fragment.HomeFragment;
import com.example.splus.my_fragment.HomeworkFragment;
import com.example.splus.my_fragment.SettingFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    public MainViewPagerAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle) {
        super(fm, lifecycle);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ClassFragment();
            case 2:
                return new HomeworkFragment();
            case 3:
                return new SettingFragment();
            default:
                return new HomeFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
