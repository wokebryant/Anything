package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.model.SettingModel;
import com.wokebryant.anythingdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-lj589732
 */
public class SettingActivity extends Activity {

    private SettingItemSelectDialog mSelectDialog;
    private RecyclerView mSettingRv;
    private ImageView mBackIv;
    private TextView mTitleTv;
    private Button mSaveBtn;

    private MultiAdapter mMultiAdapter;
    private List<BaseSettingItem> mDataList;

    private boolean isSelfPage;

    public static void launch(Activity activity, boolean isSelfPage) {
        Intent intent = new Intent();
        intent.setClass(activity, SettingActivity.class);
        intent.putExtra("isSelfPage", isSelfPage);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lf_person_setting_layout);
        isSelfPage = getIntent().getBooleanExtra("isSelfPage", false);
        initView();
        setData();
    }

    private void initView() {
        mBackIv = findViewById(R.id.setting_back_iv);
        mSaveBtn = findViewById(R.id.setting_save_btn);
        mTitleTv = findViewById(R.id.setting_title_tv);
        mSettingRv = findViewById(R.id.setting_content_rv);

        if (isSelfPage) {
            mSaveBtn.setVisibility(View.VISIBLE);
            mTitleTv.setText("我的资料");
        } else {
            mSaveBtn.setVisibility(View.GONE);
            mTitleTv.setText("我的昵称");
        }

        mBackIv.setOnClickListener(mOnClickListener);
        mSaveBtn.setOnClickListener(mOnClickListener);
    }

    public SettingModel requestModelData() {
        SettingModel settingModel = new SettingModel();

        return settingModel;
    }

    private void setData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mMultiAdapter = new MultiAdapter(getAllItemData(), isSelfPage);
        mMultiAdapter.setOnItemClickListener(onItemClickListener);
        mSettingRv.setLayoutManager(layoutManager);
        mSettingRv.setAdapter(mMultiAdapter);
    }

    private List<BaseSettingItem> getAllItemData() {
        SettingModel model = requestModelData();
        mDataList = new ArrayList<>();
        if (isSelfPage) {
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

    private CommonItem getCommonItem(String sub, String desc) {
        CommonItem commonItem = new CommonItem(sub, desc);
        return commonItem;
    }

    private GroupItem getGroupItem(String sub) {
        GroupItem groupItem = new GroupItem(sub);
        return groupItem;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.setting_back_iv) {

            } else if (v.getId() == R.id.setting_save_btn) {

            }
        }
    };

    private MultiAdapter.OnItemClickListener onItemClickListener = new MultiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String subType) {
            if (!isSelfPage) {
                return;
            }
            if (SettingConstant.person_setting_nick.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_real_person_auth.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_verified.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_sign.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_tag.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_sex.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_age.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_constellation.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_height.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_weight.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_local.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_home.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_education.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_job.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_annual_income.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_emotional_state.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_whether_buy_house.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_whether_bug_car.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_live_state.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_accept_appointment.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_whether_smoke.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_whether_drink.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_cohabitation_before_marriage.equals(subType)) {
                showToast(subType);
            } else if (SettingConstant.person_setting_chat_toll_notice.equals(subType)) {
                showToast(subType);
            }

          }
    };

    private void exitPage() {

    }

    private void saveSetting(){

    }

    private void showSelectDialog() {
        if (mSelectDialog == null) {
            Object o = new Object();
            mSelectDialog = (SettingItemSelectDialog)SettingItemSelectDialog.newInstance(o);
        }
    }

    private void showToast(String content) {
        String tip = "点击了 " + content;
        Toast.makeText(SettingActivity.this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
