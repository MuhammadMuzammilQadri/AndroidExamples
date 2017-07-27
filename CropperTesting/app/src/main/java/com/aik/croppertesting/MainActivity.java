package com.aik.croppertesting;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.aik.croppertesting.databinding.ActivityMainBinding;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
    }

    private void init() {

        binding.cropImageView.setImageResource(R.drawable.cat);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
                    @Override
                    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
                        Bitmap cropped = result.getBitmap();
                        binding.iv.setImageBitmap(cropped);
                    }
                });

                // subscribe to async event using cropImageView.setOnCropImageCompleteListener(listener)
                binding.cropImageView.getCroppedImageAsync();
            }
        });

        binding.rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.cropImageView.rotateImage(90);
            }
        });

    }
}
