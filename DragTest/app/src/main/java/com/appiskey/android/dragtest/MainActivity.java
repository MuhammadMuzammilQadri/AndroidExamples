package com.appiskey.android.dragtest;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private View view1;
    private View view2;
    private ViewGroup mRrootLayout;

    ImageView imageView;
    Button button;

    Bitmap bitmap1;

    int x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRrootLayout = (ViewGroup) findViewById(R.id.root);
        view1 = findViewById(R.id.v1);
        view2 = findViewById(R.id.v2);
        imageView = (ImageView) findViewById(R.id.IV);
        button = (Button) findViewById(R.id.button);

        Log.v("test",mRrootLayout.getWidth()+" "+ mRrootLayout.getHeight());

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(150, 150);
        view1.setLayoutParams(layoutParams);
        view1.setOnTouchListener(new View.OnTouchListener() {

            private int _xDelta;
            private int _yDelta;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();

                x1 = X;
                y1  = Y;

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                                .getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        v.setLayoutParams(layoutParams);
                        break;
                }
//                mRrootLayout.invalidate();
                return true;
            }
        });

        layoutParams = new RelativeLayout.LayoutParams(150, 150);
        view2.setLayoutParams(layoutParams);
        view2.setOnTouchListener(new View.OnTouchListener() {

            private int _xDelta;
            private int _yDelta;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int X = (int) event.getRawX();
                final int Y = (int) event.getRawY();

                x2 = X;
                y2 = Y;
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                        _xDelta = X - lParams.leftMargin;
                        _yDelta = Y - lParams.topMargin;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;
                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v
                                .getLayoutParams();
                        layoutParams.leftMargin = X - _xDelta;
                        layoutParams.topMargin = Y - _yDelta;
                        layoutParams.rightMargin = -250;
                        layoutParams.bottomMargin = -250;
                        v.setLayoutParams(layoutParams);
                        break;
                }
//                mRrootLayout.invalidate();
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });

    }


    void click() {

        Bitmap bitmap1 = Bitmap.createScaledBitmap(
                ((BitmapDrawable) imageView.getDrawable()).getBitmap(),
                mRrootLayout.getWidth(),
                mRrootLayout.getHeight(),
                false);

        Bitmap bitmap = Bitmap.createBitmap(bitmap1,
                view1.getTop(),
                view1.getLeft(),
                view1.getWidth(),
                view1.getHeight()
        );

        imageView.setImageBitmap(bitmap);
    }

}
