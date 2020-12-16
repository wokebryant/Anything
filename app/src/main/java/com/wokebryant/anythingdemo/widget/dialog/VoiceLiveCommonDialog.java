package com.wokebryant.anythingdemo.widget.dialog;

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

public class VoiceLiveCommonDialog extends Dialog implements View.OnClickListener {

    private ImageView mDismissDialog;
    private TextView mContentTv;
    private Button mLeftTextTv;
    private Button mRightTextTv;
    private String mContent;
    private String mLeftText;
    private String mRightText;
    private OnClickListener mOnClickListener;
    private OnCancelListener mOnCancelListener;

    public VoiceLiveCommonDialog(Context context) {
        super(context,R.style.VoiceLiveDialogStyle);
    }


    public VoiceLiveCommonDialog(Context context, String content, String leftText, String rightText, OnClickListener onClickListener) {
        super(context,R.style.VoiceLiveDialogStyle);
        mContent = content;
        mLeftText = leftText;
        mRightText = rightText;
        mOnClickListener = onClickListener;
    }

    public VoiceLiveCommonDialog(Context context, String content, String leftText, String rightText, OnClickListener onClickListener, OnCancelListener onCancelListener) {
        super(context,R.style.VoiceLiveDialogStyle);
        mContent = content;
        mLeftText = leftText;
        mRightText = rightText;
        mOnClickListener = onClickListener;
        mOnCancelListener = onCancelListener;
    }

    public interface OnClickListener {
        void onClick();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        mOnCancelListener = onCancelListener;
    }

    public void setSureBtnVisible(int visible) {
        if (mLeftTextTv != null) {
            mLeftTextTv.setVisibility(visible);
        }
    }

    public void setCancelBtnVisible(int visible) {
        if (mRightTextTv != null) {
            mRightTextTv.setVisibility(visible);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setContentView(R.layout.lf_dialog_voicelive_common);
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
        mDismissDialog = findViewById(R.id.dialog_dismiss);
        mContentTv = findViewById(R.id.dialog_content);
        mLeftTextTv = findViewById(R.id.dialog_sure);
        mRightTextTv = findViewById(R.id.dialog_cancel);

        mDismissDialog.setOnClickListener(this);
        mLeftTextTv.setOnClickListener(this);
        mRightTextTv.setOnClickListener(this);
    }

    private void setData() {
        if (mContent != null) {
            mContentTv.setText(mContent);
        }
        if (mLeftText != null) {
            mLeftTextTv.setText(mLeftText);
        }
        if (mRightText != null) {
            mRightTextTv.setText(mRightText);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_dismiss) {
            dismiss();
        } else if (v.getId() == R.id.dialog_sure) {
            if (mOnClickListener != null) {
                mOnClickListener.onClick();
            }
            dismiss();
        } else if (v.getId() == R.id.dialog_cancel) {
            if (mOnCancelListener != null) {
                mOnCancelListener.onCancel();
            }
            dismiss();
        }
    }

}
