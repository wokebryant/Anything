package com.wokebryant.anythingdemo.PersonSetting.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class RecorderDialog extends BaseBottomSheetDialog{

    private ImageView mCloseIv;
    private TextView mContentTv;
    private TextView mChangeTv;
    private TextView mTipTv;
    private ImageView mEffectIv;
    private ImageView mControlIv;
    private TextView mDurationTv;

    private BottomSheetDialog mDialog;
    private View mRootView;

    private String mDefaultContent;

    public static RecorderDialog newInstance() {
        RecorderDialog panel = new RecorderDialog();
        Bundle bundle = new Bundle();
        panel.setArguments(bundle);
        return panel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.VoiceLiveChiefPanelStyle);
        Bundle arguments = getArguments();
        mDefaultContent = arguments.getString("content");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDialog= (BottomSheetDialog)super.onCreateDialog(savedInstanceState);
        mRootView = View.inflate(getContext(), R.layout.lf_recorder_layout , null);
        mDialog.setContentView(mRootView);
        supportPullDownToClose(mRootView, false);
        initView(mRootView);
        setData();
        return mDialog;
    }

    private void initView(View view) {
        mCloseIv = view.findViewById(R.id.recorder_close_iv);
        mContentTv = view.findViewById(R.id.recorder_content_tv);
        mChangeTv = view.findViewById(R.id.recorder_change_content_tv);
        mTipTv = view.findViewById(R.id.recorder_tip_tv);
        mEffectIv = view.findViewById(R.id.recorder_effect_iv);
        mControlIv = view.findViewById(R.id.recorder_control_iv);
        mDurationTv = view.findViewById(R.id.recorder_duration_tv);

        mCloseIv.setOnClickListener(onClickListener);
        mChangeTv.setOnClickListener(onClickListener);
        mControlIv.setOnClickListener(onClickListener);
    }

    private void setData() {
        //mContentTv.setText(mDefaultContent);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.recorder_close_iv) {
                dismissAllowingStateLoss();
            } else if (v.getId() == R.id.recorder_change_content_tv) {

            } else if (v.getId() == R.id.recorder_control_iv) {

            }
        }
    };


}
