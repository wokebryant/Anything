package com.wokebryant.anythingdemo.DynamicsItem.item;

import com.wokebryant.anythingdemo.DynamicsItem.decorate.DynamicsVisitable;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate.Visitable;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wb-lj589732
 */
public abstract class BaseDynamicsItem<T extends BaseDynamicsItem> implements Serializable, DynamicsVisitable {

    /**
     * 照片
     */
    public static final String TYPE_PHOTO = "type_photo";

    /**
     * 短视频
     */
    public static final String TYPE_SHORT_VIDEO = "type_short_video";

    /**
     * 回放
     */
    public static final String TYPE_REPLAY = "type_replay";


    public abstract T parsingItemData(Map map);

    public abstract String getItemMediaType();

}
