package com.wokebryant.anythingdemo.view;

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

    private Bitmap backgroundBitmap;

    private Path mPath;
    private Paint mPathPaint;

    private float mWaveHeight = 10f;
    private float mWaveWidth = 60f;
    private String mWaveColor = "#FFFFFF";
    private  int  mWaveSpeed = 30;

    private int maxProgress = 100;
    private int currentProgress = 0;
    private float currentY;
    private float currentMidY;

    private float distance = 0;
    private int RefreshGap = 10;

    private static final int INVALIDATE = 0X777;
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
        if(null==getResources().getDrawable(R.drawable.lf_pk_wave_full)){
            throw new IllegalArgumentException(String.format("background is null."));
        }else{
            backgroundBitmap = getBitmapFromDrawable(getResources().getDrawable(R.drawable.lf_pk_wave_full));
        }

        mPath = new Path();
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);

        handler.sendEmptyMessageDelayed(INVALIDATE,100);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = View.MeasureSpec.getSize(widthMeasureSpec);
        currentY = height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(backgroundBitmap!=null){
            canvas.drawBitmap(createImage(), 0, 0, null);
        }
    }

    private Bitmap createImage() {
        mPathPaint.setColor(Color.parseColor(mWaveColor));
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap bmp = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bmp);

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
        canvas.drawPath(mPath, mPathPaint);
        int min = Math.max(width,height);
        backgroundBitmap = Bitmap.createScaledBitmap(backgroundBitmap,width,height,false);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(backgroundBitmap,0,0,paint);
        return bmp;
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

    public void release() {
        handler.removeCallbacksAndMessages(null);
    }

}

