package com.wokebryant.anythingdemo.PersonSetting;

import java.io.Serializable;

public class NormalUpdateModel implements Serializable {

    public String propName;

    public String propValue;

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }
}
