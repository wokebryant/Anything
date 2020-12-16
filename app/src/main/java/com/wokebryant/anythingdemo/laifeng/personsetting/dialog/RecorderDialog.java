package com.wokebryant.anythingdemo.laifeng.personsetting.dialog;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.badoo.mobile.util.WeakHandler;
import com.wokebryant.anythingdemo.laifeng.personsetting.Audio.SoundPlayerView;
import com.wokebryant.anythingdemo.laifeng.personsetting.Audio.SoundRecorderService;
import com.wokebryant.anythingdemo.laifeng.personsetting.SettingConstant;
import com.wokebryant.anythingdemo.laifeng.personsetting.SettingDataMapper;
import com.wokebryant.anythingdemo.R;

import java.io.File;
import java.util.List;

/**
 * @author wb-lj589732
 */
public class RecorderDialog extends BaseBottomSheetDialog{

    private static final int REQUEST_RECORD_PERMISSION = 101;
    private static final int MAX_RECORDER_TIME = 60;
    private static final int PREPARE_RECORDING = 0;
    private static final int ING_RECORDING = 1;
    private static final int ENDING_RECORDING = 2;

    private View mRootView;
    private ImageView mCloseIv;
    private TextView mContentTv;
    private TextView mChangeTv;
    private TextView mTipTv;
    private ImageView mEffectIv;
    private Chronometer mChronometer;
    private SoundPlayerView mSoundPlayView;
    private ImageView mControlIv;
    private BottomSheetDialog mDialog;
    private WeakHandler mHandler = new WeakHandler(Looper.getMainLooper());

    private List<String> mTipList;
    private int tipListPosition;
    private int recorderState;
    private String mPlayUrl;
    private String mOssPlayUrl = "Url";

    private OnSaveRecorderListener mListener;
    private Intent recorderIntent;


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
        mSoundPlayView = view.findViewById(R.id.recorder_strip_view);
        mSoundPlayView.setPositionFrom(SettingConstant.FROM_RECORDER_DIALOG);
        mSoundPlayView.setOnDeleteRecorderListener(listener);
        mControlIv = view.findViewById(R.id.recorder_control_iv);
        mChronometer = view.findViewById(R.id.recorder_duration_tv);
        mChronometer.setOnChronometerTickListener(onChronometerTickListener);

        mCloseIv.setOnClickListener(onClickListener);
        mChangeTv.setOnClickListener(onClickListener);
        mControlIv.setOnClickListener(onClickListener);
    }

    private void setData() {
        getRecordingTipContent();
        if (mTipList != null && mTipList.isEmpty()) {
            mContentTv.setText(mTipList.get(0));
        }
    }

    private SoundPlayerView.OnDeleteRecorderListener listener = new SoundPlayerView.OnDeleteRecorderListener() {
        @Override
        public void onDelete() {
            mTipTv.setVisibility(View.VISIBLE);
            mEffectIv.setVisibility(View.GONE);
            mSoundPlayView.setVisibility(View.GONE);
            mChronometer.setVisibility(View.INVISIBLE);
            mControlIv.setImageResource(R.drawable.lf_setting_recorder_start);

            recorderState = PREPARE_RECORDING;
        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.recorder_close_iv) {
                dismissAllowingStateLoss();
            } else if (v.getId() == R.id.recorder_change_content_tv) {
                changeTipContent();
            } else if (v.getId() == R.id.recorder_control_iv) {
                controlRecorder();
            }
        }
    };

    private void changeTipContent() {
        tipListPosition ++;
        if (mTipList != null) {
            if (tipListPosition < mTipList.size()) {
                String tipContent = mTipList.get(tipListPosition);
                mContentTv.setText(tipContent);
            } else {
                tipListPosition = 0;
                mContentTv.setText(mTipList.get(tipListPosition));
            }
        }
    }

    private void controlRecorder() {
        if (PREPARE_RECORDING == recorderState) {
            startRecorder();
        } else if (ING_RECORDING == recorderState) {
            endRecording();
        } else if (ENDING_RECORDING == recorderState) {
            uploadPlayUrlToServer();
            recorderState = PREPARE_RECORDING;
        }
    }

    private Runnable stopRecorderRunnable = new Runnable() {
        @Override
        public void run() {
            setPlayData();
            recorderState = ENDING_RECORDING;
            mTipTv.setVisibility(View.GONE);
            mEffectIv.setVisibility(View.GONE);
            mChronometer.setVisibility(View.INVISIBLE);
            mSoundPlayView.setVisibility(View.VISIBLE);

            mControlIv.setImageResource(R.drawable.lf_setting_recorder_sure);
        }
    };


    private void startRecorder() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_PERMISSION);
        } else {
            onRecording();
        }
    }
    private void onRecording() {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        recorderIntent = new Intent(getActivity(),SoundRecorderService.class);
        File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
        if (!folder.exists()) {
            folder.mkdir();
        }
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.start();
        getActivity().startService(recorderIntent);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        recorderState = ING_RECORDING;
        mTipTv.setVisibility(View.GONE);
        mEffectIv.setVisibility(View.VISIBLE);
        mChronometer.setVisibility(View.VISIBLE);
        mSoundPlayView.setVisibility(View.GONE);
        mControlIv.setImageResource(R.drawable.lf_setting_recorder_stop);
    }

    private void endRecording() {
        if (mChronometer != null) {
            mChronometer.stop();
        }
        getActivity().stopService(recorderIntent);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (mHandler != null) {
            mHandler.postDelayed(stopRecorderRunnable,200);
        }
    }

    private Chronometer.OnChronometerTickListener onChronometerTickListener = new Chronometer
        .OnChronometerTickListener() {
        @Override
        public void onChronometerTick(Chronometer chronometer) {
            int second = Integer.parseInt(mChronometer.getText().toString().split(":")[1]);
            if (MAX_RECORDER_TIME <= second) {
                Toast.makeText(getContext(), "最多只能录60s哦", Toast.LENGTH_SHORT).show();
                endRecording();
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onRecording();
                }
                break;
            default:
        }
    }

    private void setPlayData() {
        if (mSoundPlayView != null) {
            mPlayUrl = mSoundPlayView.setLocalPlayData();
        }
    }

    private void uploadPlayUrlToServer() {
        if (mPlayUrl != null) {
            //TODO 上传录音
        }

        if (mOssPlayUrl != null && mListener != null) {
            mListener.onSave(mPlayUrl);
            dismissAllowingStateLoss();
        }
    }

    private void getRecordingTipContent() {
        SettingDataMapper.getRecordingTipContent(new SettingDataMapper.OnInfoStateListener() {
            @Override
            public void onCompleted(String response) {

            }

            @Override
            public void onException(String error) {

            }
        });
    }

    public interface OnSaveRecorderListener{

        void onSave(String playUrl);
    }

    public void setOnSaveRecorderListener(OnSaveRecorderListener listener) {
        mListener = listener;
    }



    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mSoundPlayView != null) {
            mSoundPlayView.release();
            recorderState = PREPARE_RECORDING;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(stopRecorderRunnable);
        }
    }
}
