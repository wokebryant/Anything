package com.wokebryant.anythingdemo.widget.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.R;


public class ProgressSendView extends FrameLayout {

    private static final int MSG_COUNTDOWN = 1;
    private int mTotalTime;
    private int mIntervalTime = 50;
    private int mCurrentProgress = 100;

    private ProgressRing mQuickSendView;
    private ImageView mGiftIcon;

    private QuickSendListener mSendListener;


    public ProgressSendView(@NonNull Context context) {
        super(context);
        initView();
    }

    public ProgressSendView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ProgressSendView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public interface QuickSendListener {
        void onCountDownEnd();
    }

    public void setQuickSendListener(QuickSendListener listener) {
        mSendListener = listener;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_COUNTDOWN:
                    countDownProgress();
                    break;
                default:
            }
        }
    };

    public void countDownProgress() {
        if (mCurrentProgress != 0) {
            mHandler.sendEmptyMessageDelayed(MSG_COUNTDOWN, mIntervalTime);
            mQuickSendView.setProgress(mCurrentProgress--);
        } else {
            reset();
            if (mSendListener != null) {
                mSendListener.onCountDownEnd();
            }
        }
    }


    private void initView() {
        View.inflate(getContext(), R.layout.lfcontainer_layout_quick_gift_send, this);
        mQuickSendView = findViewById(R.id.quickSendView);
        mGiftIcon = findViewById(R.id.gift_icon);
    }

    public void setTotalTime(String totalTime) {
        mTotalTime = Integer.parseInt(totalTime);
        mIntervalTime = mTotalTime / 100;
    }

    public void resetAndStartProgress(final String currentTime) {
        if (mHandler != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mQuickSendView == null) {
                        return;
                    }
                    if (Integer.parseInt(currentTime) == 0) {
                        reset();
                        countDownProgress();
                        mQuickSendView.setVisibility(VISIBLE);
                    } else {
                        reset();
                    }
                }
            }, 50);
        }
    }

    public void showGiftIcon(String giftUrl) {
//        if (!TextUtils.isEmpty(giftUrl) && LaifengService.getService(IImageFacotry.class) != null) {
//            LaifengService.getService(IImageFacotry.class).displayRect(giftUrl, mGiftIcon);
//        }
    }

    public void reset() {
        mHandler.removeMessages(MSG_COUNTDOWN);
        mCurrentProgress = 100;
        mQuickSendView.setProgress(mCurrentProgress);
        mQuickSendView.setVisibility(GONE);
    }

    public void destroy() {
        reset();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }

}
