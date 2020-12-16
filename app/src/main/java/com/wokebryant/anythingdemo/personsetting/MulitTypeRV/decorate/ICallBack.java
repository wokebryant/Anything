package com.wokebryant.anythingdemo.personsetting.MulitTypeRV.decorate;

public interface ICallBack<T> {

    void onDelete(int position);

    void onSave(T data);
}
