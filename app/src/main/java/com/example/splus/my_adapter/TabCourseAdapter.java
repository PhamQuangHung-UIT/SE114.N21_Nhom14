package com.example.splus.my_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.splus.course_tabs.AssignmentFragment;
import com.example.splus.course_tabs.CourseInfoFragment;
import com.example.splus.course_tabs.LessonFragment;

public class TabCourseAdapter extends FragmentPagerAdapter {
  private static final int NUM_TABS = 3;

  public TabCourseAdapter(@NonNull FragmentManager fm) {
    super(fm);
  }

  @NonNull
  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new LessonFragment();
      case 1:
        return new AssignmentFragment();
      case 2:
        return new CourseInfoFragment();
      default:
        return null;
    }
  }

  @Override
  public int getCount() {
    return NUM_TABS;
  }
}

