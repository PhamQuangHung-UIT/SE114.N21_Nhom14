package com.example.splus.my_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.splus.NotificationActivity;
import com.example.splus.R;
import com.example.splus.my_adapter.HomeworkViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeworkFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework, container, false);

        ImageButton imageButtonNotif = view.findViewById(R.id.buttonNotifyHwFragment);
        imageButtonNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickGoToNotification();
            }
        });

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutHwFragment);
        ViewPager viewPager = view.findViewById(R.id.viewPagerHwFragment);
        HomeworkViewPagerAdapter homeworkViewPagerAdapter = new HomeworkViewPagerAdapter(getParentFragmentManager(), this.getActivity());
        viewPager.setAdapter(homeworkViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    private void onClickGoToNotification() {
        Intent intent = new Intent(this.getActivity(), NotificationActivity.class);
        startActivity(intent);
    }
}
