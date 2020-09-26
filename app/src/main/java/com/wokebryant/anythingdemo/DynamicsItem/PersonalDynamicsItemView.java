package com.wokebryant.anythingdemo.DynamicsItem;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.GridSpacingItemDecoration;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.R;

import java.util.List;

/**
 * 个人动态item
 * @author wb-lj589732
 */

public class PersonalDynamicsItemView extends LinearLayout {

    private static final String TAG = "PersonalDynamicsItemVie";

    private MultiAdapter mAdapter;
    private List<BaseSettingItem> mPhotoList;

    private TextView mDateTv;
    private TextView mContentTv;
    private ImageView mSinglePhotoIv;
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
        setPhotoData();
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_personal_dynamics_item, this, true);
        mDateTv = findViewById(R.id.dynamics_date_tv);
        mContentTv = findViewById(R.id.dynamics_content_tv);
        mSinglePhotoIv = findViewById(R.id.dynamics_single_photo_iv);
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

    private void setPhotoData() {
        if (mPhotoList != null && !mPhotoList.isEmpty()) {
            if (mPhotoList.size() == 1) {
                //TODO 设置单张图片
            } else {
                mAdapter = new MultiAdapter(mPhotoList, false);
                GridLayoutManager manager = new GridLayoutManager(getContext(), 4);
                mPhotoRv.setLayoutManager(manager);
                mPhotoRv.addItemDecoration(getSpaceItemDecoration(mPhotoList.size()));
                mPhotoRv.setAdapter(mAdapter);
            }
        }


    }

    private GridSpacingItemDecoration getSpaceItemDecoration(int size) {
        GridSpacingItemDecoration itemDecoration;
        if (size == 2 || size == 4) {
            itemDecoration = new GridSpacingItemDecoration(2, 11, false);
        } else {
            itemDecoration = new GridSpacingItemDecoration(3, 3, false);
        }
        return itemDecoration;
    }

    private MultiAdapter.OnItemClickListener onItemClickListener = new MultiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String subType, int position) {

        }
    };

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
