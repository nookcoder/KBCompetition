package com.kbc.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Image.Image;
import com.kbc.R;
import com.squareup.picasso.Picasso;

//인트로 화면
public class Intro_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();
            }
        },2000);
        ImageView imageView = findViewById(R.id.logo);
        Picasso.get()
                .load(R.drawable.app_logo)
                .centerInside()
                .resize(1000,1000)
                .into(imageView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}