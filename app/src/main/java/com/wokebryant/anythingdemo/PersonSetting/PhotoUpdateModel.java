package com.wokebryant.anythingdemo.PersonSetting;

import java.io.Serializable;
import java.util.List;

/**
 * @author wb-lj589732
 * 更新设置信息
 */
public class PhotoUpdateModel implements Serializable {

    public String propName;

    public List<String> propValue;

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public void setPropValue(List<String> propValue) {
        this.propValue = propValue;
    }
}
