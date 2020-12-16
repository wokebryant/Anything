package com.wokebryant.anythingdemo.widget.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.badoo.mobile.util.WeakHandler;
import com.wokebryant.anythingdemo.R;

public class CombFloatingView extends LinearLayout{

    private static final String TAG = "CombFloatingView";
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 3;

    private static final int MIN_RICH_NUM = 1;      //最少可送礼数量
    private static final int GENERAL_RICH_NUM = 10; //哇
    private static final int MEDIUM_RICH_NUM = 30;  //大气
    private static final int SUPER_RICH_NUM = 60;   //豪气

    private static final int DISMISS_FLOATING = 0x11;
    private boolean isChangeState;
    private boolean isChangeStateEnd = true;

    private ValueAnimator mTextScaleAnimator;
    private ValueAnimator mNumScaleAnimator;
    private ValueAnimator mDismissScaleAnimator;
    private AnimatorSet mAnimatorSet;

    private LinearLayout mCombNumView;
    private ImageView mFirstNumView;
    private ImageView mSecondNumView;
    private ImageView mThirdNumView;
    private ImageView mCombEffectView;

    public WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case DISMISS_FLOATING:
                    mDismissScaleAnimator.start();
                    break;
                default:
            }
            return false;
        }
    });

    public CombFloatingView(Context context) {
        this(context, null);
    }

    public CombFloatingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_view_comb_floating, this, true);
        mCombNumView = findViewById(R.id.comb_num_view);
        mFirstNumView = findViewById(R.id.comb_num_1);
        mSecondNumView = findViewById(R.id.comb_num_2);
        mThirdNumView = findViewById(R.id.comb_num_3);
        mCombEffectView = findViewById(R.id.comb_effct_view);

        mAnimatorSet = new AnimatorSet();
        setNumScaleType();
        setTextScaleType();
        setDismissStyleType();
    }

    private void setTextScaleType() {
        mTextScaleAnimator = new ObjectAnimator();
        mTextScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCombEffectView.setScaleX((Float)animation.getAnimatedValue());
                mCombEffectView.setScaleY((Float)animation.getAnimatedValue());
            }
        });

        mTextScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isChangeState) {
                    setStateChangeType();
                }
                weakHandler.sendEmptyMessageDelayed(DISMISS_FLOATING, 4500);
            }
        });
    }

    private void setNumScaleType() {
        mNumScaleAnimator = ObjectAnimator.ofFloat(1.2f, 0.9f);
        mNumScaleAnimator.setDuration(150);
        mNumScaleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mNumScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCombNumView.setScaleX((Float)animation.getAnimatedValue());
                mCombNumView.setScaleY((Float)animation.getAnimatedValue());
            }
        });
    }

    private void setStateChangeType() {
        ValueAnimator changeAnimator = ObjectAnimator.ofFloat(2.0f, 0.8f, 1f);
        changeAnimator.setDuration(300);
        changeAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        changeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCombEffectView.setScaleX((Float)animation.getAnimatedValue());
                mCombEffectView.setScaleY((Float)animation.getAnimatedValue());
            }
        });
        changeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isChangeStateEnd = true;
            }
        });
        changeAnimator.start();
    }

    private void setDismissStyleType() {
        mDismissScaleAnimator = ObjectAnimator.ofFloat(1.0f, 0f);
        mDismissScaleAnimator.setDuration(100);
        mDismissScaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCombEffectView.setScaleX((Float)animation.getAnimatedValue());
                mCombEffectView.setScaleY((Float)animation.getAnimatedValue());
                mCombNumView.setScaleX((Float)animation.getAnimatedValue());
                mCombNumView.setScaleY((Float)animation.getAnimatedValue());
            }
        });
        mDismissScaleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setVisibility(GONE);
            }
        });
    }

    public void setDataForText(int combNum) {
        if (combNum >= MIN_RICH_NUM && combNum <= GENERAL_RICH_NUM) {
            mCombEffectView.setImageResource(R.drawable.lf_combsend_rich0);
        } else if (combNum >GENERAL_RICH_NUM && combNum <= MEDIUM_RICH_NUM) {
            mCombEffectView.setImageResource(R.drawable.lf_combsend_rich1);
        } else if (combNum > MEDIUM_RICH_NUM && combNum <= SUPER_RICH_NUM) {
            mCombEffectView.setImageResource(R.drawable.lf_combsend_rich2);
        } else {
            mCombEffectView.setImageResource(R.drawable.lf_combsend_rich3);
        }
    }

    public void setDataForNum(int combNum) {
        String num = String.valueOf(combNum);
        if (num.length() >= MIN_LENGTH  && num.length() < MAX_LENGTH) {
            mFirstNumView.setVisibility(VISIBLE);
            mThirdNumView.setVisibility(GONE);

            String one = String.valueOf(num.charAt(0));
            if ("1".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_1);
            } else if ("2".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_2);
            } else if ("3".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_3);
            } else if ("4".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_4);
            } else if ("5".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_5);
            } else if ("6".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_6);
            } else if ("7".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_7);
            } else if ("8".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_8);
            } else if ("9".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_9);
            }

            if (num.length() == MIN_LENGTH) {
                mSecondNumView.setVisibility(GONE);
            } else {
                mSecondNumView.setVisibility(VISIBLE);
                String two = String.valueOf(num.charAt(1));
                if ("1".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_1);
                } else if ("2".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_2);
                } else if ("3".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_3);
                } else if ("4".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_4);
                } else if ("5".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_5);
                } else if ("6".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_6);
                } else if ("7".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_7);
                } else if ("8".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_8);
                } else if ("9".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_9);
                } else {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_0);
                }
            }

        } else {
            mFirstNumView.setVisibility(VISIBLE);
            mSecondNumView.setVisibility(VISIBLE);
            mThirdNumView.setVisibility(VISIBLE);

            mFirstNumView.setImageResource(R.drawable.lf_combsend_9);
            mSecondNumView.setImageResource(R.drawable.lf_combsend_9);
            mThirdNumView.setImageResource(R.drawable.lf_combsend_add);
        }
    }

    public void startFloatingAnim(int combNum) {
        Log.i("changeState", " state= " + isChangeState + " end= " + isChangeStateEnd);
        if (isChangeState && !isChangeStateEnd) {
            return;
        }
        if (weakHandler != null && mCombNumView != null && mCombEffectView != null
                && mNumScaleAnimator != null && mTextScaleAnimator != null) {
            weakHandler.removeMessages(DISMISS_FLOATING);
            setVisibility(VISIBLE);
            setDataForNum(combNum);
            setDataForText(combNum);
            setFloatingParams(combNum);
            mAnimatorSet.playTogether(mNumScaleAnimator, mTextScaleAnimator);
            mAnimatorSet.start();
        }
    }

    private void setFloatingParams(int combNum) {
        if (combNum == GENERAL_RICH_NUM + 1 || combNum == MEDIUM_RICH_NUM + 1 || combNum == SUPER_RICH_NUM + 1) {
            isChangeStateEnd = false;
            isChangeState = true;
            mTextScaleAnimator.setFloatValues(0f, 2.0f);
            mTextScaleAnimator.setDuration(300);
            mTextScaleAnimator.setInterpolator(new DecelerateInterpolator(1.0f));
        } else {
            isChangeStateEnd = false;
            isChangeState = false;
            mTextScaleAnimator.setFloatValues(1.0f, 1.4f, 1.0f);
            mTextScaleAnimator.setDuration(150);
            mTextScaleAnimator.setInterpolator(new LinearInterpolator());
        }
    }

    public void cancelAnim() {
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel(); }
    }

    public void removeSelf() {
        if (weakHandler != null) {
            weakHandler.removeCallbacksAndMessages(null);
        }
        if (this.getVisibility() == VISIBLE) {
            this.setVisibility(GONE);
            cancelAnim();
            ViewParent parent = this.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup)parent).removeView(this);
            }
        }
    }


}
