package com.wokebryant.anythingdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

public class LFDialog extends Dialog {

    private String title;
    private String content;
    private TextView textTitle;
    private TextView textContent;
    private OnClickListener onClickListener;
    private OnCancelListener onCancelListener;
    private String leftText;
    private String rightText;
    private Button btnCancel;
    private Button btnSure;
    private TextView textVice;

    private static final int GRAVITY_LEFT = 1;
    private static final int GRAVITY_CENTER = 0;
    private int contentGravity = GRAVITY_CENTER;

    public LFDialog(Context context) {
        super(context);
    }

    public LFDialog(Context context, int theme) {
        super(context, theme);
    }

    public LFDialog(String title, String content, Context context, int theme, OnClickListener onClickListener) {
        super(context, theme);
        this.title = title;
        this.content = content;
        this.onClickListener = onClickListener;
    }

    public LFDialog(String title, String content, Context context, int theme, OnClickListener onClickListener, OnCancelListener onCancelListener) {
        super(context, theme);
        this.title = title;
        this.content = content;
        this.onClickListener = onClickListener;
        this.onCancelListener = onCancelListener;
    }

    public LFDialog(String title, String content, String leftText, String rightText, Context context, int theme, OnClickListener onClickListener) {
        super(context, theme);
        this.title = title;
        this.content = content;
        this.leftText = leftText;
        this.rightText = rightText;
        this.onClickListener = onClickListener;
    }

    public LFDialog(String title, String content, String leftText, String rightText, Context context, int theme, OnClickListener onClickListener, OnCancelListener onCancelListener) {
        super(context, theme);
        this.title = title;
        this.content = content;
        this.leftText = leftText;
        this.rightText = rightText;
        this.onClickListener = onClickListener;
        this.onCancelListener = onCancelListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    public interface OnClickListener {
        void onClick();
    }

    public interface OnCancelListener {
        void onCancel();
    }

    public void setSureBtnVisible(int visible) {
        btnSure.setVisibility(visible);
    }

    public void setCancelBtnVisible(int visible) {
        btnCancel.setVisibility(visible);
    }

    public void setSureBtnColor(int color) {
        btnSure.setTextColor(color);
    }

    public void setCancelBtnColor(int color) {
        btnCancel.setTextColor(color);
    }


    public void setViceText(String content) {
        if (!TextUtils.isEmpty(content) && textVice.getVisibility() == View.INVISIBLE) {
            textVice.setVisibility(View.VISIBLE);
            textVice.setText(content);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        setContentView(R.layout.lf_dialog_account_security);
        setDialogWindow(this);
        initView();
    }

    private void setDialogWindow(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    public void setContentGravityLeft() {
        contentGravity = GRAVITY_LEFT;
    }

    public void setContentGravityCenter() {
        contentGravity = GRAVITY_CENTER;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
    }

    private void initView() {
        textTitle = (TextView) findViewById(R.id.textTitle);
        textContent = (TextView) findViewById(R.id.textContent);
        textVice = findViewById(R.id.viceContent);
        if(!TextUtils.isEmpty(title)) {
            textTitle.setText(title);
        } else {
            textTitle.setVisibility(View.INVISIBLE);
        }
        if(!TextUtils.isEmpty(content)) {
            textContent.setText(content);
        } else {
            textContent.setVisibility(View.GONE);
        }
        if(contentGravity == GRAVITY_LEFT) {
            textContent.setGravity(Gravity.LEFT);
        } else {
            textContent.setGravity(Gravity.CENTER);
        }
        btnCancel = (Button) findViewById(R.id.btnCancel);
        if (TextUtils.isEmpty(leftText)) {
            btnCancel.setText("取消");
        } else {
            btnCancel.setText(leftText);
        }
        btnSure = (Button) findViewById(R.id.btnSure);
        if (TextUtils.isEmpty(rightText)) {
            btnSure.setText("确定");
        } else {
            btnSure.setText(rightText);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                dismiss();
            }
        });

        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick();
                }
                dismiss();
            }
        });
    }
}