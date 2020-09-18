package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.model.SettingModel;

import java.util.Map;

public class SettingDataMapper {

    //获取个人页信息接口
    public static final String PAGE_INFO_GET_API = "";
    //更新个人页需审核内容接口
    public static final String REVIEW_INFO_UPDATE_API = "";
    //更新个人页不需审核内容接口
    public static final String UNREVIEW_INFO_UPDATTE_API = "";

    public static void GetSettingModel(Map<String, String> params, OnInfoStateListener listener) {

    }

    public static void UpdateReviewInfo(Map<String, String> params, OnInfoStateListener listener) {

    }

    public static void UpdateUnReviewInfo(Map<String, String> params, OnInfoStateListener listener) {

    }

    public interface OnInfoStateListener {

        void onCompleted(String response);

        void onException(String error);
    }


}
