package com.wokebryant.anythingdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.R;

public class AttentionExitDialog extends Dialog implements View.OnClickListener {

    private ImageView mAvatarIv;
    private Button mCancelBtn,mFollowBtn,mExitBtn;

    private String mActorAvatarUrl;

    private OnAttentionExitClickListener mListener;

    public interface OnAttentionExitClickListener {
        void onCancelButtonClick();

        void onFollowButtonClick();

        void onExitButtonClick();
    }

    public void setAttentionExitClickListener(OnAttentionExitClickListener listener) {
        mListener = listener;
    }


    public AttentionExitDialog(Context context) {
        super(context,R.style.AttentionGuideStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.lf_dialog_attention_exit);
        setDialogWindow(this);
        initView();
        setData();
    }

    private void setDialogWindow(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    private void initView() {
        mAvatarIv = findViewById(R.id.actor_avatar_big_iv);
        mCancelBtn = findViewById(R.id.attention_exit_btn);
        mFollowBtn = findViewById(R.id.follow_exit_btn);
        mExitBtn = findViewById(R.id.exit_btn);

        mCancelBtn.setOnClickListener(this);
        mFollowBtn.setOnClickListener(this);
        mExitBtn.setOnClickListener(this);
    }

    private void setData() {
        if (mActorAvatarUrl != null) {
            //todo
        }
    }

    public void setActorInfo(String actorAvatarUrl) {
        mActorAvatarUrl = actorAvatarUrl;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mCancelBtn.getId()) {
            mListener.onCancelButtonClick();
        } else if (v.getId() == mFollowBtn.getId()) {
            mListener.onFollowButtonClick();
        } else if (v.getId() == mExitBtn.getId()) {
            mListener.onExitButtonClick();
        }
    }

}
