package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class PhotoItem extends BaseSettingItem<PhotoItem> implements Serializable {

    public List<String> photoWall;

    public void setPhotoWall(List<String> photoWall) {
        this.photoWall = photoWall;
    }

    @Override
    public PhotoItem parsingItemData(Map map) {
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
