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
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

public class AttentionGuideDialog extends Dialog implements View.OnClickListener {

    private ImageView mAvatatIv;
    private TextView mAvatarNick;
    private Button mAttentionBtn;

    private OnAttentionButtonClickListener mListener;

    public interface OnAttentionButtonClickListener {
        void onAttentionButtonClick();
    }

    public void setAttentionBtnClickListener(OnAttentionButtonClickListener listener) {
        mListener = listener;
    }

    public AttentionGuideDialog(Context context) {
        super(context,R.style.AttentionGuideStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.lf_dialog_attention_guide);
        setDialogWindow(this);
        initView();
        setData();
    }

    private void setDialogWindow(Dialog dialog) {
        final Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
    }

    private void initView() {
        mAvatatIv = findViewById(R.id.actor_avatar_iv);
        mAvatarNick = findViewById(R.id.actor_name_tv);
        mAttentionBtn = findViewById(R.id.actor_attention_btn);
    }

    private void setData() {

    }

    public void setActorInfo() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mAttentionBtn.getId()) {
            mListener.onAttentionButtonClick();
            dismiss();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
