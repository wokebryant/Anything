package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.badoo.mobile.util.WeakHandler;
import com.wokebryant.anythingdemo.R;

import java.lang.ref.WeakReference;

public class CombSendView extends FrameLayout implements View.OnTouchListener {

    private static final String TAG = "CombSendView";
    private static final int STOP_COMB_SEND = 1;
    private static final int INTERVAL = 2500;

    private Context mContext;

    private ImageView mRotateView;
    private Animation mRotateAnim;

    private OnCombSendListener onCombSendListener;

    public WeakHandler weakHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_COMB_SEND:
                    stopCombSend();
                    break;
                default:
            }
            return false;
        }
    });



    public CombSendView(Context context) {
        super(context);
        initView();
    }

    public CombSendView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CombSendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mContext = getContext();

        View.inflate(mContext, R.layout.lf_view_combsend, this);
        mRotateView = findViewById(R.id.comb_loading_view);
        mRotateAnim = AnimationUtils.loadAnimation(mContext, R.anim.lf_anim_combsend_progress);
        mRotateAnim.setInterpolator(new LinearInterpolator());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchDown();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }
        return true;
    }

    /** 按下去重置参数**/
    private void touchDown() {
        if (weakHandler != null) {
            weakHandler.removeMessages(STOP_COMB_SEND);
        }
    }

    /** 抬起来开始连送**/
    public void touchUp() {
        if (weakHandler != null && onCombSendListener != null) {
            weakHandler.sendEmptyMessageDelayed(STOP_COMB_SEND, INTERVAL);
            onCombSendListener.onCombSend();
            startRotateAnim();
        }
    }

    private void stopCombSend() {
        if (weakHandler != null && onCombSendListener != null) {
            onCombSendListener.onCombSendEnd();
        }
    }

    private void startRotateAnim() {
        if (mRotateAnim != null) {
            mRotateView.startAnimation(mRotateAnim);
        } else {
            mRotateView.setAnimation(mRotateAnim);
            mRotateView.startAnimation(mRotateAnim);
        }
    }

    private void stopRotateAnim() {
        if (mRotateView != null) {
            mRotateView.clearAnimation();
        }
    }

    public void reset() {
        stopRotateAnim();
        if (weakHandler != null) {
            weakHandler.removeCallbacksAndMessages(null);
        }
    }

    public interface OnCombSendListener {
        void onCombSend();

        void onCombSendEnd();
    }

    public void setOnCombSendListener(OnCombSendListener listener) {
        onCombSendListener = listener;
    }


}
