package com.wokebryant.anythingdemo.personsetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.personsetting.MulitTypeRV.decorate.Visitable;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public abstract class BaseSettingItem<T extends BaseSettingItem> implements Serializable, Visitable {

    public abstract T parsingItemData(Map map);

    public abstract String getItemSubType();

}
