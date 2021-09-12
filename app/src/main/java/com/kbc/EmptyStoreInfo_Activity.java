package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class EmptyStoreInfo_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //화면 셋팅
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_store);

        //등록하러가기 버튼
        Button goToAdd = (Button) findViewById(R.id.addStoreInfo);
        goToAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환
                Intent intent1 = new Intent(getApplicationContext(),StoreManagaer_Add_Store_Info_Activity.class);
                startActivity(intent1);
            }
        });
    }
}
