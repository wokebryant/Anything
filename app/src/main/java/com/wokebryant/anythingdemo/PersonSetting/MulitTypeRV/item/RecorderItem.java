package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public class RecorderItem extends BaseSettingItem<RecorderItem> implements Serializable {

    public String recorderUrl;
    public String recorderDuration;

    public RecorderItem(String recorderUrl, String recorderDuration) {
        this.recorderUrl = recorderUrl;
        this.recorderDuration = recorderDuration;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public RecorderItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return SettingConstant.recorder;
    }
}
