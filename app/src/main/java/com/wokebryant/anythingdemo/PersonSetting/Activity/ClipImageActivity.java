package com.wokebryant.anythingdemo.PersonSetting.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.PersonSetting.dialog.PhotoPreviewMoreDialog;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.util.ImageCrop.ClipView;
import com.wokebryant.anythingdemo.util.ImageCrop.ClipViewLayout;
import com.wokebryant.anythingdemo.util.UIUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author wb-lj589732
 * 照片预览页面
 */

public class ClipImageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "ClipImageActivity";

    public static final String TYPE = "type";
    public static final String BACK = "back";
    public static final String CANCEL = "cancel";
    public static final String DELETE = "delete";
    public static final String CAMERA = "camera";
    public static final String PHONE = "PHONE";
    public static final int REQ_CLIP_AVATAR = 50;

    private PhotoPreviewMoreDialog mMoreDialog;
    private ClipViewLayout mClipViewLayout;
    private TextView mBtnCancel;
    private TextView mBtnOk;
    private TextView mBtnMore;

    private int cropType;

    public static void goToClipActivity(FragmentActivity activity, String path, int cropTYpe) {
        Intent clipIntent = getClipIntent(activity, path, cropTYpe);
        activity.startActivityForResult(clipIntent, REQ_CLIP_AVATAR);
    }

    @NonNull
    public static Intent getClipIntent(FragmentActivity activity, String path, int cropType) {
        Intent intent = new Intent();
        intent.setClass(activity, ClipImageActivity.class);
        intent.putExtra(TYPE, cropType);
        intent.putExtra("path", path);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lf_photo_preview);
        cropType = getIntent().getIntExtra(TYPE, ClipView.TYPE_SQUARE);
        initView();
        mClipViewLayout.setImageSrc(getIntent().getStringExtra("path"));
    }

    /**
     * 初始化组件
     */
    public void initView() {
        mClipViewLayout = findViewById(R.id.photo_preview_pic_iv);
        mBtnCancel = findViewById(R.id.photo_preview_cancel);
        mBtnOk = findViewById(R.id.photo_preview_save);
        mBtnMore = findViewById(R.id.setting_title_more);

        mBtnCancel.setOnClickListener(this);
        mBtnOk.setOnClickListener(this);
        mBtnMore.setOnClickListener(this);

        mClipViewLayout.setVisibility(View.VISIBLE);
        mClipViewLayout.setClipType(cropType);

        if (cropType == ClipView.TYPE_FULL) {
            mClipViewLayout.setFullHorizontalPadding();
        }

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mClipViewLayout.getLayoutParams();
        if (params != null) {
            params.gravity = Gravity.CENTER_HORIZONTAL;
            if (cropType == ClipView.TYPE_FULL) {
                params.setMargins(0, 0, 0, 0);
                mClipViewLayout.setLayoutParams(params);
            } else {
                params.setMargins(0, UIUtil.dip2px(this,68), 0, UIUtil.dip2px(this,60));
                mClipViewLayout.setLayoutParams(params);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.photo_preview_cancel) {
            cancelImageCrop();
        } else if (id == R.id.photo_preview_save) {
            generateUriAndReturn();
        } else if (id == R.id.setting_title_more) {
            showMoreDialog();
        }
    }

    private void showMoreDialog() {
        try {
            if (mMoreDialog == null) {
                mMoreDialog = PhotoPreviewMoreDialog.newInstance();
                mMoreDialog.setOnDialogClickListener(new PhotoPreviewMoreDialog.OnDialogClickListener() {
                    @Override
                    public void onDelete() {
                        deleteImageCrop();
                    }

                    @Override
                    public void onCamera() {
                        backToImageSelectActivity(CAMERA);
                    }

                    @Override
                    public void onPhone() {
                        backToImageSelectActivity(PHONE);

                    }
                });
            }
            if (!mMoreDialog.isAdded()) {
                FragmentManager manager = getSupportFragmentManager();
                mMoreDialog.show(manager, "PhotoPreviewMoreDialog");
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {
        cancelImageCrop();
    }

    /**
     * 生成Uri并且通过setResult返回给打开的activity
     */
    private void generateUriAndReturn() {
        //调用返回剪切图
        Bitmap zoomedCropBitmap = mClipViewLayout.clip();
        if (zoomedCropBitmap == null) {
            Log.e("android", "zoomedCropBitmap == null");
            return;
        }
        Uri mSaveUri = Uri.fromFile(
            new File(getCacheDir(), "lf_photo_" + cropType + System.currentTimeMillis() + ".jpg"));
        if (mSaveUri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = getContentResolver().openOutputStream(mSaveUri);
                if (outputStream != null) {
                    zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
                }
            } catch (IOException ex) {
                Log.e("android", "Cannot open file: " + mSaveUri, ex);
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Intent intent = new Intent();
            intent.setData(mSaveUri);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void backToImageSelectActivity(String tag) {
        Intent intent = new Intent();
        intent.putExtra("back", tag);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void cancelImageCrop() {
        Intent intent = new Intent();
        intent.putExtra("back", CANCEL);
        intent.putExtra(TYPE, cropType);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void deleteImageCrop() {
        Intent intent = new Intent();
        intent.putExtra("delete", DELETE);
        intent.putExtra(TYPE, cropType);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
