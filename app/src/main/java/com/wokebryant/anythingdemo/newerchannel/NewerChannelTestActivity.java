package com.wokebryant.anythingdemo.newerchannel;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.newerchannel.model.ShortVideoFeedData;
import com.wokebryant.anythingdemo.newerchannel.widget.NewerChannelView;
import com.wokebryant.anythingdemo.newerchannel.widget.PhotoFlipperView;

import java.util.List;

/**
 * @author wb-lj589732
 *  新人频道测试Activity
 */
public class NewerChannelTestActivity extends AppCompatActivity {
    private static final String TAG = "NewerChannelTestActivity";

    private boolean needPlayPhoto = true;
    private int videoCount = 0;
    private int lastPhotoPosition = 4;

    private PhotoFlipperView mFlipperView;
    private FrameLayout mVideoPlayer;
    private NewerChannelView mChannelView;

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, NewerChannelTestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newer_channe_test_layoutl);
        mVideoPlayer = findViewById(R.id.newer_channel_video_player);
        mFlipperView = findViewById(R.id.newer_channel_flipper_view);
        mChannelView = findViewById(R.id.newer_channel_root_view);

        mFlipperView.setOnPhotoWallFlipperListener(flipperListener);
        mFlipperView.addImageItem(getBigPhotoList());

        mChannelView.setOnSmallPhotoClickCallBack(callBack);
        mChannelView.initWithData(null);

        Button button = findViewById(R.id.player_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFlipperView.updateFlingPage(0);
                mFlipperView.setVisibility(View.VISIBLE);
                mVideoPlayer.setVisibility(View.GONE);
                mChannelView.updateSmallPhotoPosition(1);
            }
        });
        mFlipperView.updateFlingPage(0);
    }

    private List<String> getBigPhotoList() {
        ShortVideoFeedData model = NewerChannelUtil.getInstance().getMockModel();
        List<String> list =  NewerChannelUtil.getInstance().getFeedPhotoList(model.feeds);
        //list.remove(0);
        return list;
    }

    /**
     *  大图轮播监听
     */
    private NewerChannelContract.OnPhotoWallFlipperListener flipperListener = new NewerChannelContract
        .OnPhotoWallFlipperListener() {
        @Override
        public void onFling(int displayPosition) {
            mChannelView.updateSmallPhotoPosition(displayPosition + videoCount);
            //if (displayPosition  == lastPhotoPosition) {
            //    mFlipperView.postDelayed(new Runnable() {
            //        @Override
            //        public void run() {
            //            //切换到视频播放
            //            mFlipperView.release();
            //            mFlipperView.setVisibility(View.GONE);
            //            mVideoPlayer.setVisibility(View.VISIBLE);
            //            mChannelView.updateSmallPhotoPosition(0);
            //        }
            //    }, 2900);
            //}
            Log.i(TAG, "isFling: " + "true");
        }

    };

    /**
     *  小图点击回调
     */
    private ISmallPhotoClickCallBack callBack = new ISmallPhotoClickCallBack() {
        @Override
        public void onClick(int position) {
            //TODO  切换播放器（短视频或图片）...
            int realPosition = position - videoCount;
            if (mFlipperView != null && needPlayPhoto/* && realPosition >= 0*/) {
                mVideoPlayer.setVisibility(View.GONE);
                mFlipperView.setVisibility(View.VISIBLE);
                mFlipperView.updateFlingPage(realPosition);
            } else {
                mVideoPlayer.setVisibility(View.VISIBLE);
                mFlipperView.setVisibility(View.GONE);
                mFlipperView.release();
            }

        }
    };

}
