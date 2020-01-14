package com.wokebryant.anythingdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wokebryant.anythingdemo.view.WaveProgressView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int UPDATE_PROGRESS = 1;
    private static final int MAX_PROGRESS = 100;
    private int mCurrentProgress = 0;
    private WaveProgressView mWaveProgressView;
    private Button mButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_PROGRESS) {
                if (mCurrentProgress != MAX_PROGRESS) {
                    mCurrentProgress += 1;
                    mWaveProgressView.setProgress(mCurrentProgress);
                    sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                } else {
                    mCurrentProgress = 0;
                    mWaveProgressView.setProgress(0);
                    mWaveProgressView.resetProgress();
                    sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                    //mWaveProgressView.release();
                }

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mWaveProgressView = findViewById(R.id.waveProgressView);
        mButton = findViewById(R.id.testBtn);

        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.testBtn:
                //mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS,100);
                mWaveProgressView.setProgress(100);
                break;
            default:
        }
    }

}

