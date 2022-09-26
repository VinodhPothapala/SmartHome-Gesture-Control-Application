package com.example.homework2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import static com.example.homework2.MainActivity.EXTRA_TEXT;

public class PracticeVideo extends AppCompatActivity {

    String old_text = "";
    String path;
    long time_started = 0;
    VideoView vv_learn;
    Spinner ges_list;
    String filePath;
    String text;
    Intent videoIntent;
    Integer count =0;
    Button practice_btn;
    private static int VIDEO_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_video);
        ges_list = (Spinner) findViewById(R.id.gestures_dropdown);
        vv_learn = (VideoView) findViewById(R.id.practiceView);
        TextView textView = (TextView) findViewById(R.id.textView);
        String head = textView.getText().toString();
        Intent startVideoActivity = getIntent();
        text = startVideoActivity.getStringExtra(MainActivity.EXTRA_TEXT);
        practice_btn = findViewById(R.id.Practice_Button);
        MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    count = count+1;
                    if(count>=3){
                        practice_btn.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        vv_learn.setOnCompletionListener(onCompletionListener);
        vv_learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vv_learn.isPlaying()) {
                    vv_learn.start();
                }
            }
        });
        String text = startVideoActivity.getStringExtra(MainActivity.EXTRA_TEXT);
        head = head + " -> " + text;
        textView.setText(head);
        if (!old_text.equals(text)) {
            path = "";
            time_started = System.currentTimeMillis();
            play_video(text);
        }
    }

    public void play_video(String text) {
        old_text = text;
        if (text.equals("LightOn")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.lighton;
        } else if (text.equals("LightOff")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.lightoff;
        } else if (text.equals("FanOn")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.fanon;
        } else if (text.equals("FanOff")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.fanoff;
        } else if (text.equals("FanUp")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.fanup;
        } else if (text.equals("FanDown")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.fandown;
        } else if (text.equals("SetThermo")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.setthermo;
        } else if (text.equals("Num0")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num0;
        } else if (text.equals("Num1")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num1;
        } else if (text.equals("Num3")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num3;
        } else if (text.equals("Num4")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num4;
        } else if (text.equals("Num5")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num5;
        } else if (text.equals("Num6")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num6;
        } else if (text.equals("Num7")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num7;
        } else if (text.equals("Num8")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num8;
        } else if (text.equals("Num9")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num9;
        } else if (text.equals("Num2")) {
            path = "android.resource://" + getPackageName() + "/" + R.raw.num2;
        }
        if (!path.isEmpty()) {
            Uri uri = Uri.parse(path);
            vv_learn.setVideoURI(uri);
            vv_learn.start();
        }

    }

    public void practice_ges(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        101);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        100);
            }

        } else {
            Toast.makeText(getApplicationContext(),"Camera Opened", Toast.LENGTH_LONG).show();
            File f = new File(Environment.getExternalStorageDirectory(), "homework2");
            if (!f.exists()) {
                f.mkdirs();
            }
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/homework2/"
                    +EXTRA_TEXT+"_PRACTICE_"+ ".mp4");
            videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            videoIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            videoIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            videoIntent.putExtra(MediaStore.EXTRA_OUTPUT, file.getPath());
            videoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
            videoIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            videoIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(videoIntent, VIDEO_REQUEST);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VIDEO_REQUEST) {
            Uri VideoUri = data.getData();
            filePath = getPathFromURI(getApplicationContext(), VideoUri);
            Intent displayIntent = new Intent(getApplicationContext(),DisplayVideo.class);
            displayIntent.putExtra("path", filePath);
            displayIntent.putExtra("gesture_name", text);
            displayIntent.putExtra("videoUri", VideoUri.toString());
            startActivity(displayIntent);
        }
    }
    public String getPathFromURI(Context context, Uri contentUri) {
        if ( contentUri.toString().indexOf("file:///") > -1 ){
            return contentUri.getPath();
        }
        Cursor thisCursor = null;
        try {
            String[] temp = { MediaStore.Images.Media.DATA };
            thisCursor = context.getContentResolver().query(contentUri,  temp, null, null, null);
            int column_index = thisCursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            thisCursor.moveToFirst();
            return thisCursor.getString(column_index);
        }finally {
            if (thisCursor != null) {
                thisCursor.close();
            }
        }
    }
}