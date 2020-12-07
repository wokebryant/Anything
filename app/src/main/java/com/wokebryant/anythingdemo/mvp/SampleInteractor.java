package com.wokebryant.anythingdemo.mvp;

/**
 *  数据处理中心，进行网络访问或者本地数据读取，将Model转化为ViewModel回调给Presenter
 */
public class SampleInteractor implements Contract.Interactor, Contract.RequestDataListener{


    @Override
    public void requestRemoteData(String userId, Contract.RequestDataListener requestNetListener) {
        SampleModel model = (SampleModel) SampleMapper.transFormDataToViewModel("");
        if (requestNetListener != null && model != null) {
            requestNetListener.requestSuc(model);
        }
    }

    @Override
    public void requestLocalData(String userId, Contract.RequestDataListener requestNetListener) {

    }

    @Override
    public void requestSuc(SampleModel model) {

    }

    @Override
    public void requestFail() {

    }
}
