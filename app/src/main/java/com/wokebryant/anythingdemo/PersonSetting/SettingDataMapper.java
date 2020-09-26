package com.wokebryant.anythingdemo.PersonSetting;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.model.SettingModel;

import java.util.Map;

public class SettingDataMapper {

    //获取个人页信息接口
    public static final String PAGE_INFO_GET_API = "mtop.youku.laifeng.base.user.info";
    //更新个人页设置内容接口
    public static final String SETTING_INFO_UPDATE_API = "mtop.youku.laifeng.base.user.info.update";
    //更新录音提示文案接口
    public static final String RECORDER_TIP_CONTENT_API = "";

    public static void GetSettingModel(Map<String, String> params, OnInfoStateListener listener) {

    }

    public static void UpdateReviewInfo(String jsonStr, OnInfoStateListener listener) {

    }

    public static void UpdateUnReviewInfo(String jsonStr, OnInfoStateListener listener) {

    }

    public static void getRecordingTipContent(OnInfoStateListener listener) {

    }

    public interface OnInfoStateListener {

        void onCompleted(String response);

        void onException(String error);
    }


}
