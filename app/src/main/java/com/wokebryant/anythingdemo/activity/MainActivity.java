package com.wokebryant.anythingdemo.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.dialog.AttentionExitDialog;
import com.wokebryant.anythingdemo.dialog.AttentionGuideDialog;
import com.wokebryant.anythingdemo.mapper.AttentionGuideDataMapper;
import com.wokebryant.anythingdemo.presenter.AttentionGuidePresenter;
import com.wokebryant.anythingdemo.presenter.IAttentionGuide;
import com.wokebryant.anythingdemo.view.WaveProgressView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IAttentionGuide {

    private static final int UPDATE_PROGRESS = 1;
    private static final int MAX_PROGRESS = 100;
    private int mCurrentProgress = 0;
    private WaveProgressView mWaveProgressView;
    private Button mButton;

    //关注引导
    private AttentionGuidePresenter mAttentionPresenter;
    private boolean hasAttention;

    private AttentionGuideDialog mAttentionGuideDialog;
    private AttentionExitDialog mAttentionExitDialog;

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
        mAttentionPresenter = new AttentionGuidePresenter();
        mAttentionPresenter.initPresenter(this,hasAttention);
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

    @Override
    public void showAttentionGuideDialog(String avatarUrl,String actorNick) {
        mAttentionGuideDialog = new AttentionGuideDialog(this);
        mAttentionGuideDialog.setAttentionBtnClickListener(new AttentionGuideDialog.OnAttentionButtonClickListener() {
            @Override
            public void onAttentionButtonClick() {
                mAttentionPresenter.onBottomAttentionClick();
            }
        });
        mAttentionGuideDialog.setActorInfo(avatarUrl,actorNick);
        mAttentionGuideDialog.show();
    }

    @Override
    public void showAttentionExitDialog(String avatarUrl) {
        mAttentionExitDialog = new AttentionExitDialog(this);
        mAttentionExitDialog.setAttentionExitClickListener(new AttentionExitDialog.OnAttentionExitClickListener() {
            @Override
            public void onCancelButtonClick() {
                mAttentionPresenter.onCancelAttentionClick();
            }

            @Override
            public void onFollowButtonClick() {
                mAttentionPresenter.onCenterAttentionClick();
            }

            @Override
            public void onExitButtonClick() {
                mAttentionPresenter.onExitAttentionClick();
            }
        });
        mAttentionExitDialog.setActorInfo(avatarUrl);
        mAttentionExitDialog.show();
    }

    @Override
    public void showChatAttentionBtn() {
        //todo
    }

    @Override
    public void onBottomAttentionClick() {
        hideWatcherAttentionBtn();
        if (mAttentionGuideDialog != null && mAttentionGuideDialog.isShowing()) {
            mAttentionGuideDialog.dismiss();
        }
    }

    @Override
    public void onCenterAttentionClick() {
        hideWatcherAttentionBtn();
        if (mAttentionExitDialog != null && mAttentionExitDialog.isShowing()) {
            mAttentionExitDialog.dismiss();
        }
    }

    @Override
    public void onChatAttentionClick() {
        updateChatAttentionState();
    }

    @Override
    public void closeAttentionDialogClick() {
        if (mAttentionExitDialog != null && mAttentionExitDialog.isShowing()) {
            mAttentionExitDialog.dismiss();
        }
    }

    @Override
    public void showAttentionToast() {
        //todo
    }

    private void updateChatAttentionState() {
        //todo
    }

    private void hideWatcherAttentionBtn() {
        //todo
    }
}

