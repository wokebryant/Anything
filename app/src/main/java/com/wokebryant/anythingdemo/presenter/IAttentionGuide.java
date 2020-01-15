package com.wokebryant.anythingdemo.presenter;

public interface IAttentionGuide {

        void showAttentionGuideDialog();

        void showAttentionExitDialog();

        void showChatAttentionBtn();

        void onBottomAttentionClick();

        void onCenterAttentionClick();

        void onChatAttentionClick();

        void closeAttentionDialogClick();

        void showAttentionToast();
}
