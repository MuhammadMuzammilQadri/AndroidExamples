package com.muhammad.muzamil.galleryimagehandlingwithrecyclerview;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.muhammad.muzamil.galleryimagehandlingwithrecyclerview.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    HorizontalImageRecyclerViewAdapter adapter;
    private Cursor cc;
    private ProgressDialog myProgressDialog;
    private ArrayList<Image> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        fetchImagesFromGalleryAndUpdateAdapter();


    }

    private void fetchImagesFromGalleryAndUpdateAdapter() {
        cc = this.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,
                null);

        // File[] files=f.listFiles();
        if (cc != null) {

            myProgressDialog = new ProgressDialog(MainActivity.this);
            myProgressDialog.setMessage(getResources().getString(R.string.pls_wait_txt));
            //myProgressDialog.setIcon(R.drawable.blind);
            myProgressDialog.show();

            new Thread() {
                public void run() {
                    try {
                        String strUrl, name;
                        Uri url;
                        cc.moveToFirst();
                        for (int i = 0; i < cc.getCount(); i++) {
                            cc.moveToPosition(i);
                            url = Uri.parse(cc.getString(1));
                            strUrl = cc.getString(1);
                            name = cc.getString(3);

                            arrayList.add(new Image(url, strUrl, name));
                        }

                    } catch (Exception e) {
                    }

                    adapter = new HorizontalImageRecyclerViewAdapter(arrayList, new RecyclerViewBaseAdapter.ItemSelectedListener<Image>() {
                        @Override
                        public void onItemSelected(int postion, Image listItem) {
                            Toast.makeText(MainActivity.this, "Postion: " + postion, Toast.LENGTH_SHORT).show();
                        }
                    });
                    binding.myHistoryRecyclerView.setAdapter(adapter);

                    myProgressDialog.dismiss();
                }
            }.start();

        }
    }
}
