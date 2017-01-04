package com.glemontree.slideverificate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Administrator on 2017/1/4.
 */

public class SlideVerificateView extends View {

    VerificateResult verificateResult;

    Bitmap bitmap;
    Bitmap drawBitmap;
    Bitmap slideBitmap;
    int x, y;
    int left, top, right, bottom;
    int moveX;
    int moveMax;
    int trueX;
    boolean reCalc = true;
    private double accuracy;

    public SlideVerificateView(Context context) {
        super(context);
    }
    public SlideVerificateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public SlideVerificateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            return;
        }
        if (reCalc) {
            int width = getWidth();
            int height = getHeight();

            drawBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            int length = width > height ? height : width;
            length /= 4;
            x = new Random().nextInt(width - length * 2) + length;
            y = new Random().nextInt(height - length * 2) + length;

            left = x;
            top = y;
            right = left + length;
            bottom = top + length;

            slideBitmap = Bitmap.createBitmap(drawBitmap, left, top, length, length);

            moveMax = width - length;

            trueX = x;
            reCalc = false;
        }

        Paint paint=new Paint();
        canvas.drawBitmap(drawBitmap, 0, 0, paint);//画背景图
        paint.setColor(Color.parseColor("#55000000"));
        canvas.drawRect(left, top, right, bottom, paint);//画上阴影
        paint.setColor(Color.parseColor("#ffffffff"));
        canvas.drawBitmap(slideBitmap, moveX, y, paint);//画验证图片
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setMove(double percent) {
        if (percent < 0 || percent > 1) {
            return;
        }
        moveX = (int) (moveMax * percent);
        invalidate();
    }

    public void isTheRightRange() {
        if (moveX > trueX * (1 - accuracy) && moveX < trueX * (1 + accuracy)) {
            verificateResult.onSuucess();
        } else {
            verificateResult.onError();
        }
    }

    public void setRedraw() {
        moveX = 0;
        reCalc = true;
        invalidate();
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    interface VerificateResult {
        public void onSuucess();
        public void onError();
    }

    public void setVerficateResult(VerificateResult verficateResult) {
        this.verificateResult = verficateResult;
    }
}
