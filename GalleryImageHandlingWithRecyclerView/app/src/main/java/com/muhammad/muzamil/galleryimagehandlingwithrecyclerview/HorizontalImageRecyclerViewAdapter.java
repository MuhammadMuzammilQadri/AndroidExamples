package com.muhammad.muzamil.galleryimagehandlingwithrecyclerview;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muhammad.muzamil.galleryimagehandlingwithrecyclerview.databinding.RecyclerviewRowBinding;

import java.util.List;

/**
 * Created by Muzamil on 27-Dec-16.
 */
public class HorizontalImageRecyclerViewAdapter extends RecyclerViewBaseAdapter<Image, HorizontalImageRecyclerViewAdapter.ViewHolder> {

    HorizontalImageRecyclerViewAdapter(List<Image> items, ItemSelectedListener<Image> imageItemSelectedListener){
        super(items, imageItemSelectedListener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerviewRowBinding recyclerviewRowBinding = DataBindingUtil.inflate(inflater, R.layout.recyclerview_row, parent, false);
        return new ViewHolder(recyclerviewRowBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setItem(position, list.get(position));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerviewRowBinding binding;
        public ViewHolder(RecyclerviewRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setItem(final int position, final Image image){
                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemSelectedListener.onItemSelected(position, image);
                    }
                });
            Bitmap bmp = decodeURI(image.getUrl().getPath());
            binding.img.setImageBitmap(bmp);

        }

        /**
         * This method is to scale down the image
         */
        public Bitmap decodeURI(String filePath){

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(filePath, options);

            // Only scale if we need to
            // (16384 buffer for img processing)
            Boolean scaleByHeight = Math.abs(options.outHeight - 100) >= Math.abs(options.outWidth - 100);
            if(options.outHeight * options.outWidth * 2 >= 16384){
                // Load, scaling to smallest power of 2 that'll get it <= desired dimensions
                double sampleSize = scaleByHeight
                        ? options.outHeight / 100
                        : options.outWidth / 100;
                options.inSampleSize =
                        (int)Math.pow(2d, Math.floor(
                                Math.log(sampleSize)/Math.log(2d)));
            }

            // Do the actual decoding
            options.inJustDecodeBounds = false;
            options.inTempStorage = new byte[512];
            Bitmap output = BitmapFactory.decodeFile(filePath, options);

            return output;
        }
    }





}
