package com.wokebryant.anythingdemo.DynamicsItem.item;

import com.wokebryant.anythingdemo.DynamicsItem.factory.DynamicsTypeFactory;

import java.util.Map;

public class DynamicsReplayItem extends BaseDynamicsItem{

    @Override
    public BaseDynamicsItem parsingItemData(Map map) {
        return null;
    }

    @Override
    public String getItemMediaType() {
        return null;
    }

    @Override
    public int type(DynamicsTypeFactory typeFactory) {
        return typeFactory.type(this);
    }
}
