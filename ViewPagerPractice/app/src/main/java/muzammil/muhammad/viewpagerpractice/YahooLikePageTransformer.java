package muzammil.muhammad.viewpagerpractice;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Muhammad Muzammil on 14-Nov-15.
 */
public class YahooLikePageTransformer implements ViewPager.PageTransformer {
    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        if(imageView != null)
        imageView.setTranslationX(-position * (pageWidth / 2)); //Half the normal speed
    }
}
