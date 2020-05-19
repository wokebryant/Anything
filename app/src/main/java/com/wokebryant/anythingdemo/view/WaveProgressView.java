package com.wokebryant.anythingdemo.view;

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
import android.util.Log;
import android.view.View;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.util.UIUtil;

public class WaveProgressView extends View {

    private static final String TAG = "WaveProgressView";
    private int width;
    private int height;

    private Bitmap bmp;
    private Bitmap bmp2;
    private Bitmap backgroundBitmap;
    private Bitmap scaleBitmap;

    private Canvas canvas2;

    private Path mPath;
    private Paint mPathPaint;
    private Paint mBoxPaint;

    private float mWaveHeight = 10f;
    private float mWaveWidth = 60f;
    private String mWaveColor = "#FFFFFF";
    private  int  mWaveSpeed = 30;

    private int maxProgress = 100;
    private int currentProgress = 0;
    private float currentY;
    private float currentMidY;

    private float distance = 0;
    private int RefreshGap = 30;

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
        if(null==getResources().getDrawable(R.drawable.lfcontainer_pk_wave_full)){
            throw new IllegalArgumentException(String.format("background is null."));
        }else{
            backgroundBitmap = getBitmapFromDrawable(getResources().getDrawable(R.drawable.lfcontainer_pk_wave_full));
        }

        mPath = new Path();
        mPathPaint = new Paint();
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStyle(Paint.Style.FILL);

        //handler.sendEmptyMessageDelayed(INVALIDATE,100);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void startWaveProgress(boolean isPK) {
        if (isPK) {
            handler.sendEmptyMessage(INVALIDATE);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        currentY = height = MeasureSpec.getSize(heightMeasureSpec);
        Log.i(TAG, "width= " + width + " height= " + height);
        Log.i(TAG, "dpWidth= " + UIUtil.dip2px(getContext(), 53) + " dpHeight= " + UIUtil.dip2px(getContext(), 62));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(backgroundBitmap != null && !backgroundBitmap.isRecycled()){
            canvas.drawBitmap(createImage(), 0, 0, mPathPaint);

            scaleBitmap = Bitmap.createScaledBitmap(backgroundBitmap,width ,height,false);
            canvas2.drawBitmap(scaleBitmap,0,0,mBoxPaint);
            mBoxPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bmp2, 0, 0, mBoxPaint);
        }
    }

    private Bitmap createImage() {
        mPathPaint.setColor(Color.parseColor(mWaveColor));
        mBoxPaint = new Paint();
        mBoxPaint.setAntiAlias(true);
        try {
            bmp = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
            bmp2 = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bmp);
            canvas2 = new Canvas(bmp2);

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
            //scaleBitmap = Bitmap.createScaledBitmap(backgroundBitmap,width ,height,false);
            //
            //mBoxPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            //
            //canvas2.drawBitmap(scaleBitmap,0,0,mBoxPaint);

        } catch (OutOfMemoryError error) {
            error.printStackTrace();
        }
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
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (backgroundBitmap != null && !backgroundBitmap.isRecycled()) {
            backgroundBitmap.recycle();
            backgroundBitmap = null;
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

}

