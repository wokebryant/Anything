package com.wokebryant.anythingdemo.Audio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

import java.io.File;

public class SoundRecorderFragment extends Fragment implements ISoundPlayer{

    private static final int REQUEST_RECORD_PERMISSION = 101;

    private Context mContext;
    private SoundPlayUtil mSoundPlayUtil;
    private ImageView mRetryIv, mStartIv, mSaveIv;
    private Chronometer mChronometer;
    private TextView mRecorderTimeTv;
    private OnRecorderStateChangeListener mRecorderListener;

    private String mPlayUrl;
    private boolean startRecord = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lf_sound_recorder_view, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPlayer();
    }

    public static Fragment newInstance() {
        SoundRecorderFragment fragment = new SoundRecorderFragment();
        Bundle arg = new Bundle();
        fragment.setArguments(arg);
        return fragment;
    }

    private void initView(View view) {
        mRetryIv = view.findViewById(R.id.sound_recorder_retry);
        mStartIv = view.findViewById(R.id.sound_recorder_start);
        mSaveIv = view.findViewById(R.id.sound_recorder_save);
        mChronometer = view.findViewById(R.id.sound_recorder_chronometer);
        mRecorderTimeTv = view.findViewById(R.id.sound_recorder_time);

        mRetryIv.setOnClickListener(mOnClickListener);
        mStartIv.setOnClickListener(mOnClickListener);
        mSaveIv.setOnClickListener(mOnClickListener);

        mContext = getContext();
    }

    private void initPlayer() {
        if (mSoundPlayUtil == null) {
            mSoundPlayUtil = new SoundPlayUtil();
        }
        mSoundPlayUtil.initSoundPlayer(this, mContext);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sound_recorder_retry) {

            } else if (v.getId() == R.id.sound_recorder_start) {
                startRecorder();
            } else if (v.getId() == R.id.sound_recorder_play) {
                playRecorder();
            } else if (v.getId() == R.id.sound_recorder_save) {

            }
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
            onRecording(startRecord);
            startRecord = !startRecord;
        }
    }

    private void onRecording(boolean start) {
        if (getActivity() == null || getActivity().isFinishing()) {
            return;
        }
        Intent recorderIntent = new Intent(getActivity(),SoundRecorderService.class);
        //开始录音
        if (start) {
            File folder = new File(Environment.getExternalStorageDirectory() + "/SoundRecorder");
            if (!folder.exists()) {
                folder.mkdir();
            }
            mChronometer.setBase(SystemClock.elapsedRealtime());
            mChronometer.start();
            getActivity().startService(recorderIntent);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //结束录音
        } else {
            mChronometer.stop();
            getActivity().stopService(recorderIntent);
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void playRecorder() {
        if (mSoundPlayUtil != null) {
            mSoundPlayUtil.setPlayData();
            mSoundPlayUtil.playSound();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    onRecording(startRecord);
                }
                break;
            default:
        }
    }

    @Override
    public void showPlayAnim(String currentTime) {
        //TODO 录音播放的倒计时时间

    }

    @Override
    public void showPauseView() {
        //TODO

    }

    @Override
    public void setSoundDuration(String duration) {
        if (duration != null) {
            mRecorderTimeTv.setText(duration);
        }
    }

    private interface OnRecorderStateChangeListener {

        void onStart();

        void onStop();

        void onBack();
    }

    public void setOnRecorderStateChangeListener(OnRecorderStateChangeListener listener) {
        mRecorderListener = listener;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mSoundPlayUtil != null) {
            mSoundPlayUtil.release();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSoundPlayUtil != null) {
            mSoundPlayUtil.release();
        }
    }

}
