package com.wokebryant.anythingdemo.DynamicsItem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

/**
 * 个人动态item
 * @author wb-lj589732
 */

public class PersonalDynamicsItemView extends LinearLayout {

    private static final String TAG = "PersonalDynamicsItemVie";

    private TextView mDateTv;
    private TextView mContentTv;
    private RecyclerView mPhotoRv;
    private ImageView mShareIv;
    private ImageView mCommentIv;
    private ImageView mPraiseIv;
    private TextView mCommentTv;
    private TextView mPraiseTv;


    public PersonalDynamicsItemView(Context context) {
        this(context, null);
    }

    public PersonalDynamicsItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalDynamicsItemView(Context context, AttributeSet attrs,
                                    int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        setData();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_personal_dynamics_item, this, true);
        mDateTv = findViewById(R.id.dynamics_date_tv);
        mContentTv = findViewById(R.id.dynamics_content_tv);
        mPhotoRv = findViewById(R.id.dynamics_photo_rv);
        mShareIv = findViewById(R.id.dynamics_share_iv);
        mCommentIv = findViewById(R.id.dynamics_comment_iv);
        mPraiseIv = findViewById(R.id.dynamics_praise_iv);
        mCommentTv = findViewById(R.id.dynamics_content_tv);
        mPraiseTv = findViewById(R.id.dynamics_praise_tv);

        mShareIv.setOnClickListener(onClickListener);
        mCommentIv.setOnClickListener(onClickListener);
        mCommentTv.setOnClickListener(onClickListener);
        mPraiseIv.setOnClickListener(onClickListener);
        mPraiseTv.setOnClickListener(onClickListener);
    }

    private void setData() {

    }

    private View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.dynamics_share_iv) {
                shareDynamics();
            } else if (v.getId() == R.id.dynamics_comment_iv || v.getId() == R.id.dynamics_comment_tv) {
                commentDynamics();
            } else if (v.getId() == R.id.dynamics_praise_iv || v.getId() == R.id.dynamics_praise_tv) {
                praiseDynamics();
            }
        }
    };

    private void shareDynamics() {

    }

    private void commentDynamics() {

    }

    private void praiseDynamics() {

    }

}
