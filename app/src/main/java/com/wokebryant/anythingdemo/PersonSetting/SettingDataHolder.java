package com.wokebryant.anythingdemo.PersonSetting;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInActivityItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInFragmentItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.model.SettingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-lj589732
 * 个人编辑页数据
 */
public class SettingDataHolder {

    public static final String TEST_URL = "";

    public static SettingModel requestModelData() {
        SettingModel settingModel = new SettingModel();

        return settingModel;
    }

    public static List<? extends BaseSettingItem> getAllItemData(boolean isSelfPage) {
        SettingModel model = requestModelData();
        List<BaseSettingItem> mDataList = new ArrayList<>();
        if (isSelfPage) {
            //照片墙
            PhotoWallItem photoWallItem = new PhotoWallItem("完成度30%", mockPhotoWallItemList());
            mDataList.add(photoWallItem);

            //录音
            RecorderItem recorderItem = new RecorderItem(SettingConstant.TEST_RECORDER_URL, "9s");
            mDataList.add(recorderItem);
            //账号信息
            GroupItem accountItem = getGroupItem(SettingConstant.person_setting_account_info);
            CommonItem nickItem = getCommonItem(SettingConstant.person_setting_nick, "你吼那么大声干嘛");
            CommonItem idItem = getCommonItem(SettingConstant.person_setting_id, "1747105");
            idItem.setClickable(false);
            CommonItem realPersonItem = getCommonItem(SettingConstant.person_setting_real_person_auth, "未认证");
            CommonItem verifiedItem = getCommonItem(SettingConstant.person_setting_verified, "未认证");

            //个性签名
            GroupItem signItem = getGroupItem(SettingConstant.person_setting_sign);
            signItem.setClickable(false);
            CommonItem sign2Item = getCommonItem(SettingConstant.person_setting_sign_content, null);

            //我的标签
            GroupItem tagItem = getGroupItem(SettingConstant.person_setting_tag);
            tagItem.setClickable(true);

            TagInActivityItem tag2Item = new TagInActivityItem(mockSelectedTagList());


            mDataList.add(accountItem);
            mDataList.add(nickItem);
            mDataList.add(idItem);
            mDataList.add(realPersonItem);
            mDataList.add(verifiedItem);
            mDataList.add(signItem);
            mDataList.add(sign2Item);
            mDataList.add(tagItem);
            mDataList.add(tag2Item);


            //动态item 测试用

        }
        //else {
            //基础资料
            GroupItem baseInfoItem = getGroupItem(SettingConstant.person_setting_base_info);
            baseInfoItem.setClickable(false);
            CommonItem sexItem = getCommonItem(SettingConstant.person_setting_sex, "女");
            CommonItem ageItem = getCommonItem(SettingConstant.person_setting_age, "25");
            CommonItem constellationItem = getCommonItem(SettingConstant.person_setting_constellation, "双鱼座");
            CommonItem heightItem = getCommonItem(SettingConstant.person_setting_height, "162");
            CommonItem weightItem = getCommonItem(SettingConstant.person_setting_weight, "42kg");
            CommonItem localItem = getCommonItem(SettingConstant.person_setting_local, "杭州");
            CommonItem homeItem = getCommonItem(SettingConstant.person_setting_home, "长春");
            CommonItem eduItem = getCommonItem(SettingConstant.person_setting_education, "硕士");
            CommonItem jobItem = getCommonItem(SettingConstant.person_setting_job, "人事");
            CommonItem incomeItem = getCommonItem(SettingConstant.person_setting_annual_income, "10～20万");

            //交友信息
            CommonItem emotionalItem = getCommonItem(SettingConstant.person_setting_emotional_state, "单身");

            mDataList.add(baseInfoItem);
            mDataList.add(sexItem);
            mDataList.add(ageItem);
            mDataList.add(constellationItem);
            mDataList.add(heightItem);
            mDataList.add(weightItem);
            mDataList.add(localItem);
            mDataList.add(homeItem);
            mDataList.add(eduItem);
            mDataList.add(jobItem);
            mDataList.add(incomeItem);
            mDataList.add(emotionalItem);
        //}

        return mDataList;
    }

    private static CommonItem getCommonItem(String sub, String desc) {
        CommonItem commonItem = new CommonItem(sub, desc);
        return commonItem;
    }

    private static GroupItem getGroupItem(String sub) {
        GroupItem groupItem = new GroupItem(sub);
        return groupItem;
    }

    public static List<String> mockSelectedTagList() {
        List<String> list = new ArrayList<>();
        list.add("臊皮");
        list.add("古灵精怪");
        list.add("喜欢周杰伦");
        return list;
    }

    public static List<String> mockAllTagList() {
        List<String> list = new ArrayList<>();
        for (int i=0; i<30; i++) {
            list.add("测试标签" + i);
        }
        list.addAll(mockSelectedTagList());

        return list;
    }
    public static List<String> mockSelectedItemList() {
        List<String> mDataList = new ArrayList<>();
        mDataList.add("警察");
        mDataList.add("医生");
        mDataList.add("学生");
        mDataList.add("教师");
        mDataList.add("工程师");
        mDataList.add("程序员");
        mDataList.add("自由职业");
        return mDataList;
    }

    public static List<PhotoItem> mockPhotoWallItemList() {
        List<PhotoItem> photoItems = new ArrayList<>();
        for (int i=0; i<8; i++) {
            PhotoItem photoItem = new PhotoItem("url");
            photoItems.add(photoItem);
        }
        return photoItems;
    }

    public static List<String> transformTagItemToString(List<TagInFragmentItem> data) {
        List<String> list = new ArrayList<>();
        for (TagInFragmentItem item : data) {
            list.add(item.tagName);
        }
        return list;
    }

    public static List<TagInFragmentItem> transformStringToTagItem(List<String> data) {
        List<TagInFragmentItem> list = new ArrayList<>();
        for (String s : data) {
            TagInFragmentItem item = new TagInFragmentItem(s);
            list.add(item);
        }

        return list;
    }



}
