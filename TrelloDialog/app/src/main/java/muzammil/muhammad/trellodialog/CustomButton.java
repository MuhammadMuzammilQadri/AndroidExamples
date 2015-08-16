package muzammil.muhammad.trellodialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by panacloud on 8/10/15.
 */
public class CustomButton extends Button {

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attributes) {
        super(context, attributes);
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int maskedAction = event.getActionMasked();
        if (maskedAction == MotionEvent.ACTION_DOWN)
            getBackground().setColorFilter(Color.argb(150, 155, 155, 155), PorterDuff.Mode.DST_IN);
        else if (maskedAction == MotionEvent.ACTION_UP)
            getBackground().setColorFilter(null);
        return super.onTouchEvent(event);
    }}
