package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.StoreManger.StoreManager_Add_Store_Info_Activity;

public class EmptyStoreInfo_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //화면 셋팅
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_store);

        TextView sadText = (TextView) findViewById(R.id.sadText);

        //데이터 받아오기
        Intent intentForGet = getIntent();
        String userId = intentForGet.getExtras().getString("userID");
        String user=intentForGet.getExtras().getString("user");
        Button goToAdd = (Button) findViewById(R.id.addStoreInfo);

        //화면 셋팅
        if(user.equals("사업자")) {
            sadText.setText("등록된 가게 정보가 없어요.");
            goToAdd.setText("가게 정보 등록하러 가기");
        }
        else if(user.equals("개인")) {
            sadText.setText("등록된 개인 정보가 없어요.");
            goToAdd.setText("개인 정보 등록하러 가기");
        }

        //등록하러가기 버튼
        goToAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                //화면 전환 + 데이터 전달
                Intent intent1 = null;
                //사업자 모드
                if(user.equals("사업자")) {
                    intent1 = new Intent(getApplicationContext(), StoreManager_Add_Store_Info_Activity.class);
                    intent1.putExtra("userID", userId);
                    startActivity(intent1);
                }
                //개인 모드
                else if(user.equals("개인")){
                    intent1=new Intent(getApplicationContext(), Personal_Add_Information.class);
                }
                intent1.putExtra("userID", userId);
                startActivity(intent1);
            }
        });
    }
}
