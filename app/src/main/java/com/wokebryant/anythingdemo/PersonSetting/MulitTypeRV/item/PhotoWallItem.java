package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author wb-lj589732
 * 头像，照片墙
 */
public class PhotoWallItem extends BaseSettingItem<PhotoWallItem> implements Serializable {

    public String progress;
    public List<PhotoItem> photoList;

    public PhotoWallItem(String progress, List<PhotoItem> photoList) {
        this.progress = progress;
        this.photoList = photoList;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setPhotoList(List<PhotoItem> photoList) {
        this.photoList = photoList;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public PhotoWallItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return SettingConstant.avatar;
    }
}
