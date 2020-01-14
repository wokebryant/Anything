package com.wokebryant.anythingdemo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.wokebryant.anythingdemo.R;

public class AttentionGuideDialog extends Dialog {

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

    }

    private void setData() {

    }
}
