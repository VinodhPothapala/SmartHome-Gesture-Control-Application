package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import static com.example.homework2.LoginPage.LAST_NAME;

public class DisplayVideo extends AppCompatActivity {
    VideoView vv;
    String filePath;
    public Uri videoUri;
    String gesture_text;
    String uri;
    String ipv4AddressServer="192.168.0.76";
    String portNumber="5000";
    String lastName="";
    ProgressBar progressBar;
    TextView progressText;
    public static Map<String, Integer> counterMap = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_video);
        lastName = LAST_NAME;
        vv = findViewById(R.id.displayVideo);
        progressText = findViewById(R.id.progressView);
        Bundle extras = getIntent().getExtras();
        if(extras == null){
            filePath ="";
            gesture_text="";
            uri = "";
        }else{
            filePath = extras.getString("path");
            gesture_text = extras.getString("gesture_name");
            uri = extras.getString("videoUri");
        }
        String sampleUri = uri;
        videoUri = Uri.parse(sampleUri);
        vv.setVideoURI(videoUri);
        vv.setMediaController(new MediaController(this));
        vv.requestFocus();
        vv.start();
        httpMultiFromRequestBody(filePath);
    }
    public void postRequest(String postUrl,RequestBody httpRequestBody){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(postUrl)
                .post(httpRequestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DisplayVideo.this, "Connection was not established", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        progressText.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DisplayVideo.this, "Video uploaded into server", Toast.LENGTH_SHORT).show();
                        progressText.setVisibility(View.INVISIBLE);
                        Intent backToSquareOne = new Intent(DisplayVideo.this, MainActivity.class);
                        finish();
                        startActivity(backToSquareOne);
                    }
                });
            }
        });

    }

    public void httpMultiFromRequestBody(String videoUri){
        Button upload_video = findViewById(R.id.uploadbutton);
        upload_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(checkUploadCount()) {
                    String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(permissions, 1);
                    }
                    progressText.setVisibility(View.VISIBLE);
                    if (counterMap.get(gesture_text) == null) {
                        counterMap.put(gesture_text, 1);
                    } else {
                        Integer num = counterMap.get(gesture_text);
                        counterMap.put(gesture_text, num + 1);
                    }

                    String postUrl = "http://" + ipv4AddressServer + ":" + portNumber + "/";

                    File stream = null;
                    RequestBody postBodyImage = null;
                    try {
                        stream = new File(videoUri);

                        postBodyImage = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("image",
                                        gesture_text + "_PRACTICE_" + counterMap.get(gesture_text) +
                                                "_"+lastName + ".mp4", RequestBody.create(MediaType.parse("video/*"), stream))
                                .build();

                    } catch (Exception ioexp) {
                        ioexp.printStackTrace();
                    }

                    postRequest(postUrl, postBodyImage);
            }
        });

    }
}