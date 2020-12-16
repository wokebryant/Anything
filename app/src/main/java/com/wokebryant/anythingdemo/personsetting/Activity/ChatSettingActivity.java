package com.wokebryant.anythingdemo.personsetting.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class ChatSettingActivity extends AppCompatActivity {

    private ImageView mBackView;
    private RelativeLayout mBaseView;
    private ImageView mAvatarView;
    private TextView mNickView;
    private TextView mBaseInfoView;
    private RelativeLayout mChatTopView;
    private RelativeLayout mChatBgView;
    private RelativeLayout mCleanChatView;
    private RelativeLayout mPullBackView;
    private RelativeLayout mReportView;


    public static void launch(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, ChatSettingActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lf_chat_setting_layout);
        initView();
        setData();
    }

    private void initView() {
        mBackView = findViewById(R.id.setting_back_iv);
        mBaseView = findViewById(R.id.chat_setting_info);
        mAvatarView = findViewById(R.id.chat_setting_avatar);
        mNickView = findViewById(R.id.chat_setting_nick);
        mBaseInfoView = findViewById(R.id.chat_setting_base_info);
        mChatTopView = findViewById(R.id.chat_setting_top);
        mChatBgView = findViewById(R.id.chat_setting_bg);
        mCleanChatView = findViewById(R.id.chat_setting_recording);
        mPullBackView = findViewById(R.id.chat_setting_pull_black);
        mReportView = findViewById(R.id.chat_setting_report);

        mBackView.setOnClickListener(onClickListener);
        mBaseView.setOnClickListener(onClickListener);
        mChatTopView.setOnClickListener(onClickListener);
        mChatBgView.setOnClickListener(onClickListener);
        mCleanChatView.setOnClickListener(onClickListener);
        mPullBackView.setOnClickListener(onClickListener);
        mReportView.setOnClickListener(onClickListener);
    }

    private void setData() {

    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.setting_back_iv) {

            } else if (v.getId() == R.id.chat_setting_info) {

            } else if (v.getId() == R.id.chat_setting_top) {

            } else if (v.getId() == R.id.chat_setting_bg) {

            } else if (v.getId() == R.id.chat_setting_recording) {

            } else if (v.getId() == R.id.chat_setting_pull_black) {

            } else if (v.getId() == R.id.chat_setting_report) {}

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
