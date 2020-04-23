package com.wokebryant.anythingdemo.Demo.MulitTypeRV.bean;


import com.wokebryant.anythingdemo.Demo.MulitTypeRV.decorate.Visitable;
import com.wokebryant.anythingdemo.Demo.MulitTypeRV.factory.TypeFactory;

/**
 * Created by beifeng on 2017/2/17.
 * 头部bean
 */

public class ContentBean implements Visitable {

  /**
   * 工厂类返回对应itembean的类型
   */
  @Override
  public int type(TypeFactory typeFactory) {
    return typeFactory.type(this);
  }
}
