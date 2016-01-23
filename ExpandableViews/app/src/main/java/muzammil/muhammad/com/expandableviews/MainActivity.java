package muzammil.muhammad.com.expandableviews;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExpandablePanel panel = (ExpandablePanel)findViewById(R.id.foo);
        panel.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                mImageView = (ImageView) handle;
//                mImageView.setImageResource(R.drawable.down);
                rotateHandle();
            }

            public void onExpand(View handle, View content) {
                mImageView = (ImageView) handle;
//                mImageView.setImageResource(R.drawable.up);
                rotateHandle();

            }
        });
    }

    private void rotateHandle() {
        Log.d("MuzammilQadri", mImageView.getRotation()+"");
        if (mImageView.getRotation() == 360f)
            mImageView.setRotation(0);
        float toRotate;
        toRotate = mImageView.getRotation() == 0 ? 180 : 360;
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", toRotate);
        objectAnimator.setDuration(200);
        objectAnimator.start();


    }
}
