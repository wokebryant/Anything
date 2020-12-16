package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.factory.TypeFactory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public class CommonItem extends BaseSettingItem<CommonItem> implements Serializable {

    public String subject;
    public String desc;
    public String tagUrl;
    public String type; //当前item的标识
    public boolean clickable = true; //默认可点击


    public CommonItem(String subject, String desc) {
        this.subject = subject;
        this.desc = desc;
    }

    public void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int type(TypeFactory typeFactory) {
        return typeFactory.type(this);
    }

    @Override
    public CommonItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemSubType() {
        return subject;
    }
}
