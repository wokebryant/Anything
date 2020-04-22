package com.wokebryant.anythingdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.wokebryant.anythingdemo.R;

public class ViewFlipperActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mRootView;
    private ViewFlipper mViewFlipper;

    private Button mBtn1, mBtn2, mBtn3;

    private GestureDetector mGestureDetector;

    private int viewPostion = 0;

    private int previousViewPostion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flipper);
        initView();
    }

    private void initView() {
        mRootView = findViewById(R.id.rootView);
        mViewFlipper = findViewById(R.id.viewFlipper);
        mGestureDetector = new GestureDetector(this, mGestureListener);

        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);

        mBtn1.setOnClickListener(this);
        mBtn2.setOnClickListener(this);
        mBtn3.setOnClickListener(this);

        mRootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
    }

    final int FLING_MIN_DISTANCE = 250, FLING_MIDDLE_DISTANCE = 500 , FLING_MIN_VELOCITY = 1000;
    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                if (mViewFlipper.getDisplayedChild() == 0) {
                    previousViewPostion = 0;
                    mBtn2.performClick();
                } else if (mViewFlipper.getDisplayedChild() == 1) {
                    previousViewPostion = 1;
                    mBtn3.performClick();
                }


            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right
                if (mViewFlipper.getDisplayedChild() == 2) {
                    previousViewPostion = 2;
                    mBtn2.performClick();
                } else if (mViewFlipper.getDisplayedChild() == 1) {
                    previousViewPostion = 1;
                    mBtn1.performClick();
                }

            }
            return true;
        }

    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            if (mViewFlipper.getDisplayedChild() == 0) {
                return;
            }
            mViewFlipper.setInAnimation(this, R.anim.lf_anim_left_fade_in);
            mViewFlipper.setOutAnimation(this, R.anim.lf_anim_right_fade_out);
            mViewFlipper.setDisplayedChild(0);
            previousViewPostion = 0;

        } else if (v.getId() == R.id.btn2) {
            if (mViewFlipper.getDisplayedChild() == 1) {
                return;
            }
            if (previousViewPostion == 0) {
                mViewFlipper.setInAnimation(this, R.anim.lf_anim_right_fade_in);
                mViewFlipper.setOutAnimation(this,R.anim.lf_anim_left_fade_out);
            } else {
                mViewFlipper.setInAnimation(this, R.anim.lf_anim_left_fade_in);
                mViewFlipper.setOutAnimation(this, R.anim.lf_anim_right_fade_out);
            }
            mViewFlipper.setDisplayedChild(1);
        } else if (v.getId() == R.id.btn3) {
            if (mViewFlipper.getDisplayedChild() == 2) {
                return;
            }
            mViewFlipper.setInAnimation(this, R.anim.lf_anim_right_fade_in);
            mViewFlipper.setOutAnimation(this,R.anim.lf_anim_left_fade_out);
            mViewFlipper.setDisplayedChild(2);
            previousViewPostion = 2;
        }
    }
}
