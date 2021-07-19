package com.example.cam_ex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;



import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    Button mButtonPic;
    Uri photoUri;
    private static int REQUEST_IMAGE_CAPTURE = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonPic = findViewById(R.id.btn_pic);
        mButtonPic.setOnClickListener(v -> takePhoto());
    }


    private void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createImageFile();

            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this
                        , "com.example.cam_ex.FileProvider",
                        photoFile);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }


    }



    private File createImageFile() {

        return new File( Objects.requireNonNull(createFolder()).getPath(),"abc.jpeg");
    }

    public  File createFolder() {
        File myFolder = new File(Environment.getExternalStorageDirectory(), "faraKhalili");
        if (!myFolder.exists()) {
            if(myFolder.mkdirs()){
                return myFolder;
            } else {
                return null;
            }
        }
        return myFolder;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String l = photoUri.toString();
            Log.d(TAG, "onActivityResult: " + l);
        }
    }
}