package com.kbc;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;



public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        // 라디오그룹 참조
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        Button login = (Button)findViewById(R.id.loginButton);
        // 클릭이벤트
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                RadioButton select = (RadioButton)findViewById(id);
                login.setTextColor(Color.parseColor("#FFC11B"));
            }
        });
    }
}
