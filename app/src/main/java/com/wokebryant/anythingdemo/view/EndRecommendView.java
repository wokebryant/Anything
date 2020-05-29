package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.model.EndPageRecommendModel;

public class EndRecommendView extends LinearLayout {

    private static final String TAG = "EndRecommendView";

    private EndPageRecommendModel mModle;

    private ImageView mCoverIv;
    private TextView mNickTv;



    public EndRecommendView(Context context) {
        this(context, null);
    }

    public EndRecommendView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndRecommendView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_end_item_view_recommend, this, true);
        mCoverIv = findViewById(R.id.end_recommend_cover);
        mNickTv = findViewById(R.id.end_recommend_nick);
    }

    public void setData(EndPageRecommendModel model) {
        if (model != null) {
            mModle = model;
            mNickTv.setText(model.nickName);
        }
    }
}
