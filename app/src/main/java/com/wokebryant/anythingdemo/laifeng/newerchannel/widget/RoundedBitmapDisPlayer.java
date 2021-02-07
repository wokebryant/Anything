package com.wokebryant.anythingdemo.laifeng.newerchannel.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

/**
 *  重写ImageLoader裁剪类，让其支持ScaleType属性
 *  @author wb-lj589732
 */
public class RoundedBitmapDisPlayer implements BitmapDisplayer {

    protected final int cornerRadius;
    protected final int margin;
    protected final Type type;

    public RoundedBitmapDisPlayer(int cornerRadiusPixels, Type type) {
        this(cornerRadiusPixels, 0, type);
    }

    public RoundedBitmapDisPlayer(int cornerRadiusPixels, int marginPixels, Type type) {
        this.cornerRadius = cornerRadiusPixels;
        this.margin = marginPixels;
        this.type = type;
    }

    public enum Type {
        center, fitXY, centerCrop
    }

    @Override
    public void display(Bitmap bitmap, ImageAware imageAware, LoadedFrom loadedFrom) {
        if (!(imageAware instanceof ImageViewAware)) {
            throw new IllegalArgumentException("ImageAware should wrap ImageView. ImageViewAware is expected.");
        }

        imageAware.setImageDrawable(new RoundCornerDrawable(bitmap, cornerRadius, margin, type));

    }

    public static class RoundCornerDrawable extends Drawable {

        protected final float cornerRadius;
        protected final int margin;
        protected  Type type = Type.fitXY;

        protected RectF mRect = new RectF();
        protected final BitmapShader bitmapShader;
        protected final Paint paint;
        protected Bitmap mBitmap;


        public RoundCornerDrawable(Bitmap bitmap, int cornerRadius, int margin, Type type) {
            this.cornerRadius = cornerRadius;
            this.margin = margin;
            this.type = type;
            mBitmap = bitmap;

            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            mRect.set(margin, margin, bounds.width() - margin, bounds.height() - margin);

            Matrix shaderMatrix = new Matrix();
            int width = bounds.width();
            int height = bounds.height();

            switch (type){
                case centerCrop:
                    float scale = width * 1.0f / mBitmap.getWidth();
                    if (scale * mBitmap.getHeight() < height) {
                        scale = height * 1.0f / mBitmap.getHeight();
                    }
                    int outWidth = Math.round(scale * mBitmap.getWidth());
                    int outHeight = Math.round(scale * mBitmap.getHeight());

                    shaderMatrix.postScale(scale, scale);

                    int left = 0;
                    int top = 0;
                    if (outWidth == width) {
                        top = (outHeight - height) * -1 / 2;
                    }else {
                        left = (outWidth - width) * -1 / 2;
                    }
                    shaderMatrix.postTranslate(left, top);
                    break;
                case fitXY:
                    float wScale = width * 1.0f / mBitmap.getWidth();
                    float hScale = height * 1.0f / mBitmap.getHeight();
                    shaderMatrix.postScale(wScale, hScale);
                    break;
                case center:
                    int moveleft;
                    int movetop;
                    moveleft = (width - mBitmap.getWidth()) /2;
                    movetop = (height - mBitmap.getHeight()) /2;
                    shaderMatrix.postTranslate(moveleft, movetop);
                    break;
            }

            bitmapShader.setLocalMatrix(shaderMatrix);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawRoundRect(mRect, cornerRadius, cornerRadius, paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }

}
