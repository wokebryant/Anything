package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.wokebryant.anythingdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wb-lj589732
 *  上下轮播
 */
public class MFViewFlipper extends ViewFlipper {

    private static final String TAG = "MFViewFlipper";
    private static final String GUIDE_API = "mtop.youku.laifeng.base.api.guide.list.get";
    private static final String SHOW_USER_INFO = "1";
    private static final String SHOW_REAL_PERSON = "2";

    private Context mContext;
    private UserInfoGuideView infoView;
    private RealPersonGuideView realPersonView;
    private boolean showCompleteUserInfo;
    private boolean showRealPerson;

    public MFViewFlipper(Context context) {
        this(context, null);
    }

    public MFViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        getShowFlipperView();
        Log.i(TAG, "showView");
    }



    private void getShowFlipperView() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        if (list.size() == 1) {
            if (SHOW_USER_INFO.equals(list.get(0))) {
                showCompleteUserInfo = true;
                showRealPerson = false;
            } else {
                showCompleteUserInfo = false;
                showRealPerson = true;
            }
        } else if (list.size() == 2) {
            showCompleteUserInfo = true;
            showRealPerson = true;
        }
        addItemView();
    }

    private void addItemView() {
        if (showCompleteUserInfo) {
            infoView = new UserInfoGuideView(mContext);
            infoView.setFlipperCallBack(callBack);
            addView(infoView);
            Log.i(TAG, "showOneView");
        }

        if (showRealPerson) {
            realPersonView = new RealPersonGuideView(mContext);
            realPersonView.setFlipperCallBack(callBack);
            addView(realPersonView);
        }

        if (showCompleteUserInfo && showRealPerson) {
            startFlipper();
        }
    }

    private FlipperCallBack callBack = new FlipperCallBack() {
        @Override
        public void onClick() {

        }

        @Override
        public void onDelete(boolean isRealPerson) {
            if (isRealPerson) {
                removeView(realPersonView);
            } else {
                removeView(infoView);
            }
            stopFliper();
        }
    };

    private void startFlipper() {
        Log.i(TAG, "startFilpper");
        setFlipInterval(5000);
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.lf_mf_anim_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.lf_mf_anim_out));
        startFlipping();
    }

    private void stopFliper() {
        setFlipInterval(0);
        setInAnimation(null);
        setOutAnimation(null);
        stopFlipping();
    }

}
