package com.wokebryant.anythingdemo.utils.floatingview.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;

import com.wokebryant.anythingdemo.utils.floatingview.transition.BaseFloatingPathTransition;
import com.wokebryant.anythingdemo.utils.floatingview.transition.FloatingPath;
import com.wokebryant.anythingdemo.utils.floatingview.transition.PathPosition;
import com.wokebryant.anythingdemo.utils.floatingview.transition.YumFloating;

public class GiftFloatingTrastion extends BaseFloatingPathTransition {

    private boolean disappear = false;

    @Override
    public FloatingPath getFloatingPath() {
        Path path = new Path();
        path.moveTo(0, 0);
        //path.lineTo(0,-UIUtil.dip2px(65));
        return FloatingPath.create(path, false);
    }

    @Override
    public void applyFloating(final YumFloating yumFloating) {

        ValueAnimator translateAnimator = ObjectAnimator.ofFloat(getStartPathPosition(), getEndPathPosition());
        final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(yumFloating.getTargetView(), "alpha", 1F, 0F);

        //alphaAnimator.setDuration(300);
        alphaAnimator.setDuration(1500);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yumFloating.setAlpha((Float)animation.getAnimatedValue());
            }
        });
        translateAnimator.setDuration(1500);
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                PathPosition floatingPosition = getFloatingPosition(value);
                //if (!disappear && floatingPosition.y < -420) {
                //    disappear = true;
                //    alphaAnimator.start();
                //}
                //if (!disappear && floatingPosition.y < -UIUtil.dip2px(65)) {
                //    disappear = true;
                //}

                yumFloating.setTranslationX(floatingPosition.x);
                yumFloating.setTranslationY(floatingPosition.y);

            }
        });

        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                yumFloating.getTargetView().clearAnimation();
                yumFloating.clear();
            }
        });

        ValueAnimator scaleAnimator = ObjectAnimator.ofFloat(0.0f, 1.0f);
        scaleAnimator.setDuration(500);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                yumFloating.setScaleX((Float) valueAnimator.getAnimatedValue());
                yumFloating.setScaleY((Float) valueAnimator.getAnimatedValue());
            }
        });

        alphaAnimator.start();
        scaleAnimator.start();
        translateAnimator.start();
    }
}
