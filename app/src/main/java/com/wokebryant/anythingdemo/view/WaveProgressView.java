package com.wokebryant.anythingdemo.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.wokebryant.anythingdemo.R;

public class WaveProgressView extends View {
    private int width;
    private int height;

    private Bitmap bmp;
    private Bitmap backgroundBitmap;
    private Bitmap scaleBitmap;

    private Path mPath;
    private Paint mPathPaint;
    private Paint mBitmapPaint;

    private float mWaveHeight = 10f;
    private float mWaveWidth = 60f;
    private String mWaveColor = "#3FE2FF";
    private  int  mWaveSpeed = 30;

    private int maxProgress = 100;
    private int currentProgress = 0;
    private float currentY;
    private float currentMidY;

    private float distance = 0;
    private int RefreshGap = 50;

    private Canvas mWaveCanvas;

    private static final int INVALIDATE = 0X777;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INVALIDATE:
                    invalidate();
                    sendEmptyMessageDelayed(INVALIDATE,RefreshGap);
                    break;
            }
        }
    };

    public WaveProgressView(Context context) {
        this(context,null,0);
    }
    public WaveProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public WaveProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if(null==getResources().getDrawable(R.drawable.lf_pk_wave_empty)){
            throw new IllegalArgumentException(String.format("background is null."));
        }else{
            backgroundBitmap = getBitmapFromDrawable(getResources().getDrawable(R.drawable.lf_pk_wave_empty));
        }

        mPath = new Path();
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setColor(Color.parseColor(mWaveColor));
        mPathPaint.setStyle(Paint.Style.FILL);

        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    public void startWaveProgress(boolean isPK) {
        if (isPK) {
            handler.sendEmptyMessage(INVALIDATE);
            //startDrawWave();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        currentY = height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(backgroundBitmap != null && !backgroundBitmap.isRecycled()){
            canvas.drawBitmap(createImage(), 0, 0, null);
        }
    }

    private Bitmap createImage() {
        try {
            if (bmp == null) {
                bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }

            if (mWaveCanvas == null) {
                mWaveCanvas = new Canvas(bmp);
            } else {
                mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                mWaveCanvas.drawPaint(mBitmapPaint);
            }

            currentMidY = height*(maxProgress-currentProgress)/maxProgress;
            if(currentY > currentMidY){
                currentY = currentY - (currentY-currentMidY)/10;
            }
            mPath.reset();
            mPath.moveTo(0-distance,currentY);
            int waveNum = width/((int)mWaveWidth);
            int num = 0;
            for(int i =0;i<waveNum;i++){
                mPath.quadTo(mWaveWidth*(num+1)-distance,currentY-mWaveHeight,mWaveWidth*(num+2)-distance,currentY);
                mPath.quadTo(mWaveWidth*(num+3)-distance,currentY+mWaveHeight,mWaveWidth*(num+4)-distance,currentY);
                num+=4;
            }
            distance +=mWaveWidth/mWaveSpeed;
            distance = distance%(mWaveWidth*4);
            mPath.lineTo(width,height);
            mPath.lineTo(0,height);
            mPath.close();
            mWaveCanvas.drawPath(mPath, mPathPaint);
            if (scaleBitmap == null) {
                scaleBitmap = Bitmap.createScaledBitmap(backgroundBitmap, width, height, false);
            }

            mBitmapPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            mWaveCanvas.drawBitmap(scaleBitmap,0,0,mBitmapPaint);

        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
        return bmp;
    }

    private boolean isFirstDrawWave;

    private void startDrawWave() {
        if (!isFirstDrawWave) {
            isFirstDrawWave = true;
            ValueAnimator animator = ValueAnimator.ofInt(0, 1);
            animator.setDuration(10000);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    postInvalidateDelayed(500);
                }
            });
            animator.start();
        }
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap;
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public void setProgress(int currentProgress) {
        this.currentProgress = currentProgress;
    }

    public void setWaveColor(String mWaveColor){
        this.mWaveColor = mWaveColor;
    }

    public void resetProgress() {
        currentY = height;
    }

    //上下滑房间调用
    public void stop() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (scaleBitmap != null && !scaleBitmap.isRecycled()) {
            scaleBitmap.recycle();
            scaleBitmap = null;
        }
        if (bmp != null && !bmp.isRecycled()) {
            bmp.recycle();
            bmp = null;
        }
//        System.gc();
    }

    //退出房间时调用
    public void release() {
        stop();
        if (backgroundBitmap != null && !backgroundBitmap.isRecycled()) {
            backgroundBitmap.recycle();
            backgroundBitmap = null;
        }
    }

}

