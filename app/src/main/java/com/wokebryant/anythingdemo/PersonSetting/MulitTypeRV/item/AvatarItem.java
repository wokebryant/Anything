package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.util.Map;

/**
 * @author wb-lj589732
 */
public class AvatarItem extends BaseSettingItem<AvatarItem> {

    public String title;
    public String avatarUrl;

    public AvatarItem(String title, String avatarUrl) {
        this.title = title;
        this.avatarUrl = avatarUrl;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public AvatarItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return SettingConstant.avatar;
    }
}
