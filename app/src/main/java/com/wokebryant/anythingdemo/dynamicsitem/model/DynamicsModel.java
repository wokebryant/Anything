package com.wokebryant.anythingdemo.dynamicsitem.model;

import java.io.Serializable;
import java.util.List;

public class DynamicsModel implements Serializable {

    public List<DynamicsItemModel> data;

    public void setData(List<DynamicsItemModel> data) {
        this.data = data;
    }
}
