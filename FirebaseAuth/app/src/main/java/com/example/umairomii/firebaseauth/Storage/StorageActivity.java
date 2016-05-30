package com.example.umairomii.firebaseauth.Storage;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.umairomii.firebaseauth.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by umairomii on 5/28/16.
 */
public class StorageActivity extends AppCompatActivity {

    Button brawseImage, brawseFile, uploadImage, uploadFile, downloadImage, downloadFile;
    ImageView uploadedImage, downloadedImage;
    TextView uploadedFilename, downloadedFilename;
    private File selectedFile;
    private Uri selectedImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        init();
        setupListeners();

    }

    private void setupListeners() {
        brawseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);
            }
        });
        brawseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
                fileIntent.setType("file/*");
                startActivityForResult(fileIntent, 2);
            }
        });

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Method 1
//                uploadFileToFileStorage(v);

//              Method 2
                try {
                    uploadFileToFileStorageMethod2(v);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }


        });

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            TODO
            }
        });

        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            TODO
            }
        });

        downloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            TODO
            }
        });
    }

    private void uploadFileToFileStorageMethod2(final View v) throws FileNotFoundException {

        final ProgressDialog progressDialog = ProgressDialog.show(StorageActivity.this,"","uploading",false);
        String path  = getFilePath();
        InputStream is = new FileInputStream(new File(path));

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-3testing.appspot.com");


        UploadTask uploadTask = storageRef.child("method2").putStream(is);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(v,""+e.getMessage(),Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Snackbar.make(v,"success uploadeing",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getFilePath() {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(selectedImage);
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

    private void uploadFileToFileStorage(final View v) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-3testing.appspot.com");

        final ProgressDialog progressDialog = ProgressDialog.show(StorageActivity.this,"","uploading",false);
        uploadedImage.setDrawingCacheEnabled(true);
        uploadedImage.buildDrawingCache();
        Bitmap b = uploadedImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bytes = baos.toByteArray();
        UploadTask uploadTask = storageRef.child("images/space.jpg").putBytes(bytes);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Snackbar.make(v,""+e.getMessage(),Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                progressDialog.dismiss();
                Snackbar.make(v,"success uploadeing",Snackbar.LENGTH_LONG).setAction("Action",null).show();
            }
        });
    }

    private void init() {
        brawseImage = (Button) findViewById(R.id.browseImage);
        brawseFile = (Button) findViewById(R.id.browseFile);

        uploadImage = (Button) findViewById(R.id.uploadImage);
        uploadFile = (Button) findViewById(R.id.uploadFile);

        downloadImage = (Button) findViewById(R.id.downloadImage);
        downloadFile = (Button) findViewById(R.id.downloadFile);


        downloadedImage = (ImageView) findViewById(R.id.downloadedImage);

        uploadedFilename = (TextView) findViewById(R.id.uploadedFilename);
        downloadedFilename = (TextView) findViewById(R.id.downloadedFilename);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            if(requestCode == 1 && resultCode == RESULT_OK && data != null){
                 selectedImage = data.getData();

                String filePath = "";
                uploadedImage = (ImageView) findViewById(R.id.uploadedImage);
                Bitmap b = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                Log.d("TAG",data.getData().getPath());
                uploadedImage.setImageBitmap(b);


            }
            if(requestCode == 2 && resultCode == RESULT_OK && data != null){
                selectedFile = new File(data.getData().getPath());
                uploadedFilename.setText(selectedFile.getName()+"");
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
}
