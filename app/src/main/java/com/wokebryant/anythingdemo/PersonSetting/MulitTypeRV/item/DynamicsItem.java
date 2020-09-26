package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.util.Map;

public class DynamicsItem extends BaseSettingItem<DynamicsItem>{

    public String photoUrl;

    @Override
    public DynamicsItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return null;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return 0;
    }
}
