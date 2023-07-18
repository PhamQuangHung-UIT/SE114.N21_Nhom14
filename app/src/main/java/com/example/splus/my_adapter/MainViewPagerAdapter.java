package com.example.splus.my_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.splus.my_fragment.AccountFragment;
import com.example.splus.my_fragment.AssignFragment;
import com.example.splus.my_fragment.AssignmentFragment;
import com.example.splus.my_fragment.CoursesFragment;
import com.example.splus.my_fragment.HomeFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {
    private final int role;
    public MainViewPagerAdapter(@NonNull FragmentManager fm, Lifecycle lifecycle, int role) {
        super(fm, lifecycle);
        this.role = role;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new CoursesFragment();
            case 2:
                if (role==0) {
                    return new AssignmentFragment();
                } else {
                    return new AssignFragment();
                }
            case 3:
                return new AccountFragment();
            default:
                return new HomeFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
}
