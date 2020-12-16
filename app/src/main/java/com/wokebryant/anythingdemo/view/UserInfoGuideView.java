package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wokebryant.anythingdemo.R;

public class UserInfoGuideView extends LinearLayout {

    private FlipperCallBack callBack;

    private View mRootView;

    public UserInfoGuideView(Context context) {
        super(context);
        initView(context);
    }

    public UserInfoGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public UserInfoGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setFlipperCallBack(FlipperCallBack callBack) {
        this.callBack = callBack;
    }

    private void initView(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.lf_mf_view_userinfo_guide, this, true);
        findViewById(R.id.closeBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.setVisibility(GONE);
                callBack.onDelete(false);
            }
        });
        findViewById(R.id.okBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.setVisibility(GONE);
                goNewEdit();
            }
        });
        //if(readShowGuideStatus()) {
        //    mRootView.setVisibility(GONE);
        //} else {
        //    mRootView.setVisibility(VISIBLE);
        //    saveShowGuideStatus(true);
        //}
    }

    private void goNewEdit() {
        //if (!NetWorkUtil.isNetworkConnected(getContext())) {
        //    ToastUtil.showToast(getContext(), getResources().getString(R.string.notice_network_error));
        //    return;
        //}
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("laifeng://personalsetting"));
        //intent.putExtra("intent.user_id_personalpage", UserInfo.getInstance().getUserInfo().getId());
        //getContext().startActivity(intent);
    }

    //public boolean readShowGuideStatus() {
    //    return SPUtils.getBoolean(LFBaseWidget.getApplicationContext(), "show_userinfo_guide", false);
    //}
    //
    //public void saveShowGuideStatus(boolean isVisible) {
    //    SPUtils.saveBoolean(LFBaseWidget.getApplicationContext(), "show_userinfo_guide", isVisible);
    //}

}
