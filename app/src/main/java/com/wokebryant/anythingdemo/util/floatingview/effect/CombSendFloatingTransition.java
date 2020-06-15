package com.wokebryant.anythingdemo.util.floatingview.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.util.UIUtil;
import com.wokebryant.anythingdemo.util.floatingview.transition.BaseFloatingPathTransition;
import com.wokebryant.anythingdemo.util.floatingview.transition.FloatingPath;
import com.wokebryant.anythingdemo.util.floatingview.transition.FloatingTransition;
import com.wokebryant.anythingdemo.util.floatingview.transition.PathPosition;
import com.wokebryant.anythingdemo.util.floatingview.transition.YumFloating;

public class CombSendFloatingTransition extends BaseFloatingPathTransition {

    private static final long DURATION = 1000;
    private static final long END_DURATION = 200;

    private Context mContext;

    public CombSendFloatingTransition(Context context) {
        mContext = context;
    }

    @Override
    public FloatingPath getFloatingPath() {
        Path path = new Path();
        path.moveTo(0, 0);
        path.quadTo(-UIUtil.dip2px(mContext, 5), -UIUtil.dip2px(mContext, 200),
            -UIUtil.dip2px(mContext, 120), -UIUtil.dip2px(mContext,400));

        return FloatingPath.create(path, false);
    }

    @Override
    public void applyFloating(final YumFloating yumFloating) {
        ValueAnimator scaleAnimator = ObjectAnimator.ofFloat(0.8f, 1.2f);
        scaleAnimator.setDuration(DURATION);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yumFloating.setScaleX((Float)animation.getAnimatedValue());
                yumFloating.setScaleY((Float)animation.getAnimatedValue());
            }
        });

        ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(1.0f, 0.1f);
        alphaAnimator.setDuration(DURATION);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yumFloating.setAlpha((Float)animation.getAnimatedValue());
            }
        });

        ValueAnimator translateAnimator = ObjectAnimator.ofFloat(getStartPathPosition(), getEndPathPosition());
        translateAnimator.setDuration(DURATION);
        alphaAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float)animation.getAnimatedValue();
                PathPosition floatingPosition = getFloatingPosition(value);
                yumFloating.setTranslationX(floatingPosition.x);
                yumFloating.setTranslationY(floatingPosition.y);
            }
        });

        AnimatorSet animatorSet= new AnimatorSet();
        animatorSet.playTogether(scaleAnimator, alphaAnimator, translateAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //doWhenAnimEnd(yumFloating);
                yumFloating.getTargetView().clearAnimation();
                yumFloating.clear();
            }
        });

        animatorSet.start();
    }

    private void doWhenAnimEnd(final YumFloating yumFloating) {
        ValueAnimator endScaleAnimator = ObjectAnimator.ofFloat(1.2f, 2.2f);
        endScaleAnimator.setDuration(END_DURATION);
        endScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yumFloating.setScaleX((Float)animation.getAnimatedValue());
                yumFloating.setScaleY((Float)animation.getAnimatedValue());
            }
        });

        ValueAnimator endAlphaAnimator = ObjectAnimator.ofFloat(0.5f, 0f);
        endAlphaAnimator.setDuration(END_DURATION);
        endAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yumFloating.setAlpha((Float)animation.getAnimatedValue());
            }
        });

        AnimatorSet endAnimatorSet= new AnimatorSet();
        endAnimatorSet.playTogether(endScaleAnimator, endAlphaAnimator);

        endAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                yumFloating.getTargetView().clearAnimation();
                yumFloating.clear();

            }
        });

        endAnimatorSet.start();
    }

}
