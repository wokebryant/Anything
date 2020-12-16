package com.wokebryant.anythingdemo.laifeng.personsetting;

import java.io.Serializable;

public class SettingUpdateModel<T> implements Serializable {

    public String propName;

    public T propValue;

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public void setPropValue(T propValue) {
        this.propValue = propValue;
    }
}
