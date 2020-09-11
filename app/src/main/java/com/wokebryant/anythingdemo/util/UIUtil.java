package com.wokebryant.anythingdemo.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.math.BigDecimal;

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

    public static String formatNum(String num, Boolean kBool) {
        StringBuffer sb = new StringBuffer();
        //if (!StringUtils.isNumeric(num)) {
        //    return "0";
        //}
        if (kBool == null) {
            kBool = false;
        }

        BigDecimal b0 = new BigDecimal("1000");
        BigDecimal b1 = new BigDecimal("10000");
        BigDecimal b2 = new BigDecimal("100000000");
        BigDecimal b3 = new BigDecimal(num);

        String formatNumStr = "";
        String nuit = "";

        // 以千为单位处理
        if (kBool) {
            if (b3.compareTo(b0) == 0 || b3.compareTo(b0) == 1) {
                return "999+";
            }
            return num;
        }

        // 以万为单位处理
        if (b3.compareTo(b1) == -1) {
            sb.append(b3.toString());
        } else if ((b3.compareTo(b1) == 0 && b3.compareTo(b1) == 1)
            || b3.compareTo(b2) == -1) {
            formatNumStr = b3.divide(b1).toString();
            nuit = "万";
        } else if (b3.compareTo(b2) == 0 || b3.compareTo(b2) == 1) {
            formatNumStr = b3.divide(b2).toString();
            nuit = "亿";
        }
        if (!"".equals(formatNumStr)) {
            int i = formatNumStr.indexOf(".");
            if (i == -1) {
                sb.append(formatNumStr).append(".0").append(nuit);
            } else {
                i = i + 1;
                String v = formatNumStr.substring(i, i + 1);
                if (!v.equals("0")) {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                } else {
                    sb.append(formatNumStr.substring(0, i + 1)).append(nuit);
                }
            }
        }
        if (sb.length() == 0) {
            return "0";
        }
        return sb.toString();
    }

    public static Drawable zoomImage(Context context, int resId, int w, int h){
        Resources res = context.getResources();
        Bitmap oldBmp = BitmapFactory.decodeResource(res, resId);
        Bitmap newBmp = Bitmap.createScaledBitmap(oldBmp,w, h, true);
        Drawable drawable = new BitmapDrawable(res, newBmp);
        return drawable;
    }

    public static void getMemoryInfo(ActivityManager activityManager) {
        if (activityManager != null) {
            //最大分配内存
            int memory = activityManager.getMemoryClass();
            //最大分配内存获取方法2
            float maxMemory = (float) (Runtime.getRuntime().maxMemory() * 1.0/ (1024 * 1024));
            //当前分配的总内存
            float totalMemory = (float) (Runtime.getRuntime().totalMemory() * 1.0/ (1024 * 1024));
            //剩余内存
            float freeMemory = (float) (Runtime.getRuntime().freeMemory() * 1.0/ (1024 * 1024));

            Log.i("内存信息 ", "memory: " + maxMemory + "m" + " realMemory: " + totalMemory + "m" + " freeMemory " + freeMemory + "m");
        }
    }

}
