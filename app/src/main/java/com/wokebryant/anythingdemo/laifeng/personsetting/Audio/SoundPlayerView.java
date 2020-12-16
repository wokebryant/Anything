package com.wokebryant.anythingdemo.laifeng.personsetting.Audio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.laifeng.personsetting.Activity.PersonalSettingActivity;
import com.wokebryant.anythingdemo.laifeng.personsetting.SettingConstant;
import com.wokebryant.anythingdemo.R;

/**
 * @author wb-lj589732
 */
public class SoundPlayerView extends RelativeLayout implements ISoundPlayer{

    private Context mContext;
    private SoundPlayUtil mSoundPlayUtil;

    private View mRootView;
    private TextView mAddView;
    private TextView mStateTv;
    private FrameLayout mDeleteView;

    private String mPlayUrl;
    private String from;

    public OnDeleteRecorderListener listener;


    public SoundPlayerView(Context context) {
        this(context, null);
    }

    public SoundPlayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SoundPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initPlayer();
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.lf_sound_player_view, this, true);
        mRootView = findViewById(R.id.rootView);
        mAddView = findViewById(R.id.person_setting_recorder_add);
        mStateTv = findViewById(R.id.person_setting_recorder_state);
        mDeleteView = findViewById(R.id.person_setting_recorder_delete);

        mRootView.setOnClickListener(mOnClickListener);
        mDeleteView.setOnClickListener(mOnClickListener);
    }

    private void initPlayer() {
        if (mSoundPlayUtil == null) {
            mSoundPlayUtil = new SoundPlayUtil();
            mSoundPlayUtil.initSoundPlayer(this, mContext);
        }
    }

    public void setPlayUrl(String playUrl) {
        mPlayUrl = playUrl;
        if (mPlayUrl != null && mSoundPlayUtil != null) {
            mSoundPlayUtil.setPlayUrl(mPlayUrl);
            mAddView.setVisibility(GONE);
            mDeleteView.setVisibility(VISIBLE);
        } else {
            mAddView.setVisibility(VISIBLE);
            mStateTv.setText(R.string.person_setting_add_recorder);
            mDeleteView.setVisibility(GONE);
        }
    }

    /**
     * 个人页自动播放
     */
    private void startPlayer() {
        if (mSoundPlayUtil != null) {
            mSoundPlayUtil.playSound();
        }
    }

    public String setLocalPlayData() {
        String playUrl = null;
        if (mSoundPlayUtil != null) {
            playUrl = mSoundPlayUtil.setLocalPlayData();
            if (playUrl != null) {
                mAddView.setVisibility(GONE);
                mDeleteView.setVisibility(VISIBLE);
            }
        }
        return playUrl;
    }

    @Override
    public void showPlayAnim(String currentTime) {
        //TODO
    }

    @Override
    public void showPauseView() {
        //TODO
    }

    @Override
    public void setSoundDuration(String duration) {
        mStateTv.setText(duration);
    }

    @Override
    public void showRecorderDialog() {
        if (mContext instanceof PersonalSettingActivity) {
            PersonalSettingActivity activity = (PersonalSettingActivity) mContext;
            //
            activity.showRealPersonDialog();
        }
    }

    @Override
    public void deleteView() {
        if (SettingConstant.FROM_PERSON_SETTING_EDIT.equals(from)) {
            mAddView.setVisibility(VISIBLE);
            mStateTv.setText(R.string.person_setting_add_recorder);
            mDeleteView.setVisibility(GONE);
            mSoundPlayUtil.resetPlayUrl(null);
            listener.onDelete();
        } else if (SettingConstant.FROM_RECORDER_DIALOG.equals(from)){
            setVisibility(GONE);
            listener.onDelete();
        } else {

        }
    }

    /**
     *设置显示位置，个人页，个人编辑页，或录音弹窗
     */
    public void setPositionFrom(String from) {
        this.from = from;
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.rootView && mSoundPlayUtil != null) {
                mSoundPlayUtil.controlPlay();
            } else if (v.getId() == R.id.person_setting_recorder_delete) {
                mSoundPlayUtil.deleteRecorder();
            }
        }
    };

    public interface OnDeleteRecorderListener{

        void onDelete();
    }

    public void setOnDeleteRecorderListener(OnDeleteRecorderListener listener){
        this.listener = listener;
    }

    public void release() {
        if (mSoundPlayUtil != null) {
            mSoundPlayUtil.release();
        }
    }
}
