package com.wokebryant.anythingdemo.dynamicsitem.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.dynamicsitem.item.DynamicsPhotoItem;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.util.UIUtil;

public class DynamicsPhotoViewHolder extends BaseDynamicsViewHolder<DynamicsPhotoItem>{

    private Context mContext;
    private ImageView mPhotoView;

    public DynamicsPhotoViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mPhotoView = itemView.findViewById(R.id.lf_dynamics_photo);
    }

    @Override
    public void bindViewData(DynamicsPhotoItem data, int size, int position) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mPhotoView.getLayoutParams();
        if (1 == size) {
            layoutParams.width = UIUtil.dip2px(mContext, 164);
            layoutParams.height = UIUtil.dip2px(mContext, 164);
        } else {
            layoutParams.width = UIUtil.dip2px(mContext, 115);
            layoutParams.height = UIUtil.dip2px(mContext, 115);
        }
        mPhotoView.setLayoutParams(layoutParams);
        mPhotoView.setImageResource(data.photoUrl);


    }
}
