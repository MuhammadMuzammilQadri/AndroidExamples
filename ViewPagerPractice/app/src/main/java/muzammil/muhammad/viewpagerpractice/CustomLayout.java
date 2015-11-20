package muzammil.muhammad.viewpagerpractice;

/**
 * Created by Muhammad Muzammil on 14-Nov-15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomLayout extends View {
    Context context;
    AttributeSet attrs;

    public CustomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        Log.i("MuzammilQadri", "in CustomLayout's constructor");

    }

    public CustomLayout(Context context) {
        super(context);
        this.context = context;
        Log.i("MuzammilQadri", "in CustomLayout's constructor");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.skew(0f, 0.15f);

        Bitmap bitmap1;
          float density = getResources().getDisplayMetrics().density;
        Matrix matrix = new Matrix();
//        matrix.postSkew(0f, 0.1f);  //dont need to used it.. i.e dont need to anti skew anything..

        Paint mSelectedIndicatorPaint= new Paint();
        int myColor =
                context.getResources().getColor(R.color.white);
        mSelectedIndicatorPaint.setColor(myColor);
        bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.bottompart);
        bitmap1 = Bitmap.createScaledBitmap(bitmap1, getWidth(), Math.round(300 * density), true);
        bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);

        canvas.drawBitmap(bitmap1, 0, 0, null);



//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//        paint.setTextSize(26f);
//        paint.setColor(Color.parseColor("#ffffff"));
//
//        canvas.drawText("Canvas text", (getWidth() / 6) , 10 + getHeight() / 2, paint);
        //change values to suit your needs
//        Drawable drawable= getDrawable();
//        Bitmap output = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
//                Bitmap.Config.ARGB_8888);
//        canvas.drawBitmap(output, 0, 0, null);
//        canvas.skew(-1.0f, -0.3f);

    }
}