package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CombFloatingView extends FrameLayout {
    public CombFloatingView(Context context) {
        this(context, null);
    }

    public CombFloatingView(Context context, AttributeSet attrs) {
        this(context, null, 0);
    }

    public CombFloatingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }
}
