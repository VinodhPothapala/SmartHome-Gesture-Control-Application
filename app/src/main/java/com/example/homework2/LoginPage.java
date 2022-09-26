package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    public static String LAST_NAME = "";
    Button letsgo_btn;
    TextView last_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        letsgo_btn = findViewById(R.id.letsgo_btn);
        last_name = findViewById(R.id.lastName);
        navigateToMain();
    }
    public void navigateToMain(){
        letsgo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LAST_NAME = last_name.getText().toString();
                if(LAST_NAME!=null && LAST_NAME.length()>0){
                    Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                    Toast.makeText(getApplicationContext(),"Learn Gestures",Toast.LENGTH_LONG).show();
                    startActivity(mainIntent);
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter your Name to continue",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}