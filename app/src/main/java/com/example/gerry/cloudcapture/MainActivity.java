package com.example.gerry.cloudcapture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    //Variable initialization
    private ImageView result;
    private VideoView mVideoPlayBack;
    ImageButton photoButton, recordButton, playVideoButton;
    private int ACTIVITY_START_CAMERA_APP = 0;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This creates the initialization for the camera on app startup
        photoButton = (ImageButton)findViewById(R.id.imageView);
        recordButton = (ImageButton) findViewById(R.id.videoView);
        playVideoButton = (ImageButton) findViewById(R.id.replay);

        mVideoPlayBack = (VideoView) findViewById(R.id.videoPlayBack);
        result = (ImageView)findViewById(R.id.imageView);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeVideo = new Intent();
                takeVideo.setAction(MediaStore.ACTION_VIDEO_CAPTURE);

                startActivityForResult(takeVideo, ACTIVITY_START_CAMERA_APP);
            }
        });

        playVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoPlayBack.start();
            }
        });
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //This method will create an activity for the "result" which we will set to the photo taking screen
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            result.setImageBitmap(imageBitmap);
        }
        else if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            //These lines control video playback
            Uri videoUri = data.getData();
            mVideoPlayBack.setVideoURI(videoUri);
        }
    }


}
