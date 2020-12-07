package com.wokebryant.anythingdemo.mvp;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView(V view);

    boolean isViewAttached(V view);

}
