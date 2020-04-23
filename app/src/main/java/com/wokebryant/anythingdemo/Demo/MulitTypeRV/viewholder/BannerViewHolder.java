package com.wokebryant.anythingdemo.Demo.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.TextView;

import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.BannerBean;
import com.wokebryant.anythingdemo.R;


/**
 * Created by beifeng on 2017/2/17.
 * banner viewholder
 */

public class BannerViewHolder extends BaseViewHolder {

  TextView textView;
  public BannerViewHolder(View itemView) {
    super(itemView);
    textView = itemView.findViewById(R.id.sdv_pictuer);
  }

  @Override
  public void bindViewData(Object data) {
    textView.setText(((BannerBean)data).getUrl());
  }

}
