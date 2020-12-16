package com.wokebryant.anythingdemo.utils.ImageCrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class ClipView extends View {

    private static final String TAG = "ClipView";

    public static final int TYPE_HORIZONTAL = 1;
    public static final int TYPE_VERTICAL = 2;
    public static final int TYPE_SQUARE = 3;
    public static final int TYPE_FULL = 4;

    //画裁剪框透明区域的画笔
    private Paint paint = new Paint();

    //裁剪框水平方向间距
    private float mHorizontalPadding;
    //裁剪框的半径
    private int clipRadiusWidth;
    //裁剪框矩形宽度
    private int clipWidth;

    //裁剪框类别，默认为1:1
    private ClipType clipType = ClipType.SQUARE;
    private Xfermode xfermode;
    private Context context;

    //裁剪框边框画笔
    private Paint mPalaceBorderPaint;
    //裁剪框九宫格画笔
    private Paint mGuidelinePaint;
    //绘制裁剪边框四个角的画笔
    private Paint mCornerPaint;
    private float mCornerThickness;
    private float mBorderThickness;
    //四个角小短边的长度
    private float mCornerLength;


    public ClipView(Context context) {
        this(context, null);
    }

    public ClipView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClipView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint.setAntiAlias(true);
        paint.setStyle(Style.FILL);

        mPalaceBorderPaint = new Paint();
        mPalaceBorderPaint.setStyle(Style.STROKE);
        mPalaceBorderPaint.setStrokeWidth(dip2px(context, 1.0f));
        mPalaceBorderPaint.setColor(Color.parseColor("#FFFFFF"));

        mGuidelinePaint = new Paint();
        mGuidelinePaint.setStyle(Style.STROKE);
        mGuidelinePaint.setStrokeWidth(dip2px(context, 1.0f));
        mGuidelinePaint.setColor(Color.parseColor("#B3FFFFFF"));

        mCornerPaint = new Paint();
        mCornerPaint.setStyle(Style.STROKE);
        mCornerPaint.setStrokeWidth(dip2px(context, 3.0f));
        mCornerPaint.setColor(Color.WHITE);

        mBorderThickness = mPalaceBorderPaint.getStrokeWidth();
        mCornerThickness = mCornerPaint.getStrokeWidth();
        mCornerLength = dip2px(context, 25);

        xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.i(TAG, "onDraw: clipType =" + clipType);
        canvas.saveLayer(0, 0, this.getWidth(), this.getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //设置背景
        canvas.drawColor(Color.parseColor("#801C1F2F"));
        paint.setXfermode(xfermode);

        Rect clipRect = getClipRect();
        if (clipType == ClipType.HORIZONTAL) {
            //绘制中间的矩形
            canvas.drawRect(mHorizontalPadding, this.getHeight() / 2 - (float) 9 / 16 * clipWidth / 2,
                this.getWidth() - mHorizontalPadding, this.getHeight() / 2 + (float) 9 / 16 * clipWidth / 2, paint);
        } else if (clipType == ClipType.VERTICAL) {
            canvas.drawRect(mHorizontalPadding, this.getHeight() / 2 - (float) 16 / 9 * clipWidth / 2,
                this.getWidth() - mHorizontalPadding, this.getHeight() / 2 + (float) 16 / 9 * clipWidth / 2, paint);
        } else if (clipType == ClipType.SQUARE){
            canvas.drawRect(mHorizontalPadding, this.getHeight() / 2 - clipWidth / 2,
                this.getWidth() - mHorizontalPadding, this.getHeight() / 2 + clipWidth / 2, paint);
        } else {
            if ((float)16 / 9 * this.getWidth() < this.getHeight()) {
                float top = (this.getHeight() - (float)16 / 9 * this.getWidth()) / 2;
                float bottom = this.getHeight() - top;
                canvas.drawRect(0,top,this.getWidth(),bottom,paint);
            } else if ((float)16 / 9 * this.getWidth() > this.getHeight()){
                float left = (this.getWidth() - (float)9 / 16 * this.getHeight()) / 2;
                float right = this.getWidth() - left;
                canvas.drawRect(left,0,right,this.getHeight(),paint);
            } else {
                canvas.drawRect(0,0,this.getWidth(),this.getHeight(),paint);
            }
        }
        //绘制裁剪边框
        drawBorder(canvas, clipRect);
        //绘制九宫格引导线
        drawGuidelines(canvas, clipRect);
        //绘制裁剪边框的四个角
        drawCorners(canvas, clipRect);
        //出栈，恢复到之前的图层，意味着新建的图层会被删除，新建图层上的内容会被绘制到canvas (or the previous layer)
        canvas.restore();
    }

    /**
     * 获取裁剪区域的Rect
     *
     * @return
     */
    public Rect getClipRect() {
        Rect rect = new Rect();
        rect.left = (this.getWidth() / 2 - clipRadiusWidth);
        rect.right = (this.getWidth() / 2 + clipRadiusWidth);

        if (clipType == ClipType.HORIZONTAL) {
            rect.top = (int)(this.getHeight() / 2 - (float)9 / 16 * clipRadiusWidth);
            rect.bottom = (int)(this.getHeight() / 2 + (float)9 / 16 * clipRadiusWidth);
        } else if (clipType == ClipType.VERTICAL) {
            rect.top = (int)(this.getHeight() / 2 - (float)16 / 9 * clipRadiusWidth);
            rect.bottom = (int)(this.getHeight() / 2 + (float)16 / 9 * clipRadiusWidth);
        } else if (clipType == ClipType.SQUARE){
            rect.top = this.getHeight() / 2 -  clipRadiusWidth;
            rect.bottom = this.getHeight() / 2 + clipRadiusWidth;
        } else {
            if ((float)16 / 9 * this.getWidth() < this.getHeight()) {
                rect.left = 0;
                rect.right = this.getWidth();
                rect.top = (int)((this.getHeight() - (float)16 / 9 * this.getWidth()) / 2);
                rect.bottom = this.getHeight() - rect.top;
            } else if ((float)16 / 9 * this.getWidth() > this.getHeight()) {
                rect.left = (int)((this.getWidth() - (float)9 / 16 * this.getHeight()) / 2);
                rect.right = this.getWidth() - rect.left;
                rect.top = 0;
                rect.bottom = this.getHeight();
            } else {
                rect.left = 0;
                rect.right = this.getWidth();
                rect.top = 0;
                rect.bottom = this.getHeight();
            }
        }

        return rect;
    }

    /**
     * 设置裁剪框水平间距
     *
     * @param mHorizontalPadding
     */
    public void setHorizontalPadding(float mHorizontalPadding) {
        this.mHorizontalPadding = mHorizontalPadding;
        this.clipRadiusWidth = (int) (getScreenWidth(getContext()) - 2 * mHorizontalPadding) / 2;
        this.clipWidth = clipRadiusWidth * 2;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public void setClipType(int type) {
        switch (type) {
            case TYPE_HORIZONTAL:
                this.setClipType(ClipType.HORIZONTAL);
                break;
            case TYPE_VERTICAL:
                this.setClipType(ClipType.VERTICAL);
                break;
            case TYPE_SQUARE:
                this.setClipType(ClipType.SQUARE);
                break;
            case TYPE_FULL:
                this.setClipType(ClipType.FULL);
            default:
                break;
        }
    }


    /**
     * 设置裁剪框类别
     *
     * @param clipType
     */
    public void setClipType(ClipType clipType) {
        this.clipType = clipType;
    }

    /**
     * 裁剪框类别，16:9、9:16、1:1,全屏
     */
    public enum ClipType {
        HORIZONTAL(1), VERTICAL(2), SQUARE(3), FULL(4);

        private int value;

        ClipType(int value) {
            value = value;
        }
    }

    private void drawGuidelines(@NonNull Canvas canvas, Rect clipRect) {

        final float left = clipRect.left;
        final float top = clipRect.top;
        final float right = clipRect.right;
        final float bottom = clipRect.bottom;

        final float oneThirdCropWidth = (right - left) / 3;

        final float x1 = left + oneThirdCropWidth;
        //引导线竖直方向第一条线
        canvas.drawLine(x1, top, x1, bottom, mGuidelinePaint);
        final float x2 = right - oneThirdCropWidth;
        //引导线竖直方向第二条线
        canvas.drawLine(x2, top, x2, bottom, mGuidelinePaint);

        final float oneThirdCropHeight = (bottom - top) / 3;

        final float y1 = top + oneThirdCropHeight;
        //引导线水平方向第一条线
        canvas.drawLine(left, y1, right, y1, mGuidelinePaint);
        final float y2 = bottom - oneThirdCropHeight;
        //引导线水平方向第二条线
        canvas.drawLine(left, y2, right, y2, mGuidelinePaint);
    }

    private void drawBorder(@NonNull Canvas canvas, Rect clipRect) {

        canvas.drawRect(clipRect.left,
                clipRect.top,
                clipRect.right,
                clipRect.bottom,
                mPalaceBorderPaint);
    }


    private void drawCorners(@NonNull Canvas canvas, Rect clipRect) {

        final float left = clipRect.left;
        final float top = clipRect.top;
        final float right = clipRect.right;
        final float bottom = clipRect.bottom;

        //简单的数学计算
        float lateralOffset;
        float startOffset;
        if (clipType == ClipType.FULL) {
            lateralOffset = -dip2px(getContext(),2.0f);
            startOffset = -0.0f;
        } else {
            lateralOffset = (mCornerThickness - mBorderThickness) / 2f;
            startOffset = mCornerThickness - (mBorderThickness / 2f);
        }
        //左上角左面的短线
        canvas.drawLine(left - lateralOffset, top - startOffset, left - lateralOffset, top + mCornerLength, mCornerPaint);
        //左上角上面的短线
        canvas.drawLine(left - startOffset, top - lateralOffset, left + mCornerLength, top - lateralOffset, mCornerPaint);

        //右上角右面的短线
        canvas.drawLine(right + lateralOffset, top - startOffset, right + lateralOffset, top + mCornerLength, mCornerPaint);
        //右上角上面的短线
        canvas.drawLine(right + startOffset, top - lateralOffset, right - mCornerLength, top - lateralOffset, mCornerPaint);

        //左下角左面的短线
        canvas.drawLine(left - lateralOffset, bottom + startOffset, left - lateralOffset, bottom - mCornerLength, mCornerPaint);
        //左下角底部的短线
        canvas.drawLine(left - startOffset, bottom + lateralOffset, left + mCornerLength, bottom + lateralOffset, mCornerPaint);

        //右下角左面的短线
        canvas.drawLine(right + lateralOffset, bottom + startOffset, right + lateralOffset, bottom - mCornerLength, mCornerPaint);
        //右下角底部的短线
        canvas.drawLine(right + startOffset, bottom + lateralOffset, right - mCornerLength, bottom + lateralOffset, mCornerPaint);
    }

    public static int dip2px(@Nullable Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
