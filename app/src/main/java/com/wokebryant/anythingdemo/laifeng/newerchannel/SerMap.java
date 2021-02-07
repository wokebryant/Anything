package com.wokebryant.anythingdemo.laifeng.newerchannel;

import java.io.Serializable;
import java.util.HashMap;

public class SerMap implements Serializable {
    public HashMap<String,Object> map;
    public SerMap(){

    }

    public HashMap<String, Object> getMap() {
        return map;
    }

    public void setMap(HashMap<String, Object> map) {
        this.map = map;

    }
}
