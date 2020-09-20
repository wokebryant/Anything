package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public class GroupItem extends BaseSettingItem<GroupItem> implements Serializable {

    public String groupName;
    public boolean clickable;

    public GroupItem(String groupName) {
        this.groupName = groupName;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public GroupItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return groupName;
    }
}
