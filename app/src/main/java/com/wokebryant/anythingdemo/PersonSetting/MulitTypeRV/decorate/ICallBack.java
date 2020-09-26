package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate;

public interface ICallBack<T> {

    void onDelete(int position);

    void onSave(T data);
}
