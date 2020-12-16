package com.wokebryant.anythingdemo.widget.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.badoo.mobile.util.WeakHandler;
import com.wokebryant.anythingdemo.R;

public class CombSendView extends FrameLayout {

    private static final String TAG = "CombSendView";
    private static final int STOP_COMB_SEND = 1;
    private static final int INTERVAL = 5000; //松手后圆环旋转的时间

    private int mCombNum = 0;

    private Context mContext;

    private Button mCombSendBtn;
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
        mCombSendBtn = findViewById(R.id.comb_send_btn);
        mRotateView = findViewById(R.id.comb_loading_view);
        mRotateAnim = AnimationUtils.loadAnimation(mContext, R.anim.lf_anim_combsend_progress);
        mRotateAnim.setInterpolator(new LinearInterpolator());

        mCombSendBtn.setOnTouchListener(mOnTouchListener);
    }

    private OnTouchListener mOnTouchListener = new OnTouchListener() {
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
            return false;
        }
    };


    /** 按下去重置参数**/
    private void touchDown() {
        if (weakHandler != null) {
            weakHandler.removeMessages(STOP_COMB_SEND);
        }
        onCombSendListener.onTouchDown();
    }

    /** 抬起来开始连送**/
    public void touchUp() {
        if (weakHandler != null && onCombSendListener != null) {
            weakHandler.sendEmptyMessageDelayed(STOP_COMB_SEND, INTERVAL);
            mCombNum ++;
            onCombSendListener.onCombSend(mCombNum);
            onCombSendListener.onTouchUp();
            startRotateAnim();
        }
    }

    private void stopCombSend() {
        if (weakHandler != null && onCombSendListener != null) {
            onCombSendListener.onCombSendEnd();
            mCombNum = 0;
            reset();
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
        void onCombSend(int combNum);

        void onCombSendEnd();

        void onTouchUp();

        void onTouchDown();
    }

    public void setOnCombSendListener(OnCombSendListener listener) {
        onCombSendListener = listener;
    }


}
