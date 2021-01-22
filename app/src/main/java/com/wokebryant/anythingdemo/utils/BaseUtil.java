package com.wokebryant.anythingdemo.utils;

import android.app.Application;

public class BaseUtil {

    private static Application mApplication;

    public static void init(Application application) {
        mApplication = application;
    }

    public static Application getApplicationContext() {
        return mApplication;
    }
}
