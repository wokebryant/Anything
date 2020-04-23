package com.wokebryant.anythingdemo.Demo.MulitTypeRV.factory;

import android.view.View;

import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.BannerBean;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.ContentBean;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.viewholder.BannerViewHolder;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.viewholder.BaseViewHolder;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.viewholder.ContentViewHolder;
import com.wokebryant.anythingdemo.R;


/**
 * Created by beifeng on 2017/2/17.
 * 实现type工程类
 */

public class ItemTypeFactory implements TypeFactory {
  //  将id作为type传入adapter
  public static final int BANNER_ITEM_LAYOUT = R.layout.item_banner_mulittype;
  public static final int CONTENT_ITEM_LAYOUT = R.layout.item_content_mulittype;
  @Override public int type(BannerBean bannerBean) {
    return BANNER_ITEM_LAYOUT;
  }

  @Override public int type(ContentBean contentBean) {
    return CONTENT_ITEM_LAYOUT;
  }

  @Override public BaseViewHolder createViewHolder(int type, View itemView) {
    switch (type) {
      case BANNER_ITEM_LAYOUT:
        return new BannerViewHolder(itemView);
      case CONTENT_ITEM_LAYOUT:
        return new ContentViewHolder(itemView);
      default:
        return null;
    }
  }
}
