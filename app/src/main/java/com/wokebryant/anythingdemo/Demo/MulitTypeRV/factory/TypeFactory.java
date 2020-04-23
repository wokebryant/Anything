package com.wokebryant.anythingdemo.Demo.MulitTypeRV.factory;

import android.view.View;

import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.BannerBean;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean.ContentBean;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.viewholder.BaseViewHolder;


/**
 * Created by beifeng on 2017/2/17.
 * type 工厂类 用来返回不同的itmetpe
 */

public interface TypeFactory {
  //  定义所有的返回类型
  int type(BannerBean bannerBean);

  int type(ContentBean contentBean);

  BaseViewHolder createViewHolder(int type, View itemView);
}

