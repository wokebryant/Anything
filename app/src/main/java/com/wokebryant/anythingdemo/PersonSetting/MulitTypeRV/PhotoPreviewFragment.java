package com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 * 照片预览页面
 */
public class PhotoPreviewFragment extends Fragment {

    private Activity mActivity;
    private ImageView mBackIv;
    private ImageView mMoreIv;
    private ImageView mPreviewIv;

    private boolean isAvatarPreview;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
        Bundle bundle = getArguments();
        if (getArguments() != null) {
            isAvatarPreview = bundle.getBoolean("isAvatar");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lf_photo_preview, container, false);
        initView(view);
        return view;
    }

    public static PhotoPreviewFragment newInstance(boolean isAvatarPreview) {
        PhotoPreviewFragment fragment = new PhotoPreviewFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isAvatar", isAvatarPreview);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void initView(View view) {
        mBackIv = view.findViewById(R.id.photo_preview_back_iv);
        mMoreIv = view.findViewById(R.id.photo_preview_more_iv);
        mPreviewIv = view.findViewById(R.id.photo_preview_pic_iv);

        mBackIv.setOnClickListener(onClickListener);
        mMoreIv.setOnClickListener(onClickListener);
        mPreviewIv.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.photo_preview_back_iv) {
                if (mActivity instanceof SettingActivity && !mActivity.isFinishing()) {
                    ((SettingActivity)mActivity).closePreviewFragment();
                }

            } else if (v.getId() == R.id.photo_preview_more_iv) {

            }
        }
    };









}
