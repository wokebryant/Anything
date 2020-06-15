package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.R;

public class CombGiftView extends FrameLayout {

    private ImageView imageIcon;

    public CombGiftView(Context context) {
        this(context, null);
    }

    public CombGiftView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombGiftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.lf_view_comb_gift, this, true);
        imageIcon = (ImageView) findViewById(R.id.gift_icon);

    }

    public void setData(String giftUrl) {

    }
}
