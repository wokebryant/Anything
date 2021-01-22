package com.wokebryant.anythingdemo.newerchannel.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.newerchannel.NewerChannelContract;

import java.util.List;

/**
 * @author wb-lj589732
 *  照片自动轮播
 */
public class PhotoFlipperView extends ViewFlipper {

    public static final int FLING_MIN_DISTANCE = 250;
    public static final int FLING_MIN_VELOCITY = 1000;
    public static final int FLING_LEFT = 0;
    public static final int FLING_RIGHT = 1;
    private Context mContext;
    private int previousViewPosition = 0;
    private boolean isPlaying;
    private boolean isFirstPlay = true;
    private List<String> mImageList;
    private GestureDetector mGestureDetector;
    private NewerChannelContract.OnPhotoWallFlipperListener mListener;


    public PhotoFlipperView(Context context) {
        this(context, null);
    }

    public PhotoFlipperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mGestureDetector = new GestureDetector(mContext, mGestureListener);
        setLongClickable(true);
        setOnTouchListener(onTouchListener);
        setBackgroundResource(R.drawable.lf_newer_channel_image_bg);
    }

    private View.OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return mGestureDetector.onTouchEvent(event);
        }
    };

    private GestureDetector.SimpleOnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // previousViewPosition是当前展示的那个view的索引
            previousViewPosition = getDisplayedChild();
            // 从右向左滑动
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                //设置滑动右边界
                //if (previousViewPosition >= mImageList.size() - 1) {
                //    return false;
                //}
                startManualFlipper(FLING_RIGHT);
            // 从左向右滑动
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                //设置滑动左边界
                //if (previousViewPosition == 0) {
                //    return false;
                //}
                startManualFlipper(FLING_LEFT);
            } else {
                return false;
            }
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mListener != null) {
                //if (isPlaying) {
                //    stopAutoFlipper();
                //    mListener.onStopFling();
                //} else {
                //    // 再次起播时必须清除动画
                //    cancelAnimation();
                //    startAutoFlipper();
                //    mListener.onStartFling();
                //}
            }
            return true;
        }
    };

    public void addImageItem(List<String> imageList) {
        if (imageList == null || imageList.isEmpty()) {
            return;
        }
        mImageList = imageList;
        for (String imageUrl : imageList) {
            if (!TextUtils.isEmpty(imageUrl)) {
                addView(getImageView(imageUrl));
            }
        }
        //startAutoFlipper();
    }

    private ImageView getImageView(String imageUrl) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
        return imageView;
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (mListener != null) {
                mListener.onFling(getDisplayedChild());
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     *  自动轮播
     */
    public void startAutoFlipper() {
        isPlaying = true;
        setFlipInterval(3000);

        setAutoStart(true);
        startFlipping();
        setRightAnimation();
        getInAnimation().setAnimationListener(animationListener);
    }

    /**
     *  停止自动轮播
     */
    private void stopAutoFlipper() {
        isPlaying = false;
        setFlipInterval(0);
        setAutoStart(false);
        cancelAnimation();
        stopFlipping();
    }

    private void cancelAnimation() {
        if (getInAnimation() != null) {
            getInAnimation().setAnimationListener(null);
            setInAnimation(null);
        }
        if (getOutAnimation() != null) {
            setOutAnimation(null);
        }
    }

    /**
     *  手势滑动播放
     * @param flingType
     */
    private void startManualFlipper(int flingType) {
        if (isAutoStart()) {
            stopAutoFlipper();
        }
        if (FLING_LEFT == flingType) {
            setLeftAnimation();
            showPrevious();
        } else if (FLING_RIGHT == flingType) {
            setRightAnimation();
            showNext();
        }
        if (mListener != null) {
            mListener.onFling(getDisplayedChild());
        }
    }

    /**
     *  点击小图切换播放
     */
    public void updateFlingPage(int position) {
        release();
        if (position < getDisplayedChild()) {
            setLeftAnimation();
        } else if (position > getDisplayedChild()){
            setRightAnimation();
        }
        setDisplayedChild(position);
        if (mImageList.size() > 1) {
            startAutoFlipper();
        }
    }

    public void setOnPhotoWallFlipperListener(NewerChannelContract.OnPhotoWallFlipperListener listener) {
        mListener = listener;
    }

    /**
     *  从左往右滑动
     */
    private void setLeftAnimation() {
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.lf_anim_left_fade_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.lf_anim_right_fade_out));
    }

    /**
     *  从右往左滑动
     */
    private void setRightAnimation() {
        setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.lf_anim_right_fade_in));
        setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.lf_anim_left_fade_out));
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }

    public void release() {
        if (isAutoStart()) {
            stopAutoFlipper();
        }
    }

}
