package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.viewholder;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.laifeng.personsetting.SettingConstant;
import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class CommonViewHolder extends BaseViewHolder<CommonItem> {

    private static final int POSITION_REAL_PERSON = 4;
    private static final int POSITION_VERIFIED = 5;

    private TextView mSubView;
    private TextView mDescView;
    private ImageView mTagView;
    private ImageView mArrowView;

    private boolean isSelfPage;

    public CommonViewHolder(View itemView, boolean isSelfPage) {
        super(itemView);
        mSubView = itemView.findViewById(R.id.person_setting_common_sub);
        mDescView = itemView.findViewById(R.id.person_setting_common_desc);
        mTagView = itemView.findViewById(R.id.person_setting_common_tag);
        mArrowView = itemView.findViewById(R.id.person_setting_common_arrow);
        this.isSelfPage = isSelfPage;
    }

    @Override
    public void bindViewData(CommonItem data, int size, int position) {
        mSubView.setText(data.subject);
        mDescView.setText(data.desc);
        if (data.tagUrl != null) {
            mTagView.setVisibility(View.VISIBLE);
        } else {
            mTagView.setVisibility(View.GONE);
        }
        if (data.clickable && isSelfPage) {
            mArrowView.setVisibility(View.VISIBLE);
        } else {
            mArrowView.setVisibility(View.GONE);
        }
        if (data.desc == null) {
            if (position == POSITION_REAL_PERSON || position == POSITION_VERIFIED) {
                mDescView.setText(R.string.person_setting_not_certified);
            } else {
                mDescView.setText(R.string.person_setting_please_add);
            }
            mDescView.setTextColor(Color.parseColor("#999999"));
        } else {
            mDescView.setTextColor(Color.parseColor("#333333"));
        }



        //如果当前items是个性签名，调整itemView高度
        if (SettingConstant.person_setting_sign.equals(data.type)) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)itemView.getLayoutParams();
            layoutParams.height = FrameLayout.LayoutParams.WRAP_CONTENT;
            itemView.setLayoutParams(layoutParams);

            if (SettingConstant.person_setting_sign_content.equals(data.subject)) {
                mSubView.setTextColor(Color.parseColor("#999999"));
            } else {
                mSubView.setTextColor(Color.parseColor("#333333"));
            }
        }
    }
}