package com.kbc.StoreManger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Common.Login_Activity;
import com.kbc.R;

public class StoreManager_Add_Store_Info_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_add_store_info);

        //컴포넌트 할당
        Button next = (Button) findViewById(R.id.next);
        Button backBtn = (Button) findViewById(R.id.goBack);

        EditText nameEditText = (EditText) findViewById(R.id.storeName);
        EditText phoneEditText = (EditText) findViewById(R.id.storeNum);

        TextView nameCheck = (TextView) findViewById(R.id.nameCheck);
        TextView phoneCheck = (TextView) findViewById(R.id.numCheck);

        //뒤로가기 버튼 클릭 이벤트 달기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(StoreManager_Add_Store_Info_Activity.this, Login_Activity.class);
                startActivity(goBack);
            }
        });
        //다음 버튼 클릭 이벤트 달기
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String 컴포넌트 선언 -> 입력 받은 정보 정리!!!!!
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();

                //비어있는 정보 있는지 확인
                if (name.length() == 0 || phone.length() == 0) {
                    if (name.length() == 0)
                        nameCheck.setVisibility(view.VISIBLE);
                    else if(name.length()> 0){
                        nameCheck.setVisibility(view.INVISIBLE);
                    }
                    if (phone.length() == 0)
                        phoneCheck.setVisibility(view.VISIBLE);
                    else if(phone.length()> 0){
                        phoneCheck.setVisibility(view.INVISIBLE);
                    }

                }

                //다 입력되어 있으면
                else if (name.length() != 0 && phone.length() != 0) {
                    //데이터 받아오기
                    Intent intentForGet = getIntent();
                    String userId = intentForGet.getExtras().getString("userID");
                    //화면 전환 + 데이터 전달( 회원Id, 가게 이름, 가게 번호)
                    Intent intent = new Intent(getApplicationContext(), StoreManager_Add_Manager_Info_Activity.class);
                    intent.putExtra("userID" , userId);
                    intent.putExtra("storeName" , name);
                    intent.putExtra("storeNum" , phone);
                    startActivity(intent);
                }
            }
        });
    }
}