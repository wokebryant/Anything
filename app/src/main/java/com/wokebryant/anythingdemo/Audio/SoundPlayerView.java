package com.wokebryant.anythingdemo.Audio;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;

public class SoundPlayerView extends FrameLayout implements ISoundPlayer{

    private Context mContext;
    private SoundPlayUtil mSoundPlayUtil;

    private ImageView mSoundPlayBtn;
    private TextView mSoundDurationTv;
    private TextView mSoundPraiseTv;

    private String mPlayUrl;


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
        mSoundPlayBtn = findViewById(R.id.sound_play_btn);
        mSoundDurationTv = findViewById(R.id.sound_duration_tv);
        mSoundPraiseTv = findViewById(R.id.sound_praise_tv);

        mSoundPlayBtn.setOnClickListener(mOnClickListener);
    }

    private void initPlayer() {
        if (mSoundPlayUtil == null) {
            mSoundPlayUtil = new SoundPlayUtil();
        }
        mSoundPlayUtil.initSoundPlayer(this, mContext);
        mSoundPlayUtil.setPlayUrl(mPlayUrl);
        mSoundPlayUtil.playSound();
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
        mSoundDurationTv.setText(duration);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.sound_play_btn && mSoundPlayUtil != null) {
                mSoundPlayUtil.controlPlay();
            }
        }
    };

    public void release() {
        if (mSoundPlayUtil != null) {
            mSoundPlayUtil.release();
        }
    }
}
