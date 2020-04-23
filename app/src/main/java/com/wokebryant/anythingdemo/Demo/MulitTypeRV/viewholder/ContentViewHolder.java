package com.wokebryant.anythingdemo.Demo.MulitTypeRV.viewholder;

import android.view.View;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

/**
 * Created by beifeng on 2017/2/17.
 * banner viewholder
 */

public class ContentViewHolder extends BaseViewHolder {

  TextView textView;

  public ContentViewHolder(View itemView) {
    super(itemView);
    textView = itemView.findViewById(R.id.sdv_content);
  }

  @Override public void bindViewData(Object data) {
    //imageview.setImageURI(((BannerBean)data).getUrl());
  }
}
