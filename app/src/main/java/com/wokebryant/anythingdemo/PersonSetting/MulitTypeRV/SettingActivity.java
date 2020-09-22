package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.GroupItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.model.SettingModel;
import com.wokebryant.anythingdemo.PersonSetting.dialog.RecorderDialog;
import com.wokebryant.anythingdemo.PersonSetting.dialog.SettingItemSelectDialog;
import com.wokebryant.anythingdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-lj589732
 * 个人页编辑界面
 */
public class SettingActivity extends AppCompatActivity {

    private PhotoPreviewFragment mPhotoPreviewFragment;
    private SettingItemSelectDialog mSelectDialog;
    private RecorderDialog mRecorderDialog;
    private FrameLayout mFragmentContainer;
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

    public void launchFragment(boolean isAvatar, int position) {
        showToast("这是第" + position + "图片");
        if (mPhotoPreviewFragment == null) {
            mPhotoPreviewFragment = PhotoPreviewFragment.newInstance(isAvatar);
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.setting_container, mPhotoPreviewFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public void closePreviewFragment() {
        if (getSupportFragmentManager() != null) {
            getSupportFragmentManager().popBackStack();
        }
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
        mFragmentContainer = findViewById(R.id.setting_container);

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

    private void setData() {
        mDataList = (List<BaseSettingItem>)SettingDataHolder.getAllItemData(isSelfPage);
        if (mDataList != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mMultiAdapter = new MultiAdapter(mDataList, isSelfPage);
            mMultiAdapter.setOnItemClickListener(onItemClickListener);
            mSettingRv.setLayoutManager(layoutManager);
            mSettingRv.setAdapter(mMultiAdapter);
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.setting_back_iv) {
                exitSettingPage();
            } else if (v.getId() == R.id.setting_save_btn) {
                saveSettingData();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private MultiAdapter.OnItemClickListener onItemClickListener = new MultiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String subType, int position) {
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

    private void exitSettingPage() {
        if (!SettingActivity.this.isFinishing()) {
            SettingActivity.this.finish();
        }
    }

    private void saveSettingData(){

    }

    public void playRecorder() {

    }

    public void showRecorderDialog() {
        showToast("展示录音弹窗");
        try {
            if (mRecorderDialog == null) {
                mRecorderDialog = RecorderDialog.newInstance();
            }
            if (!mRecorderDialog.isAdded()) {
                FragmentManager manager = getSupportFragmentManager();
                mRecorderDialog.show(manager, "RecorderDialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showSelectDialog(String itemType) {
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
