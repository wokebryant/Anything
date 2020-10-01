package com.wokebryant.anythingdemo.DynamicsItem.model;

import java.io.Serializable;

public class DynamicsShortVideoModel implements Serializable {

    public String playUrl;

    public String duration;

    public String onlineNum;

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setOnlineNum(String onlineNum) {
        this.onlineNum = onlineNum;
    }
}
