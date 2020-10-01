package com.wokebryant.anythingdemo.DynamicsItem.decorate;

public interface DynamicsItemCallBack {

    void onDetailClick();

    void onShortVideoClick(String url);

    void onReplayClick();

    void onPhotoListClick(int position);

    void onCommentClick();

    void onPraiseClick();

    void onTeaseClick();

}
