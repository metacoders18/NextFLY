package com.metacoders.nextfly;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {
    TextView mTitleTv, mDetailTv;
    ImageView mImageIv;

    Bitmap bitmap;

    Button mSaveBtn, mShareBtn, mWallBtn;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        //initialize views
        mTitleTv = findViewById(R.id.titleTv);
        mDetailTv = findViewById(R.id.descriptionTv);
        mImageIv = findViewById(R.id.imageView);
        mSaveBtn = findViewById(R.id.saveBtn);
        mShareBtn = findViewById(R.id.shareBtn);
        //   mWallBtn = findViewById(R.id.wallBtn);

        //get data from intent
        String image = getIntent().getStringExtra("image");
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("description");
        //    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        //set data to views
        mTitleTv.setText(title);
        mDetailTv.setText(desc);
        Picasso.get().load(image).into(mImageIv);
        //   mImageIv.setImageBitmap(bmp);

        //get image from imageview as bitmap
        //  bitmap = ((BitmapDrawable) mImageIv.getDrawable()).getBitmap();

        //save btn click handle
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if os is >= marshmallow we need runtime permission to save image
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        //show popup to grant permission
                        requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);
                    } else {
                        //permission already granted, save image
                        saveImage();
                    }
                } else {
                    //System os is < marshmallow, save image
                    saveImage();
                }
            }
        });
        //share btn click handle
        mShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage();
            }
        });
        //set wallpaper btn click handle


    }
    /*
        private void setImgWallpaper() {

            //get image from
            bitmap = ((BitmapDrawable)mImageIv.getDrawable()).getBitmap();

            WallpaperManager myWallManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                myWallManager.setBitmap(bitmap);
                Toast.makeText(this, "Wallpaper set...", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    */
    private void shareImage() {
        //get image from
        bitmap = ((BitmapDrawable)mImageIv.getDrawable()).getBitmap();

        try {
            //get title and description and save in string s
            String s = mTitleTv.getText().toString() + "\n \n" + mDetailTv.getText().toString();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain") ;
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"My App");
            shareIntent.putExtra(Intent.EXTRA_TEXT,s);
            startActivity(Intent.createChooser(shareIntent ,"Share Via"));




/*
            File file = new File(getExternalCacheDir(), "sample.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            //intent to share image and text
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_TEXT, s); // put the text
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Share via"));
            */
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveImage() {

        //get image from
        bitmap = ((BitmapDrawable)mImageIv.getDrawable()).getBitmap();
        //time stamp, for image name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        //path to external storage
        File path = Environment.getExternalStorageDirectory();
        //create folder named "Firebase"
        File dir = new File(path + "/JobApp/");
        dir.mkdirs();
        //image name
        String imageName = timeStamp + ".PNG";
        File file = new File(dir, imageName);
        OutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 95, out);
            out.flush();
            out.close();
            Toast.makeText(this, imageName + " saved to" + dir, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            //failed saving image
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //handle onBackPressed(go to previous activity)
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE: {
                //if request code is cancelled the result arrays are empty
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission is granted, save image
                    saveImage();
                } else {
                    //permission denied
                    Toast.makeText(this, "enable permission to save image", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }




}