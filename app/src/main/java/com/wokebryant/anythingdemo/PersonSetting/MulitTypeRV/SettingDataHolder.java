package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.model.SettingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-lj589732
 * 个人编辑页数据
 */
public class SettingDataHolder {

    public static SettingModel requestModelData() {
        SettingModel settingModel = new SettingModel();

        return settingModel;
    }

    public static List<? extends BaseSettingItem> getAllItemData(boolean isSelfPage) {
        SettingModel model = requestModelData();
        List<BaseSettingItem> mDataList = new ArrayList<>();
        if (isSelfPage) {
            //照片墙
            List<PhotoItem> photoItems = new ArrayList<>();
            for (int i=0; i<8; i++) {
                PhotoItem photoItem = new PhotoItem("url");
                photoItems.add(photoItem);
            }
            PhotoWallItem photoWallItem = new PhotoWallItem("完成度30%", photoItems);
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
            signItem.setClickable(true);
            //TODO 个性签名编辑框

            //我的标签
            GroupItem tagItem = getGroupItem(SettingConstant.person_setting_tag);
            tagItem.setClickable(true);
            //TODO 标签栏

            mDataList.add(accountItem);
            mDataList.add(nickItem);
            mDataList.add(idItem);
            mDataList.add(realPersonItem);
            mDataList.add(verifiedItem);
            mDataList.add(signItem);
            mDataList.add(tagItem);
        } else {
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
            GroupItem friendItem = getGroupItem(SettingConstant.person_setting_friend_info);
            friendItem.setClickable(false);
            CommonItem emotionalItem = getCommonItem(SettingConstant.person_setting_emotional_state, "单身");
            CommonItem houseItem = getCommonItem(SettingConstant.person_setting_whether_buy_house, "已购房");
            CommonItem carItem = getCommonItem(SettingConstant.person_setting_whether_bug_car, "已购买豪华型");
            CommonItem liveItem = getCommonItem(SettingConstant.person_setting_live_state, "宿舍");
            CommonItem appointmentItem = getCommonItem(SettingConstant.person_setting_accept_appointment, "接受");
            CommonItem smokeItem = getCommonItem(SettingConstant.person_setting_whether_smoke, "否");
            CommonItem drinkItem = getCommonItem(SettingConstant.person_setting_whether_drink, "否");
            CommonItem liveTogether = getCommonItem(SettingConstant.person_setting_cohabitation_before_marriage, "不接受");

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
            mDataList.add(friendItem);
            mDataList.add(emotionalItem);
            mDataList.add(houseItem);
            mDataList.add(carItem);
            mDataList.add(liveItem);
            mDataList.add(appointmentItem);
            mDataList.add(smokeItem);
            mDataList.add(drinkItem);
            mDataList.add(liveTogether);
        }

        if (isSelfPage) {
            //交友设置
            GroupItem friendItem = getGroupItem(SettingConstant.person_setting_friend_set);
            CommonItem tollNoticeItem = getCommonItem(SettingConstant.person_setting_chat_toll_notice, "否");
            mDataList.add(friendItem);
            mDataList.add(tollNoticeItem);
        }

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


}
