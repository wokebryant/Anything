package com.wokebryant.anythingdemo.Audio;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Looper;
import android.util.Log;

import com.badoo.mobile.util.WeakHandler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author wb-lj589732
 */
public class SoundPlayUtil {

    private Context mContext;
    private ISoundPlayer mSoundPlayView;
    private MediaPlayer mSoundPlayer;
    private WeakHandler mHandler;
    private Runnable mPlayRunnable;

    private boolean hasPrepared;
    private boolean isPage;
    private String mSoundUrl;
    private int mRecorderDuration;
    private int position;


    public void initSoundPlayer(ISoundPlayer soundPlayView, Context context) {
        mSoundPlayView = soundPlayView;
        mContext = context;

        try {
            mSoundPlayer = new MediaPlayer();
            mSoundPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mSoundPlayer.setOnPreparedListener(mOnPreparedListener);
            mSoundPlayer.setOnErrorListener(mOnErrorListener);
            mSoundPlayer.setOnCompletionListener(mOnCompletionListener);
        } catch (Exception e) {
            Log.i("soundPlayer", "init error");
        }
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            hasPrepared = true;
            if (mSoundPlayView != null) {
                mSoundPlayView.setSoundDuration(getSoundDuration());
            }
        }
    };

    private MediaPlayer.OnErrorListener mOnErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            if (mSoundPlayer != null) {
                mSoundPlayer.reset();
            }
            return false;
        }
    };

    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            stopPlayAnim();
        }
    };

    public void setPlayUrl(String soundUrl) {
        mSoundUrl = soundUrl;
        if (mSoundUrl == null) {
            return;
        }
        try {
            mSoundPlayer.setDataSource(mSoundUrl);
            mSoundPlayer.prepare();
        } catch (IOException e) {
            Log.i("soundPlayer", "setDataSource error");
        } catch (IllegalStateException e) {
            Log.i("soundPlayer", "setDataSource error");
        }
    }

    public void setPlayData() {
        if (mContext != null) {
            SharedPreferences recorderRef = mContext.getSharedPreferences("sp_name_audio", MODE_PRIVATE);
            String filePath = recorderRef.getString("audio_path", "");
            long fileLength  = recorderRef.getLong("elapsed", 0);

            long minutes = TimeUnit.MILLISECONDS.toMinutes(fileLength);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(fileLength) - TimeUnit.MINUTES.toSeconds(minutes);

            if (filePath != null) {
                setPlayUrl(filePath);
            }
            Log.i("soundPlayer", "filePath= " + filePath + " fileLength= " + fileLength);
        }
    }

    public void resetPlayUrl(String soundUrl) {
        if (soundUrl != null || hasPrepared) {
            mSoundPlayer.reset();
        }
        setPlayUrl(soundUrl);
    }

    public void playSound() {
        if (mSoundPlayer != null) {
            mSoundPlayer.seekTo(0);
            mSoundPlayer.start();
            startPlayAnim();
        }
    }

    public void controlPlay() {
        if (mSoundPlayer != null) {
            if (mSoundPlayer.isPlaying()) {
                mSoundPlayer.pause();
                stopPlayAnim();
            } else {
                playSound();
            }

            if (mSoundUrl == null) {
                mSoundPlayView.showRecorderDialog();
            }
        }
    }

    private void startPlayAnim() {
        position = 0;
        if (mHandler == null) {
            mHandler = new WeakHandler(Looper.getMainLooper());
        }
        if (mPlayRunnable == null) {
            mPlayRunnable = new Runnable() {
                @Override
                public void run() {
                    mHandler.postDelayed(this, 500);
                    if (mSoundPlayView != null) {
                        mSoundPlayView.showPlayAnim(transformDate(mRecorderDuration
                            - mSoundPlayer.getCurrentPosition()));
                        position ++;
                    }
                }
            };
        }
        mHandler.removeCallbacks(mPlayRunnable);
        mHandler.post(mPlayRunnable);
    }

    private void stopPlayAnim() {
        if (mSoundPlayView != null) {
            mSoundPlayView.showPauseView();
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mPlayRunnable);
        }
    }

    private String getSoundDuration() {
        if (mSoundPlayer == null) {
            return null;
        }
        mRecorderDuration = mSoundPlayer.getDuration();
        if (mRecorderDuration == -1) {
            return null;
        } else {
            int sec = mRecorderDuration / 1000;
            int m = sec / 60;
            int s = sec % 60;
            return m + ":" + s;
        }
    }

    public String transformDate(long millSecond) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millSecond);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millSecond)
            - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes,seconds);
    }

    public void deleteRecorder() {
        if (mSoundPlayView != null) {
            mSoundPlayView.deleteView();
        }
    }

    public void release() {
        if (mSoundPlayer != null) {
            mSoundPlayer.stop();
            mSoundPlayer.reset();
            mSoundPlayer.release();
            mSoundPlayer = null;
        }
        if (mHandler != null) {
            mHandler.removeCallbacks(mPlayRunnable);
        }
    }

}
