package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate.Visitable;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.util.Map;

public class TagItem extends BaseSettingItem<TagItem> {

    public String tagUrl;
    public String desc;

    public TagItem(String tagUrl, String desc) {
        this.tagUrl = tagUrl;
        this.desc = desc;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public TagItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return null;
    }

}
