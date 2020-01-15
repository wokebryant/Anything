package com.wokebryant.anythingdemo.presenter;

import android.os.CountDownTimer;

import com.wokebryant.anythingdemo.mapper.AttentionGuideDataMapper;
import com.wokebryant.anythingdemo.model.AttentionGuideModel;

public class AttentionGuidePresenter {

    private boolean useTimeCount = false;
    private boolean hasAttention = false;

    private AttentionGuideDataMapper mAttentionDataMapper;
    private AttentionGuideModel mAttentionDataModel;
    private IAttentionGuide mAttentionGuideView;
    private CountDownTimer mCountDownTimer;


    public void initView(IAttentionGuide view,boolean hasAttention) {
        mAttentionGuideView = view;
        mAttentionDataMapper = new AttentionGuideDataMapper();
        initAttentionMtop();
        initCountDownTimer();
        this.hasAttention = hasAttention;
    }

    private void initAttentionMtop(){
        mAttentionDataMapper.requestAttentionMtop(new AttentionGuideDataMapper.OnRequestSuccessListener() {
            @Override
            public void requestSuccess(AttentionGuideModel model) {
                mAttentionDataModel = model;
            }

            @Override
            public void requestFail() {

            }
        });
    }

    private void initCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        mCountDownTimer = new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long restTime = millisUntilFinished / 1000 - 1;
                if (restTime == 0) {
                    mAttentionGuideView.showAttentionGuideDialog();
                    useTimeCount = true;
                }
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    public void onChatBoxClick() {
        if (useTimeCount) {
            return;
        }
        if (hasAttention) {
            return;
        }
        if (canAttention()) {
            mAttentionGuideView.showAttentionGuideDialog();
        }
    }

    public void onGiftPopWindowClick() {
        if (useTimeCount) {
            return;
        }
        if (hasAttention) {
            return;
        }
        if (canAttention()) {
            mAttentionGuideView.showAttentionGuideDialog();
        }
    }

    public void onExitRoomClick() {
        if (!hasAttention) {
            mAttentionGuideView.showAttentionExitDialog();
        }
    }

    public void onBottomAttentionClick() {
        requestAttention();
        mAttentionGuideView.onBottomAttentionClick();
        mAttentionGuideView.showAttentionToast();
    }

    public void onCenterAttentionClick() {
        requestAttention();
        mAttentionGuideView.onCenterAttentionClick();
        mAttentionGuideView.showAttentionToast();
    }

    public void onCancelAttentionClick() {
        mAttentionGuideView.closeAttentionDialogClick();
    }

    public void onExitAttentionClick() {
        mAttentionGuideView.closeAttentionDialogClick();
    }

    private boolean canAttention() {
        boolean canAttention = false;
        if (mAttentionDataModel != null) {
            canAttention = mAttentionDataModel.guideArray.get(0).open;
        }
        return canAttention;
    }




    private void requestAttention() {

    }

}
