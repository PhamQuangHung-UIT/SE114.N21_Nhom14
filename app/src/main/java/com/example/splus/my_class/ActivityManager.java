package com.example.splus.my_class;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {

    private static List<Activity> activityList = new ArrayList<>();

    public static void add(Activity activity)
    {
        activityList.add(activity);
    }
    public static void recreateAll()
    {
        activityList.forEach(a -> {a.recreate();});
    }
}
