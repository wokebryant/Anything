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

import com.wokebryant.anythingdemo.R;

public class PhotoPreviewFragment extends Fragment {

    private Activity mActivity;
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

    }





}
