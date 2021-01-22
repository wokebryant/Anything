package com.wokebryant.anythingdemo.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *  View层， 渲染UI
 */

public class SampleFragment extends Fragment implements Contract.View {

    private Contract.Presenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new SamplePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        onLoginBtnClick();
    }

    private void onLoginBtnClick() {
        mPresenter.Login("xxx");
    }

    @Override
    public void onSuccess(SampleModel model) {
        // handle model
    }


    @Override
    public void onError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView(this);
    }

}
