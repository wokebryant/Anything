package com.wokebryant.anythingdemo.presenter;

public interface IAttentionGuide {

        void showAttentionGuideDialog(String avatarUrl,String actorNick);

        void showAttentionExitDialog(String avatarUrl);

        void showChatAttentionBtn();

        void onBottomAttentionClick();

        void onCenterAttentionClick();

        void onChatAttentionClick();

        void closeAttentionDialogClick();

        void showAttentionToast();
}
