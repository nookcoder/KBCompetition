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
                //데이터 받아오기
                Intent intentForGet = getIntent();
                String userId = intentForGet.getExtras().getString("userID");
                String user=intentForGet.getExtras().getString("user");

                //화면 전환 + 데이터 전달
                Intent intent1;
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
            }
        });
    }
}
