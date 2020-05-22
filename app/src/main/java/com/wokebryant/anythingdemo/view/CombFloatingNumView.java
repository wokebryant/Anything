package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wokebryant.anythingdemo.R;

public class CombFloatingNumView extends LinearLayout{

    private static final String TAG = "CombFloatingView";
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 3;

    private ImageView mFirstNumView;
    private ImageView mSecondNumView;

    public CombFloatingNumView(Context context) {
        this(context, null);
    }

    public CombFloatingNumView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombFloatingNumView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_view_comb_num, this, true);
        mFirstNumView = findViewById(R.id.comb_num_1);
        mSecondNumView =findViewById(R.id.comb_num_2);
    }

    public void setData(int combNum) {
        String num = String.valueOf(combNum);
        if (num.length() >= MIN_LENGTH  && num.length() < MAX_LENGTH) {
            mFirstNumView.setVisibility(VISIBLE);

            String one = String.valueOf(num.charAt(0));
            if ("1".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_1);
            } else if ("2".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_2);
            } else if ("3".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_3);
            } else if ("4".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_4);
            } else if ("5".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_5);
            } else if ("6".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_6);
            } else if ("7".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_7);
            } else if ("8".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_8);
            } else if ("9".equals(one)) {
                mFirstNumView.setImageResource(R.drawable.lf_combsend_9);
            }

            if (num.length() == MIN_LENGTH) {
                mSecondNumView.setVisibility(GONE);
            } else {
                mSecondNumView.setVisibility(VISIBLE);
                String two = String.valueOf(num.charAt(1));
                if ("1".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_1);
                } else if ("2".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_2);
                } else if ("3".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_3);
                } else if ("4".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_4);
                } else if ("5".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_5);
                } else if ("6".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_6);
                } else if ("7".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_7);
                } else if ("8".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_8);
                } else if ("9".equals(two)) {
                    mSecondNumView.setImageResource(R.drawable.lf_combsend_9);
                }
            }

        } else {

        }
    }

}
