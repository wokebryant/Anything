package com.wokebryant.anythingdemo.mvp;

/**
 *  中间层，处理View层的事件，并将Interactor层的ViewModel返回给View层
 */
public class SamplePresenter implements Contract.Presenter {

    private Contract.View mView;
    private Contract.Interactor mInteractor;

    public SamplePresenter() {
        mInteractor = new SampleInteractor();
    }

    @Override
    public void Login(String userId) {
        if (isViewAttached(mView)) {
            return;
        }

        mView.showLoading();
        mInteractor.requestRemoteData(userId, new Contract.RequestDataListener() {
            @Override
            public void requestSuc(SampleModel model) {
                mView.onSuccess(model);
                mView.hideLoading();
            }

            @Override
            public void requestFail() {
                mView.onError();
                mView.hideLoading();
            }
        });
    }

    @Override
    public void attachView(Contract.View view) {
        mView = view;
    }

    @Override
    public void detachView(Contract.View view) {
        mView = null;
    }

    @Override
    public boolean isViewAttached(Contract.View view) {
        return mView == null;
    }

}
