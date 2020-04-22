package com.wokebryant.anythingdemo.model;

import java.io.Serializable;

public class ChiefPanelHostModel implements Serializable {

    public long userId;
    public String faceUrl;
    public int resId;

    public ChiefPanelHostModel(long userId, String faceUrl) {
        this.userId = userId;
        this.faceUrl = faceUrl;
    }

    public ChiefPanelHostModel(long userId, int resId) {
        this.userId = userId;
        this.resId = resId;
    }


}
