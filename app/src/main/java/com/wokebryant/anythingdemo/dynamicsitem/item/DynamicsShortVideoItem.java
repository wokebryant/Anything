package com.wokebryant.anythingdemo.dynamicsitem.item;

import com.wokebryant.anythingdemo.dynamicsitem.factory.DynamicsTypeFactory;

import java.util.Map;

public class DynamicsShortVideoItem extends BaseDynamicsItem{

    public String shortVideoUrl;

    public String duration;

    public String onlineNum;

    public DynamicsShortVideoItem(String shortVideoUrl, String duration, String onlineNum) {
        this.shortVideoUrl = shortVideoUrl;
        this.duration = duration;
        this.onlineNum = onlineNum;
    }

    @Override
    public BaseDynamicsItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemMediaType() {
        return BaseDynamicsItem.TYPE_SHORT_VIDEO;
    }

    @Override
    public int type(DynamicsTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
