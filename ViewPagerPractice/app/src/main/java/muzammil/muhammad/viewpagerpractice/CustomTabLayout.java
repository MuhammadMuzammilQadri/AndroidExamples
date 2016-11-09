
/**
 * Created by Muhammad Muzammil on 24-Nov-15.
 */

package muzammil.muhammad.viewpagerpractice;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CustomTabLayout extends TabLayout {
    DisplayMetrics displaymetrics = new DisplayMetrics();

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }


//    @Override
//    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);
//        View firstTab = ((ViewGroup)getChildAt(0)).getChildAt(0);
//        View lastTab = ((ViewGroup)getChildAt(0)).getChildAt(((ViewGroup)getChildAt(0)).getChildCount()-1);
//        ViewCompat.setPaddingRelative(getChildAt(0), (getWidth() / 2) - (firstTab.getWidth() / 2), 0, (getWidth() / 2) - (lastTab.getWidth() / 2), 0);
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            if (getTabCount() == 0)
                return;
            Field mTabMinWidthField = TabLayout.class.getDeclaredField("mTabMinWidth");
            mTabMinWidthField.setAccessible(true);
            mTabMinWidthField.set(this, (int) (getMeasuredWidth() / (float) (getTabCount() / 1.5)));    //For dynamically managing tab width..


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Field mContentInsetStartField = null;

            mContentInsetStartField =TabLayout.class.getDeclaredField("mContentInsetStart"); //Reflection intro -- http://docs.oracle.com/javase/tutorial/reflect/index.html
            Method reflectMethod= TabLayout.class.getDeclaredMethod("applyModeAndGravity");  //Reflection intro -- http://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful
//                Field tempField = TabLayout.class.getDeclaredField("mTabMaxWidth");
//                tempField.setAccessible(true);
//                int temp = tempField.getInt(this);
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int width = displaymetrics.widthPixels;

            mContentInsetStartField.setAccessible(true);
            mContentInsetStartField.set(this, width/3);

            try {
                reflectMethod.setAccessible(true);
                reflectMethod.invoke(this, null);
            }
            catch (Exception e){
                Log.e("MuzammilQadri", "reflectMethod returned error.. Message: " + e.getMessage());

            }
        } catch (Exception e) {
            Log.e("MuzammilQadri","mContentInsetStart not found..");
            e.printStackTrace();
        }

    }
}
