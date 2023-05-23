package com.example.splus.my_adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.splus.R;
import com.example.splus.my_fragment.OtherNotifFragment;
import com.example.splus.my_fragment.StudyNotifFragment;

public class NotifViewPagerAdapter extends FragmentStatePagerAdapter {


    private final Context context;
    public NotifViewPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new OtherNotifFragment();
        }
        return new StudyNotifFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = context.getString(R.string.study_notif_fragment_title);
                break;
            case 1:
                title = context.getString(R.string.other_notif_fragment_title);
                break;
        }
        return title;
    }
}
