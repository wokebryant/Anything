package com.wokebryant.anythingdemo.personsetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.personsetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public class PhotoItem extends BaseSettingItem<PhotoItem> implements Serializable {

    public static final String defaultUrl = "url";

    public String photoUrl;

    public PhotoItem(String photoUrl) {
        this.photoUrl = photoUrl;
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
