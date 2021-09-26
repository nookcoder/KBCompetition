package com.kbc.Common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Image.Image;
import com.kbc.R;
//인트로 화면
public class Intro_Activity extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        image = (ImageView) findViewById(R.id.logo);
        Animation ani = AnimationUtils.loadAnimation(getApplicationContext() , R.anim.intro);
        image.startAnimation(ani);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login_Activity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}