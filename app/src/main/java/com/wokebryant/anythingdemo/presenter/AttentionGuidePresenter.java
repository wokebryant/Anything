package com.wokebryant.anythingdemo.presenter;

import android.os.CountDownTimer;

import com.wokebryant.anythingdemo.mapper.AttentionGuideDataMapper;
import com.wokebryant.anythingdemo.model.AttentionGuideModel;

public class AttentionGuidePresenter {

    private AttentionGuideDataMapper mAttentionDataMapper;
    private AttentionGuideModel mAttentionDataModel;
    private IAttentionGuide mAttentionGuideView;
    private CountDownTimer mCountDownTimer;

    private String mActorAvatarUrl;
    private String mActorNick;
    private boolean mAttentionState;

    private boolean useFirstTimer;  //观看15s触发关注
    private boolean useSecondTimer; //观看30s退出直播间触发关注
    private boolean useDialogAttention;  //通过输入面板和礼物面板触发关注

    //如果用户在进入直播间30s退出了直播间，是否还展示第二次确认弹窗？
    //如果用户通过顶部主播信息栏或者个人卡片关注了主播，怎样获取当前关注状态？


    //初始化view和获取actorinfo,mtop数据
    public void initPresenter(IAttentionGuide view,ActorInfo actorinfo) {
        mAttentionGuideView = view;
        mAttentionDataMapper = new AttentionGuideDataMapper();
        initActorInfo(actorinfo);
        initAttentionMtop();
        initCountDownTimer();
    }

    private void initActorInfo(ActorInfo actorinfo) {
        if (actorinfo != null) {
            mActorAvatarUrl = actorinfo;
            mActorNick = actorinfo;
            mAttentionState = actorinfo;
        }
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
        mCountDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long restTime = millisUntilFinished / 1000 - 1;
                if (restTime == 15) {
                    if (mAttentionState) {
                        return;
                    }
                    if (useDialogAttention) {
                        return;
                    }
                    mAttentionGuideView.showAttentionGuideDialog(mActorAvatarUrl,mActorNick);
                    useFirstTimer = true;
                }
                if (restTime == 0) {
                    useSecondTimer = true;
                }
            }

            @Override
            public void onFinish() {

            }
        };
        mCountDownTimer.start();
    }

    //点击输入框
    public void onChatBoxClick() {
        if (mAttentionState) {
            return;
        }
        if (useFirstTimer) {
            return;
        }
        if (canAttention()) {
            mAttentionGuideView.showAttentionGuideDialog(mActorAvatarUrl,mActorNick);
            useDialogAttention = true;
        }
    }

    //点击送礼面板
    public void onGiftPopWindowClick() {
        if (mAttentionState) {
            return;
        }
        if (useFirstTimer) {
            return;
        }
        if (canAttention()) {
            mAttentionGuideView.showAttentionGuideDialog(mActorAvatarUrl,mActorNick);
            useDialogAttention = true;
        }
    }

    //点击退出直播间按钮
    public void onExitRoomClick() {
        if (!mAttentionState && useSecondTimer) {
            mAttentionGuideView.showAttentionExitDialog(mActorAvatarUrl);
        }
    }

    //聊天区关注点击
    public void onChatAttentionClick() {
        if (mAttentionState) {
            return;
        }
        requestAttention();
        mAttentionGuideView.onChatAttentionClick();
    }

    //底部弹窗关注点击
    public void onBottomAttentionClick() {
        requestAttention();
        mAttentionGuideView.onBottomAttentionClick();
        mAttentionGuideView.showAttentionToast();
    }

    //中间弹窗关注点击
    public void onCenterAttentionClick() {
        requestAttention();
        mAttentionGuideView.onCenterAttentionClick();
        mAttentionGuideView.showAttentionToast();
    }

    //关闭中间弹窗
    public void onCancelAttentionClick() {
        mAttentionGuideView.closeAttentionDialogClick();
    }

    //退出中间弹窗
    public void onExitAttentionClick() {
        mAttentionGuideView.closeAttentionDialogClick();
    }

    //通过mtop接口获取关注状态
    private boolean canAttention() {
        boolean canAttention = false;
        if (mAttentionDataModel != null) {
            canAttention = mAttentionDataModel.guideArray.get(0).open;
        }
        return canAttention;
    }

    //通过引导逻辑关注主播
    private void requestAttention() {
        mAttentionState = true;
    }

    //通过主播信息栏或者个人卡片关注主播后更新关注状态
    private void updateAttentionState(boolean attentionState) {
        mAttentionState = attentionState;
    }

}
