package com.example.muhammadmuzammil.ondrawpractise;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Muhammad Muzammil on 04-May-16.
 */
class TouchToCreateCirlceView extends View {

    Paint vertexPaint;
    int counter;
    static boolean isItFirstTime = true;

    Runnable runnable;
    private Context context;
    private int mWidth;
    private int mSizeFactor;
    private float mX;
    private float mY;

    //animation velocity = 200px / second.
    public TouchToCreateCirlceView(Context context) {
        super(context);
        intializeComponents(context);
    }

    public TouchToCreateCirlceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intializeComponents(context);
    }

    public TouchToCreateCirlceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intializeComponents(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TouchToCreateCirlceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        intializeComponents(context);
    }


    private void intializeComponents(Context context) {
        this.context = context;
        vertexPaint = new Paint();
        vertexPaint.setAntiAlias(true);
        vertexPaint.setColor(Color.parseColor("black"));
        vertexPaint.setStyle(Paint.Style.FILL);
        vertexPaint.setStrokeJoin(Paint.Join.MITER);
        vertexPaint.setStrokeCap(Paint.Cap.ROUND);
        vertexPaint.setStrokeWidth(55);
        runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                counter+=2;
                invalidate();
            }
        };

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mSizeFactor = (int) mWidth / 500;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d("MuzammilQadri", "in onDraw");
        if (isItFirstTime) {
            isItFirstTime = false;
            return;
        }

        this.removeCallbacks(runnable);

        int alpha = (int) ((1 - counter/100f)*255f);

        if (alpha < 0)
            return;

        vertexPaint.setAlpha(alpha);
        int endX = (int) (mSizeFactor * counter);
        canvas.drawCircle(mX, mY, endX, vertexPaint);

//        canvas.drawPoint(endX, 50, vertexPaint);
//        canvas.drawArc(endX, 50, vertexPaint);
//        canvas.drawBitmap(fireIcon, endX - 6, 100 - 12, vertexPaint);

        if (counter < 100)
            this.postDelayed(runnable, 1000 / 200);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        counter = 0;
        mX = event.getX();
        mY = event.getY();
        invalidate();

        return super.onTouchEvent(event);
    }


}
