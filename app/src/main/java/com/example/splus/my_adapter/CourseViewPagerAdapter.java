package com.example.splus.my_adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.splus.CommentsFragment;
import com.example.splus.LessonListFragment;
import com.example.splus.R;
import com.google.android.material.tabs.TabLayout;

public class CourseViewPagerAdapter extends FragmentStateAdapter implements TabLayout.OnTabSelectedListener {

    private Context context;

    public CourseViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.context = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new LessonListFragment() : new CommentsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab != null && tab.getIcon() != null)
            tab.getIcon().setTint(context.getColor(R.color.white));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab != null && tab.getIcon() != null)
            tab.getIcon().setTint(context.getColor(R.color.unselected));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //ignored
    }
}
