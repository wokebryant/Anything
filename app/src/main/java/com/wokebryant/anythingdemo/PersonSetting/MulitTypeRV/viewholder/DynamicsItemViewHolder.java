package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.viewholder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.DynamicsItem;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.util.UIUtil;

/**
 * @author wb-lj589732
 * 动态页面照片
 */
public class DynamicsItemViewHolder extends BaseViewHolder<DynamicsItem>{

    private Context context;
    private ImageView imageView;

    public DynamicsItemViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        imageView = itemView.findViewById(R.id.dynamics_item_iv);
    }

    @Override
    public void bindViewData(DynamicsItem data, int size, int position) {
        setImageViewParams(size);
        imageView.setImageResource(R.color.colorAccent);
    }

    private void setImageViewParams(int size) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
            , ViewGroup.LayoutParams.WRAP_CONTENT);
        if (2 == size || 4 == size) {
            layoutParams.width = UIUtil.dip2px(context, 168);
            layoutParams.height = UIUtil.dip2px(context, 158);
        } else {
            layoutParams.width = UIUtil.dip2px(context, 113);
            layoutParams.height = UIUtil.dip2px(context, 113);
        }
        imageView.setLayoutParams(layoutParams);
    }
}
