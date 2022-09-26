package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static String EXTRA_TEXT = "com.example.Application2";
    Spinner ges_list;
    public void watch_video(View view) {
        ges_list = (Spinner) findViewById(R.id.gestures_dropdown);
        String text = ges_list.getSelectedItem().toString();
        Intent startVideoActivity = new Intent(this, PracticeVideo.class);
        startVideoActivity.putExtra(EXTRA_TEXT, text);
        startActivity(startVideoActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}