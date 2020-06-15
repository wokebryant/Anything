package com.wokebryant.anythingdemo.util;

import android.content.Context;
import android.os.Vibrator;

public class VibrateUtil {
    private static Vibrator vibrator;
    /**
     * @ClassName:VibrateHelp - 简单的震动
     * @author:CaoJiaHao
     * @Param:context 调用震动类的 context
     * @param:millisecond 震动的时间
     */
    @SuppressWarnings("static-access")
    public static void vSimple(Context context, int millisecode) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(millisecode);
    }
    /**
     * @param : pattern 震动的形式
     * @param : repeate 震动循环的次数
     * @ClassName:VibrateHelp - 复杂的震动
     * @author:CaoJiaHao
     * @Param: context 调用复杂震动的context
     **/
    @SuppressWarnings("static-access")
    public static void vComplicated(Context context, long[] pattern, int repeate) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, repeate);
    }
    /**
     *@ClassName:VibrateHelp - 停止震动
     *@author:CaoJiaHao
     **/
    public static void stop() {
        if (vibrator != null) {
            vibrator.cancel();
        }
    }
}
