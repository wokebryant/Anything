package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.factory.TypeFactory;

import java.util.List;
import java.util.Map;

public class DynamicsListIItem extends BaseSettingItem<DynamicsListIItem>{

    public List<DynamicsItem> dynamicsItemList;
    public String date;
    public String content;
    public String comment;
    public String praise;

    public DynamicsListIItem(List<DynamicsItem> dynamicsItemList, String date, String content
        , String comment, String praise) {
        this.dynamicsItemList = dynamicsItemList;
        this.date = date;
        this.content = content;
        this.comment = comment;
        this.praise = praise;
    }

    @Override
    public DynamicsListIItem parsingItemData(Map map) {
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
