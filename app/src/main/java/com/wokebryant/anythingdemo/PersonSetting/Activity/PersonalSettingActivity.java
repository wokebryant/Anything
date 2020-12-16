package com.wokebryant.anythingdemo.PersonSetting.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.wokebryant.anythingdemo.PersonSetting.DatePicker.CustomSelectedPicker;
import com.wokebryant.anythingdemo.PersonSetting.DatePicker.DateFormatUtils;
import com.wokebryant.anythingdemo.PersonSetting.Fragment.SettingTagFragment;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.adapter.MultiAdapter;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.decorate.ICallBack;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.BaseSettingItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.CommonItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.PhotoWallItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.RecorderItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInActivityItem;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.item.TagInFragmentItem;
import com.wokebryant.anythingdemo.PersonSetting.SettingUpdateModel;
import com.wokebryant.anythingdemo.PersonSetting.SettingConstant;
import com.wokebryant.anythingdemo.PersonSetting.SettingDataHolder;
import com.wokebryant.anythingdemo.PersonSetting.SettingDataMapper;
import com.wokebryant.anythingdemo.PersonSetting.dialog.RecorderDialog;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.dialog.RealManCertificationDialog;
import com.wokebryant.anythingdemo.dialog.RealPersonBottomDialog;
import com.wokebryant.anythingdemo.util.BrightnessTools;
import com.wokebryant.anythingdemo.util.ImageCrop.ClipView;
import com.wokebryant.anythingdemo.util.ImageCrop.FileUtil;
import com.wokebryant.anythingdemo.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import static com.wokebryant.anythingdemo.PersonSetting.Activity.ClipImageActivity.PHONE;
import static com.wokebryant.anythingdemo.PersonSetting.Activity.ClipImageActivity.REQ_CLIP_AVATAR;

/**
 * @author wb-lj589732
 * 个人页编辑界面
 */
public class PersonalSettingActivity extends AppCompatActivity implements ICallBack {

    private static final String TAG = "PersonalSettingActivity";

    private static final int PHOTO_FRAGMENT = 0;
    private static final int TAG_FRAGMENT = 1;

    //请求相册
    private static final int REQUEST_PICK = 101;

    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;

    private SettingTagFragment mSettingTagFragment;
        private RecorderDialog mRecorderDialog;
    private FrameLayout mFragmentContainer;
    private RecyclerView mSettingRv;
    private ImageView mBackIv;
    private TextView mTitleTv;

    private MultiAdapter mMultiAdapter;
    private List<BaseSettingItem> mDataList;

    private CustomSelectedPicker mSelectedItemPicker;


    private List<String> mOssPhotoList;     //云端的照片
    private List<PhotoItem> mAllPhotoList;  //8张照片，包含空照片
    private String mOriginUrl;             //相册选择返回的原始图片Url
    private String mUploadUrl;              //添加或更新到OSS拿到的照片
    private int photoWallPosition;          //点击照片墙的位置

    private boolean isSelfPage;

    public static void launch(Activity activity, boolean isSelfPage) {
        Intent intent = new Intent();
        intent.setClass(activity, PersonalSettingActivity.class);
        intent.putExtra("isSelfPage", isSelfPage);
        activity.startActivity(intent);
    }

    /**
     * 打开相册或裁剪界面
     * @param photoUrl
     * @param position
     */
    public void launchPhotoActivity(String photoUrl,int position) {
        photoWallPosition = position;
        mOriginUrl = photoUrl;
        requestStoragePermission();
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(PersonalSettingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(PersonalSettingActivity.this,
                new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                READ_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            editPhoto();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                editPhoto();
            }
        }
    }

    private void editPhoto() {
        if (PhotoItem.defaultUrl.equals(mOriginUrl)) {
            gotoClipActivity(mOriginUrl);
        } else {
            gotoSelectCover(PHONE);
        }
    }

    /**
     * 打开标签页
     */
    public void launchFragment() {
        mSettingTagFragment = SettingTagFragment.newInstance(SettingDataHolder.mockSelectedTagList()
            , SettingDataHolder.mockAllTagList());
        initFragment(mSettingTagFragment, SettingConstant.TAG_FRAGMENT_TAG);
    }

    public void initFragment(Fragment fragment, String tag) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.setting_container, fragment, tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    public void closeFragment() {
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
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.StatusBarLightMode(this);
    }

