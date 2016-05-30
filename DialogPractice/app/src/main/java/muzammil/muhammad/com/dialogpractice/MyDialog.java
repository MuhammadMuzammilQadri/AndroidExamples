package muzammil.muhammad.com.dialogpractice;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ehsan Umar on 4/18/2016.
 */
public class MyDialog extends DialogFragment {
    private View source;

    public MyDialog(View viewById) {
        source =  viewById;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_mydialog, null);
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).setView(R.layout.dialog_mydialog).create();
        return alertDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setDialogPosition();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void setDialogPosition() {
        if (source == null) {
            return; // Leave the dialog in default position
        }

        source.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // Find out location of source component on screen
        // see http://stackoverflow.com/a/6798093/56285
//        int[] location = new int[2];
//        source.getLocationOnScreen(location);

        final Window window = getDialog().getWindow();

//        // set "origin" to top left corner
//        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        final WindowManager.LayoutParams params = window.getAttributes();

        source.post(new Runnable() {
            @Override
            public void run() {
                // Just an example; edit to suit your needs.
//                params.x = (int) source.getX(); // about half of confirm button size left of source view
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int height = size.y;
                int sourceParentHeight = ((View)source.getParent()).getHeight();
                params.y = ((int) source.getY()) - sourceParentHeight/2 ; // above source view
                //Subtracting upper blank space of dialog so that dialog 'y' can be set from source's layout top.

                window.setAttributes(params);

            }
        });

    }
}
