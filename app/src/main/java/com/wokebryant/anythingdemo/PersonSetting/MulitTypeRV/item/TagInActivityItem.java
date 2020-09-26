package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public class TagInActivityItem extends BaseSettingItem<TagInActivityItem> implements Serializable {

    public List<String> tagList;

    public TagInActivityItem(List<String> tagList) {
        this.tagList = tagList;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public TagInActivityItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return null;
    }

}
