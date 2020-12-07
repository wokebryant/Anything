package com.wokebryant.anythingdemo.mvp;

/**
 *  管理View, SamplePresenter, interactor 接口
 */
public interface Contract {

    interface View extends BaseView {

        void onSuccess(SampleModel model);

    }

    interface Presenter extends BasePresenter<View> {

        void Login(String userId);
    }

    interface Interactor {

        void requestRemoteData(String userId, RequestDataListener requestNetListener);

        void requestLocalData(String userId, RequestDataListener requestNetListener);

    }

    interface RequestDataListener {

        void requestSuc(SampleModel model);

        void requestFail();

    }

}
