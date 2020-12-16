package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.interfaces.FlipperCallBack;

/**
 * @author wb-lj589732
 *  真人认证引导条
 */
public class RealPersonGuideView extends LinearLayout{

    private FlipperCallBack callBack;

    private View mRootView;

    public RealPersonGuideView(Context context) {
        this(context, null);
    }

    public RealPersonGuideView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public RealPersonGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRootView = LayoutInflater.from(context).inflate(R.layout.lf_mf_view_real_person_certification_guide, this, true);
        findViewById(R.id.closeBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.setVisibility(GONE);
                callBack.onDelete(true);
            }
        });
        findViewById(R.id.okBtn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootView.setVisibility(GONE);
                //goRealPersonCertification();
            }
        });
        //if(readShowGuideStatus()) {
        //    mRootView.setVisibility(GONE);
        //} else {
        //    mRootView.setVisibility(VISIBLE);
        //    saveShowGuideStatus(true);
        //}
    }

    public void setFlipperCallBack(FlipperCallBack callBack) {
        this.callBack = callBack;
    }

    //private void goRealPersonCertification() {
    //    if (!NetWorkUtil.isNetworkConnected(getContext())) {
    //        ToastUtil.showToast(getContext(), getResources().getString(R.string.notice_network_error));
    //        return;
    //    }
    //    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("laifeng://realperson"));
    //    intent.putExtra("intent.fragment_tag", "certification_fragment");
    //    intent.putExtra("intent.user_id_realperson", UserInfo.getInstance().getUserInfo().getId());
    //    getContext().startActivity(intent);
    //}
    //
    //public boolean readShowGuideStatus() {
    //    return SPUtils.getBoolean(LFBaseWidget.getApplicationContext(), "show_userinfo_guide", false);
    //}
    //
    //public void saveShowGuideStatus(boolean isVisible) {
    //    SPUtils.saveBoolean(LFBaseWidget.getApplicationContext(), "show_userinfo_guide", isVisible);
    //}

}
