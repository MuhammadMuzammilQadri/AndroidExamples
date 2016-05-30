package com.example.muhammadmuzammil.ondrawpractise;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;

/**
 * Created by Muhammad Muzammil on 04-May-16.
 */
class LinearProgressView extends View {

    Handler mHandler = new Handler();
    Paint vertexPaint;
    int counter;

    Bitmap fireIcon;
    Runnable runnable;
    private Context context;
    private int mWidth;
    private int mFactor;

    //animation velocity = 200px / second.
    public LinearProgressView(Context context) {
        super(context);
        intializeComponents(context);
    }

    public LinearProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intializeComponents(context);
    }

    public LinearProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intializeComponents(context);
    }

    public LinearProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        intializeComponents(context);
    }


    private void intializeComponents(Context context) {
        this.context = context;
        fireIcon = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
        vertexPaint = new Paint();
        vertexPaint.setAntiAlias(true);
        vertexPaint.setColor(0xFF66009A);
        vertexPaint.setStyle(Paint.Style.STROKE);
        vertexPaint.setStrokeJoin(Paint.Join.MITER);
        vertexPaint.setStrokeCap(Paint.Cap.ROUND);
        vertexPaint.setStrokeWidth(55);
        runnable = new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                counter++;
                invalidate();
            }
        };

        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mWidth = size.x;
        mWidth -= 50;
        mFactor = (int) mWidth / 100;
        Log.d("MuzammilQadri", "mWidth: " + mWidth);
        Log.d("MuzammilQadri", "mFactor: " + mFactor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int endX = mFactor * counter - 50;
        endX = Math.max(50, endX);
        canvas.drawLine(50, 50, endX, 50, vertexPaint);
//        canvas.drawPoint(endX, 50, vertexPaint);
//        canvas.drawArc(endX, 50, vertexPaint);
//        canvas.drawBitmap(fireIcon, endX - 6, 100 - 12, vertexPaint);

        Log.d("MuzammilQadri", "endX: " + endX);

//        if (endX < mWidth)
        if (counter < 100)
            mHandler.postDelayed(runnable, 1000 / 50);
    }



    /**
     * Invoke this method to animate the line.
     */
    public void lightCracker() {

        counter = 0;
        invalidate();
    }

    private class Line {
        int startX, startY;
        int endX, endY;


    }
}
