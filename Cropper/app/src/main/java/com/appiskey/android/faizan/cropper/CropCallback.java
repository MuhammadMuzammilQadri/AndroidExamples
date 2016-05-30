package com.appiskey.android.faizan.cropper;

import android.graphics.Bitmap;

public interface CropCallback extends Callback {
    void onSuccess(Bitmap cropped);
    void onError();
}
