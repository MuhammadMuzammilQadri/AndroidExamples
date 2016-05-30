package com.example.muhammadmuzammil.ondrawpractise;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Muhammad Muzammil on 04-May-16.
 */
public class ThreeDotsLoader extends View {

    Paint dot1;
    Paint dot2;
    Paint dot3;

    int defaultColor;
    int highlightColor;

    boolean animationStarted = false;

    TimerTask task;
    Timer timer;

    int count = 1;

    public ThreeDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultColor = Color.rgb(44, 78, 82);
        highlightColor = Color.WHITE;

        initPaint();
        startLoading();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("custom", "ondraw");
        canvas.drawCircle(50, 50, 15, dot1);
        canvas.drawCircle(100, 50, 15, dot2);
        canvas.drawCircle(150, 50, 15, dot3);
    }

    private void initPaint() {
        dot1 = new Paint();
        dot1.setColor(defaultColor);
        dot1.setStyle(Paint.Style.FILL);
        dot1.setAntiAlias(true);


        dot2 = new Paint();
        dot2.setColor(defaultColor);
        dot2.setStyle(Paint.Style.FILL);
        dot2.setAntiAlias(true);

        dot3 = new Paint();
        dot3.setColor(defaultColor);
        dot3.setStyle(Paint.Style.FILL);
        dot3.setAntiAlias(true);
    }

    public void startLoading() {
        task = new TimerTask() {

            @Override
            public void run() {

                switch (count) {
                    case 1:
                        dot1.setColor(highlightColor);
                        dot2.setColor(defaultColor);
                        dot3.setColor(defaultColor);
                        count++;
                        break;
                    case 2:
                        dot1.setColor(defaultColor);
                        dot2.setColor(highlightColor);
                        dot3.setColor(defaultColor);
                        count++;
                        break;
                    case 3:
                        dot1.setColor(defaultColor);
                        dot2.setColor(defaultColor);
                        dot3.setColor(highlightColor);
                        count = 1;
                        break;
                }
                animationStarted = true;
                postInvalidate();
            }
        };
        timer = new Timer();
        timer.schedule(task, 0, 300);

    }

    public void stopLoading() {
        animationStarted = false;
        timer.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("MuzammilQadri", "X: " + event.getX() + "Y: " + event.getY());
        return super.onTouchEvent(event);
    }
}
