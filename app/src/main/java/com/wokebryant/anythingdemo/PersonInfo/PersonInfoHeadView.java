package com.wokebryant.anythingdemo.PersonInfo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class PersonInfoHeadView extends FrameLayout {

    private ViewPager mViewPager;



    public PersonInfoHeadView(Context context) {
        this(context, null);
    }

    public PersonInfoHeadView(Context context, AttributeSet attrs) {
        this(context,attrs, 0);
    }

    public PersonInfoHeadView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

    }
}
