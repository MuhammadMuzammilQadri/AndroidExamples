package muzammil.muhammad.com.gifloader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by Muhammad Muzammil on 20/01/16.
 */

public class AlMeezanProgressDialog extends DialogFragment {

    private LayoutInflater mInflater;
    private onDismissDialogListener mOnDismissDialogListener;

    public AlMeezanProgressDialog() {
    }

    public static AlMeezanProgressDialog newInstance() {
        AlMeezanProgressDialog alMeezanProgressDialog = new AlMeezanProgressDialog();
        return alMeezanProgressDialog;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_Dialog);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Dimmed background
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.7f; // dim only a little bit
        window.setAttributes(params);

        // Transparent background
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Put your dialog layout in R.layout.dialog_more
        mInflater = inflater;
        View view = inflater.inflate(R.layout.dialog_almeezanprogressdialog, container, false);

        initializeComponents(view);
        setUpListeners();

        return view;
    }


    private void setUpListeners() {

    }

    private void initializeComponents(View view) {


    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissDialogListener != null)
            mOnDismissDialogListener.onDismissDialog();

    }

    public void setOnDismissDialogListener(onDismissDialogListener onDismissDialogListener) {
        mOnDismissDialogListener = onDismissDialogListener;
    }

    public interface onDismissDialogListener {
        void onDismissDialog();
    }
}
