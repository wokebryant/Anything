package com.wokebryant.anythingdemo.Demo.MulitTypeRV.decorate;

import com.wokebryant.anythingdemo.Demo.MulitTypeRV.factory.TypeFactory;

/**
 * Created by beifeng on 2017/2/17.
 * 所有itembean统一实现的接口
 */

public interface Visitable {
    int type(TypeFactory typeFactory);
}
