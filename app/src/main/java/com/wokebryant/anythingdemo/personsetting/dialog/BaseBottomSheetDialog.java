package com.wokebryant.anythingdemo.personsetting.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author wb-lj589732
 */
public class BaseBottomSheetDialog extends BottomSheetDialogFragment {

    private BottomSheetBehavior<View> mBehavior;
    private View mRootView;
    private boolean supportPullToClose;

    public BaseBottomSheetDialog() {

    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
        = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                //设置为收缩状态
                mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    /**
     * 是否支持下拉关闭dialog
     */
    public void supportPullDownToClose(View rootView, boolean supportPullToClose) {
        this.supportPullToClose = supportPullToClose;
        if (supportPullToClose) {
            mRootView = rootView;
            mBehavior = BottomSheetBehavior.from((View)mRootView.getParent());
            mBehavior.setSkipCollapsed(true);
            mBehavior.setHideable(true);
            mBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (supportPullToClose) {
            mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (supportPullToClose) {
            if (mRootView.getParent() != null) {
                ((ViewGroup)(mRootView.getParent())).removeView(mRootView);
            }
        }
    }

}
