package com.example.splus.my_class;

import android.app.Application;
import android.content.Context;

public class AppManager extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}