    private void initView() {
        mBackIv = findViewById(R.id.setting_back_iv);
        mTitleTv = findViewById(R.id.setting_title_tv);
        mSettingRv = findViewById(R.id.setting_content_rv);
        mFragmentContainer = findViewById(R.id.setting_container);

        if (isSelfPage) {
            mTitleTv.setText("我的资料");
        } else {
            mTitleTv.setText("我的昵称");
        }

        mBackIv.setOnClickListener(mOnClickListener);
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
            }
        }
    };

    private void exitSettingPage () {
        if (!PersonalSettingActivity.this.isFinishing()) {
            PersonalSettingActivity.this.finish();
        }
    }

    private MultiAdapter.OnItemClickListener onItemClickListener = new MultiAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String subType, int position) {
            if (!isSelfPage) {
                return;
            }
            //昵称
            if (SettingConstant.person_setting_nick.equals(subType)) {
                //TODO 昵称修改
                //真人认证
            } else if (SettingConstant.person_setting_real_person_auth.equals(subType)) {
                showToast(subType);
                //实名认证
            } else if (SettingConstant.person_setting_verified.equals(subType)) {
                showToast(subType);
                //签名
            } else if (SettingConstant.person_setting_sign_content.equals(subType)) {
                //TODO 签名修改
                //标签
            } else if (SettingConstant.person_setting_tag.equals(subType)) {
                launchFragment();
                //其它
            } else {
                if (SettingConstant.person_setting_account_info.equals(subType)
                        || SettingConstant.person_setting_sign.equals(subType)
                        || SettingConstant.person_setting_tag.equals(subType)
                        || SettingConstant.person_setting_base_info.equals(subType)) {
                    return;
                }
                showSelectDialog(position);
            }
        }
    };

    /**
     * 打开录音弹窗
     */
    public void showRecorderDialog () {
        try {
            if (mRecorderDialog == null) {
                mRecorderDialog = RecorderDialog.newInstance();
                mRecorderDialog.setOnSaveRecorderListener(new RecorderDialog.OnSaveRecorderListener() {
                    @Override
                    public void onSave(String playUrl) {
                        if (mDataList != null && mMultiAdapter != null) {
                            RecorderItem item = new RecorderItem(playUrl, "3s");
                            mDataList.set(1, item);
                            mMultiAdapter.notifyDataSetChanged();
                            updateUnReviewInfoToServer("voice", playUrl);
                        }
                    }
                });
            }
            if (!mRecorderDialog.isAdded()) {
                FragmentManager manager = getSupportFragmentManager();
                mRecorderDialog.show(manager, "RecorderDialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RealManCertificationDialog mRealManDialog;

    /**
     * 真人认证弹窗
     */
    public void showRealManDialog() {
        try{
            if (mRealManDialog == null) {
                mRealManDialog = RealManCertificationDialog.newInstance();
                mRealManDialog.setOnDialogClickListener(new RealManCertificationDialog.OnDialogClickListener() {
                    @Override
                    public void onGotoAlbum() {
                        //backToImageSelectActivity(PHONE);
                    }
                });
            }
            if (!mRealManDialog.isAdded()) {
                FragmentManager manager = getSupportFragmentManager();
                mRealManDialog.show(manager, "RealManCertificationDialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void showRealPersonDialog() {
        try {
            RealPersonBottomDialog mRealPersonDialog;
            mRealPersonDialog = RealPersonBottomDialog.newInstance(""
                , "哈哈");
            mRealPersonDialog.setOnDialogClickListener(new RealPersonBottomDialog.OnDialogClickListener() {
                @Override
                public void onGotoCertification() {
                }
            });
            if (!mRealPersonDialog.isAdded()) {
                FragmentManager manager = getSupportFragmentManager();
                mRealPersonDialog.show(manager, "RealPersonBottomDialog");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除录音
     */
    public void deleteRecorder() {
        updateUnReviewInfoToServer("voice", null);
    }

    /**
     * 打开通用信息选择器
     * @param position
     */
    private void showSelectDialog ( final int position){
        final List<String> selectedList = SettingDataHolder.mockSelectedItemList();
        if (isDateSelected(position)) {
            long beginTimestamp = DateFormatUtils.str2Long("1900-01-01", false);
            final long endTimestamp = System.currentTimeMillis();
            mSelectedItemPicker = new CustomSelectedPicker(this, new CustomSelectedPicker.Callback() {
                @Override
                public void onTimeSelected(long timestamp) {
                    if (timestamp != 0) {
                        updateViewAfterSelected(position, DateFormatUtils.long2Str(timestamp, false));
                    } else {
                        updateViewAfterSelected(position, DateFormatUtils.long2Str(endTimestamp, false));
                    }

                }
            }, beginTimestamp, endTimestamp);
            // 不显示时和分
            mSelectedItemPicker.setCanShowPreciseTime(false);
            // 不允许循环滚动
            mSelectedItemPicker.setScrollLoop(false);
            // 不允许滚动动画
            mSelectedItemPicker.setCanShowAnim(false);
            mSelectedItemPicker.show("2010-08-08");
        } else {
            final String currentString = "人事";
            mSelectedItemPicker = new CustomSelectedPicker(this, new CustomSelectedPicker.StringCallback() {
                @Override
                public void onItemSelected(String selected) {
                    //为null代表没有滑动选择器
                    if (selected != null) {
                        updateViewAfterSelected(position, selected);
                    } else {
                        if (currentString == null) {
                            updateViewAfterSelected(position, selectedList.get(selectedList.size() / 2));
                        } else {
                            updateViewAfterSelected(position, currentString);
                        }
                    }
                }
            });

            mSelectedItemPicker.setScrollLoop(false);
            //仅设置一个选择器
            mSelectedItemPicker.setOnePickerView(true);
            mSelectedItemPicker.show(selectedList, getSelectedPosition(currentString, selectedList));
        }
    }

    private void updateViewAfterSelected ( int position, String selected){
        if (position >= mDataList.size() || mMultiAdapter == null) {
            return;
        }
        //性别
        if (12 == position) {
            updateUnReviewInfoToServer("gender", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_sex, selected));
            //年龄
        } else if (13 == position) {
            updateUnReviewInfoToServer("birthday", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_age, selected));
            // 星座
        } else if (14 == position) {

            mDataList.set(position, new CommonItem(SettingConstant.person_setting_constellation, selected));
            // 身高
        } else if (15 == position) {
            updateUnReviewInfoToServer("height", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_height, selected));
            //体重
        } else if (16 == position) {
            updateUnReviewInfoToServer("weight", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_weight, selected));
            //所在地
        } else if (17 == position) {
            updateUnReviewInfoToServer("cityCode", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_local, selected));
            //家乡
        } else if (18 == position) {
            updateUnReviewInfoToServer("hometownCode", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_home, selected));
            //学历
        } else if (19 == position) {
            updateUnReviewInfoToServer("education", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_education, selected));
            //从事职业
        } else if (20 == position) {
            updateUnReviewInfoToServer("profession", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_job, selected));
            //年收入
        } else if (21 == position) {
            updateUnReviewInfoToServer("annualIncome", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_annual_income, selected));
            //情感状态
        } else if (22 == position) {
            updateUnReviewInfoToServer("marital", selected);
            mDataList.set(position, new CommonItem(SettingConstant.person_setting_emotional_state, selected));
        }
        mMultiAdapter.notifyDataSetChanged();
    }

    private boolean isDateSelected (int position){
        boolean isDateSelected;
        if (13 == position) {
            isDateSelected = true;
        } else {
            isDateSelected = false;
        }
        return isDateSelected;
    }

    /**
     * 设置选择弹窗打开时当前item的显示位置
     * @return
     */
    private int getSelectedPosition (String currentString, List <String> dataList){
        int position = dataList.size() / 2;
        for (String s : dataList) {
            if (currentString.equals(s)) {
                position = dataList.indexOf(s);
                break;
            }
        }
        return position;
    }

    private void showToast (String content){
        String tip = "点击了 " + content;
        Toast.makeText(PersonalSettingActivity.this, tip, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete ( int position){

    }

    /**
     * 标签页回调
     *
     */
    @Override
    public void onSave (Object data){
        if (data instanceof Uri) {

        } else {
            List<TagInFragmentItem> itemList = (List<TagInFragmentItem>)data;
            TagInActivityItem inActivityItem;
            if (itemList == null || itemList.size() == 0) {
                List<String> list = new ArrayList<>();
                list.add(SettingConstant.person_setting_tag_none);
                inActivityItem = new TagInActivityItem(list);
            } else {
                inActivityItem = new TagInActivityItem(SettingDataHolder.transformTagItemToString(itemList));
            }

            mDataList.set(10, inActivityItem);
            mMultiAdapter.notifyDataSetChanged();

            updateUnReviewInfoToServer("tag", SettingDataHolder.transformTagItemToString(itemList));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return;
        }
        //相册选择或相机拍照返回
        if (requestCode == REQUEST_PICK) {
            gotoClipActivity(SettingDataHolder.TEST_URL);
            //  裁剪完毕返回
        } else if (requestCode == REQ_CLIP_AVATAR) {
            String backTag = intent.getStringExtra("back");
            int position = photoWallPosition;
            if (backTag != null) {
                //进入照片选择
                if (backTag.equals(PHONE) || backTag.equals(ClipImageActivity.CAMERA)) {
                    gotoSelectCover(backTag);
                    return;
                    //删除照片
                } else if (backTag.equals(ClipImageActivity.DELETE)) {
                    deleteCropImage(position);
                }
            }
            final Uri uri = intent.getData();
            if (uri == null) {
                return;
            }
            String cropImagePath = FileUtil.getRealFilePathFromUri(getApplicationContext(), uri);
            long fileSize = FileUtil.getFileSize(cropImagePath);
            Log.i(TAG, "onActivityResult: fileSize =" + fileSize * 1.0f / 1024);
            if (cropImagePath != null) {
                uploadCropImageToOss(cropImagePath, position);
            }
        }
    }

    /**
     * 上传图片到oss
     */
    private void uploadCropImageToOss(String cropUrl, int position) {
        //TODO


        updatePhotoWall(mUploadUrl, position);
    }

    /**
     * 选择完照片后更新照片墙
     * @param mUploadUrl
     * @param position
     */
    private void updatePhotoWall(String mUploadUrl, int position) {
        if (mDataList != null && mMultiAdapter != null) {

            if (position >= mOssPhotoList.size()) {
                //添加照片
                mOssPhotoList.add(mUploadUrl);
            } else {
                //更新照片
                mOssPhotoList.set(position, mUploadUrl);
            }
            refreshPhotoList(mOssPhotoList);
            PhotoWallItem item = new PhotoWallItem("完成度30%", mAllPhotoList);
            mDataList.set(0, item);
            mMultiAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 删除照片并更新照片墙
     */
    private void deleteCropImage(int position) {
        if (mOssPhotoList != null && mOssPhotoList.size() > position) {
            mOssPhotoList.remove(position);
            refreshPhotoList(mOssPhotoList);
            PhotoWallItem item = new PhotoWallItem("完成度20%", mAllPhotoList);
            mDataList.set(0, item);
            mMultiAdapter.notifyDataSetChanged();
        }
    }



    /**
     * 刷新一遍照片墙数据列表
     * @param ossList
     */
    private void refreshPhotoList(List<String> ossList) {
        if (mAllPhotoList != null) {
            if (ossList != null && !ossList.isEmpty()) {
                mAllPhotoList.clear();
                for (String s : ossList) {
                    PhotoItem item = new PhotoItem(s);
                    mAllPhotoList.add(item);
                }
                int size = mAllPhotoList.size();
                if (size < 8) {
                    for (int i = size; i < 8; i++) {
                        mAllPhotoList.add(new PhotoItem(""));
                    }
                }
            }
        }
    }

    /**
     * 打开照片裁剪界面
     */
    public void gotoClipActivity (String path){
        if (path == null) {
            return;
        }
        ClipImageActivity.goToClipActivity(this, path, ClipView.TYPE_SQUARE);
    }

    /**
     * 跳转到来疯图库
     */
    private void gotoSelectCover (String tag){
        //Intent intent = new Intent(this, ImageSelectorActivity.class);
        //intent.putExtra("camera", tag);
        //startActivityForResult(intent, REQUEST_PICK);
    }

    /**
     * 上传拿到的oss图片地址到来疯server
     */
    public void savePhotoSettingData () {
        if (mOssPhotoList != null && mOssPhotoList.isEmpty()) {
            List<SettingUpdateModel> list = new ArrayList<>();
            SettingUpdateModel model = new SettingUpdateModel();
            model.setPropName("photoAlbum");
            model.setPropValue(mOssPhotoList);
            list.add(model);
            String jsonStr = JSON.toJSONString(list);
            SettingDataMapper.UpdateReviewInfo(jsonStr, onInfoStateListener);
        }
    }

    /**
     * 更新其它设置项目到来疯server
     * @param name
     * @param value
     */
    public <T> void updateUnReviewInfoToServer(String name, T value) {
        List<SettingUpdateModel> list = new ArrayList<>();
        SettingUpdateModel model = new SettingUpdateModel();
        model.setPropName(name);
        model.setPropValue(value);
        list.add(model);
        String jsonStr = JSON.toJSONString(list);
        SettingDataMapper.UpdateUnReviewInfo(jsonStr, onInfoStateListener);
    }

    private SettingDataMapper.OnInfoStateListener onInfoStateListener = new SettingDataMapper.OnInfoStateListener() {
        @Override
        public void onCompleted(String response) {

        }

        @Override
        public void onException(String error) {

        }
    };

    private int preview_bright;
    private static final int MAX_BRIGHT = 255;

    @Override
    protected void onResume() {
        super.onResume();
        setScreenMaxBright();
    }

    /**
     *  设置屏幕最高亮度
     */
    private void setScreenMaxBright() {
        if (BrightnessTools.isAutoBrightness(getContentResolver())) {
            BrightnessTools.stopAutoBrightness(this);
        }
        preview_bright = BrightnessTools.getScreenBrightness(this);
        if (MAX_BRIGHT > preview_bright) {
            BrightnessTools.setBrightness(this, MAX_BRIGHT);
        }
    }

    /**
     *  重置亮度
     */
    private void resetScreenBright() {
        if (BrightnessTools.isAutoBrightness(getContentResolver())) {
            BrightnessTools.startAutoBrightness(this);
        }
        if (preview_bright != 0) {
            BrightnessTools.setBrightness(this, preview_bright);
        }
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
    }


}
