package com.wokebryant.anythingdemo.application;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wokebryant.anythingdemo.utils.BaseUtil;

public class BaseApplication extends Application {

    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        BaseUtil.init(this);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
        super.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks callback) {
        super.registerComponentCallbacks(componentCallbacks);
    }

    private ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            Log.i(TAG,"onActivityCreated: " + activity.getLocalClassName());
        }

        @Override
        public void onActivityStarted(Activity activity) {
            Log.i(TAG,"onActivityStarted: " + activity.getLocalClassName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            Log.i(TAG,"onActivityResumed: " + activity.getLocalClassName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Log.i(TAG,"onActivityPaused: " + activity.getLocalClassName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            Log.i(TAG,"onActivityStopped: " + activity.getLocalClassName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            Log.i(TAG,"onActivitySaveInstanceState: " + activity.getLocalClassName());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            Log.i(TAG,"onActivityDestroyed: " + activity.getLocalClassName());
        }
    };

    private ComponentCallbacks componentCallbacks = new ComponentCallbacks() {
        @Override
        public void onConfigurationChanged(Configuration newConfig) {

        }

        @Override
        public void onLowMemory() {

        }

    };
}
