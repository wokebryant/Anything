package com.wokebryant.anythingdemo.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

/**
 * @author wb-lj589732
 *  活体认证动效
 */
public class RealPersonAnimationManager {

    private ValueAnimator outAnimator;
    private ValueAnimator innerAnimator;


    private static class InstanceHolder{
        static final RealPersonAnimationManager animationManager = new RealPersonAnimationManager();
    }

    public static RealPersonAnimationManager getInstance() {
        return InstanceHolder.animationManager;
    }

    /**
     * 外层光圈动效
     */
    public void showOutApertureAnim(final View view) {
        outAnimator = ObjectAnimator.ofFloat(1.0f, 0.2f, 1.0f);
        outAnimator.setDuration(2000);
        outAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((Float)animation.getAnimatedValue());
            }
        });
        outAnimator.start();
    }

    /**
     * 头像区域光晕动效
     */
    public void showInsideApertureAnim(final View view) {
        Log.i("showInsideAnim: ", "start");
        if (view.getVisibility() == View.VISIBLE) {
            return;
        }
        view.setVisibility(View.VISIBLE);
        ValueAnimator innerAnimator = ObjectAnimator.ofFloat(1.0f, 0f);
        innerAnimator.setDuration(5000);
        innerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((Float)animation.getAnimatedValue());
            }
        });
        innerAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                Log.i("showInsideAnim: ", "finish");
            }
        });
        innerAnimator.start();
    }

    public void stopOutApertureAnim() {
        if (outAnimator != null) {
            outAnimator.cancel();
        }
    }

    public void stopInnerApertureAnim() {
        if (innerAnimator != null) {
            innerAnimator.cancel();
        }
    }


}
