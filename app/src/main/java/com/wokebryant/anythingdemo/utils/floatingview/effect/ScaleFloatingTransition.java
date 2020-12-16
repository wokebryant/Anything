/*
 * Copyright (C) 2015 UFreedom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.wokebryant.anythingdemo.utils.floatingview.effect;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;

import com.wokebryant.anythingdemo.utils.floatingview.transition.FloatingTransition;
import com.wokebryant.anythingdemo.utils.floatingview.transition.YumFloating;

public class ScaleFloatingTransition implements FloatingTransition {

    private long mDuration;
    private double mBounciness;
    private double mSpeed;

    public ScaleFloatingTransition() {
        mDuration = 1000;
        mBounciness = 10;
        mSpeed = 15;
    }

    public ScaleFloatingTransition(long duration) {
        this.mDuration = duration;
        mBounciness = 10;
        mSpeed = 15;
    }

    public ScaleFloatingTransition(long duration, double bounciness, double speed) {
        this.mDuration = duration;
        this.mBounciness = bounciness;
        this.mSpeed = speed;
    }

    @Override
    public void applyFloating(final YumFloating yumFloating) {
        
        ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(1.0f, 0.0f);
        alphaAnimator.setDuration(mDuration);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                yumFloating.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });
        alphaAnimator.start();
    }
    
}
