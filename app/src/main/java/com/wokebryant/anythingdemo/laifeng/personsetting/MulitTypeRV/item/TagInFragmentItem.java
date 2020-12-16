package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.factory.TypeFactory;

import java.util.Map;

/**
 * @author wb-lj589732
 */
public class TagInFragmentItem extends BaseSettingItem<TagInFragmentItem>{

    public String tagName;

    public TagInFragmentItem(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public TagInFragmentItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return null;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
