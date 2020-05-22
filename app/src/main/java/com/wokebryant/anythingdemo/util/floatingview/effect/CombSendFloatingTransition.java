package com.wokebryant.anythingdemo.util.floatingview.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import com.wokebryant.anythingdemo.util.floatingview.transition.FloatingTransition;
import com.wokebryant.anythingdemo.util.floatingview.transition.YumFloating;

public class CombSendFloatingTransition implements FloatingTransition {

    @Override
    public void applyFloating(final YumFloating yumFloating) {
        ValueAnimator scaleAnimator = ObjectAnimator.ofFloat(0.7f, 1.0f);
        scaleAnimator.setDuration(300);
        scaleAnimator.setRepeatMode(ValueAnimator.REVERSE);
        scaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                yumFloating.setScaleX((Float)animation.getAnimatedValue());
                yumFloating.setScaleY((Float)animation.getAnimatedValue());
            }
        });
        scaleAnimator.start();
    }
}
