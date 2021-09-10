package com.kbc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StoreManager_CheckNum extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_number_verification);

        //컴포넌트 할당
        Button getCertificationNumBtn = (Button) findViewById(R.id.getchecknum);
        Button checkNumBtn = (Button) findViewById(R.id.checkCertificationNum);
        Button backBtn = (Button) findViewById(R.id.goBack);

        EditText nameEditText = (EditText) findViewById(R.id.name);
        EditText birthEditText = (EditText) findViewById(R.id.birth);
        EditText sexEditText = (EditText) findViewById(R.id.sex);
        EditText phoneEditText = (EditText) findViewById(R.id.phone);
        EditText certificationNumEditText = (EditText) findViewById(R.id.certificationNum);

        TextView nameCheck = (TextView) findViewById(R.id.nameCheck);
        TextView birthCheck = (TextView) findViewById(R.id.birthCheck);
        TextView phoneCheck = (TextView) findViewById(R.id.phoneCheck);
        TextView certificationNumView = (TextView) findViewById(R.id.certificationNumView);

        //뒤로가기 버튼 클릭 이벤트 달기
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(),Select_User_Activity.class);
                intent1.putExtra("log","sign up");
                startActivity(intent1);
            }
        });
        //인증번호 받기 버튼 클릭 이벤트 달기
        getCertificationNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String 컴포넌트 선언
                String name = nameEditText.getText().toString();
                String birth = birthEditText.getText().toString();
                String sex = sexEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String certificationNum = certificationNumEditText.getText().toString();

                //입력 공간 안보이게 설정
                nameCheck.setVisibility(view.INVISIBLE);
                birthCheck.setVisibility(view.INVISIBLE);
                phoneCheck.setVisibility(view.INVISIBLE);

                //비어있는 정보 있는지 확인
                if (name.length() == 0 || birth.length() == 0 || sex.length() == 0 || phone.length() == 0) {
                    if (name.length() == 0)
                        nameCheck.setVisibility(view.VISIBLE);
                    if (birth.length() == 0 || sex.length() == 0)
                        birthCheck.setVisibility(view.VISIBLE);
                    if (phone.length() == 0)
                        phoneCheck.setVisibility(view.VISIBLE);
                }

                //다 입력되어 있으면
                else if (name.length() != 0 && birth.length() != 0 && sex.length() != 0 && phone.length() != 0) {
                    //아래에 입력 공간 생기기
                    checkNumBtn.setVisibility(view.VISIBLE);
                    certificationNumView.setVisibility(view.VISIBLE);
                    certificationNumEditText.setVisibility(view.VISIBLE);
                    //인증번호 받기 텍스트 변경
                    getCertificationNumBtn.setText("인증문자 다시 받기");
                }
            }
        });
    }
}