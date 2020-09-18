package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate.Visitable;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public abstract class BaseSettingItem<T extends BaseSettingItem> implements Serializable, Visitable {

    public abstract T parsingItemData(Map map);

    public abstract String getItemSubType();

}
