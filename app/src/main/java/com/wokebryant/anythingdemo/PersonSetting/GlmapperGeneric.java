package com.wokebryant.anythingdemo.PersonSetting;

import java.util.ArrayList;
import java.util.List;

public class GlmapperGeneric<T> {

    private T t;

    public void setT(T t) {
        this.t = t;
    }

    public <T extends inner>T getT(T a) {
        return a;
    }

    List<? extends Father> humen;

    List<T> a = new ArrayList<T>();


    public void test(List<? extends Father> list) {

    }

    public <T extends inner> String c (T x){
        T v = x;
        return "";
    }

    public class inner<T extends Father>{

    }

    private <T extends Son> void test(){
        //inner result = arg1;
        //arg2.compareTo(arg1);
        //.....
        //return result;
    }

    private T t(T b) {
        T a = b;
        return a;
    }

    public Class<T> tClass;


}
