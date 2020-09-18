package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class CommonViewHolder extends BaseViewHolder<CommonItem> {

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
    public void bindViewData(CommonItem data) {
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
    }
}