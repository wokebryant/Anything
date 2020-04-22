package com.wokebryant.anythingdemo.model;

import java.io.Serializable;

public class ChiefPanelManagerModel implements Serializable {

    public long userId;
    public String faceUrl;
    public int resId;

    public ChiefPanelManagerModel(long userId, String faceUrl) {
        this.userId = userId;
        this.faceUrl = faceUrl;
    }

    public ChiefPanelManagerModel(long userId, int resId) {
        this.userId = userId;
        this.resId = resId;
    }


}
