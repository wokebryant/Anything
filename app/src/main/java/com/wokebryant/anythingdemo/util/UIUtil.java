package com.wokebryant.anythingdemo.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class UIUtil {

    public static int dip2px(Context context, float dpValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        Log.i("scale= ", String.valueOf(scale));
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public static final int getScreenWidth(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        return width;
    }

    //weex尺寸转换px
    public static int vp2px(Context context, float vpValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        int px = (int) (vpValue * getScreenWidth(context) / 750);
        Log.i("px= ", String.valueOf(px) + " " + String.valueOf(getScreenWidth(context)));
        return px;
    }

    //weex尺寸转换dp
    public static int vp2dp(Context context, float vpValue) {
        final float scale =context.getResources().getDisplayMetrics().density;
        return  (int) (vpValue * getScreenWidth(context) * 750 / scale);
    }
}
