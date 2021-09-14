package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//사업자 정보 등록
public class StoreManager_Add_Manager_Info_Activity extends AppCompatActivity {

    //주소찾기 요청 변수
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    //컴포넌트 할당
    private TextView addressZipcodeText = (TextView) findViewById(R.id.address1);
    private TextView addressText = (TextView) findViewById(R.id.address2);
    //버튼
    Button btn_search = (Button) findViewById(R.id.findAddress);
    Button next = (Button) findViewById(R.id.next);
    Button backBtn = (Button) findViewById(R.id.goBack);
    //텍스트
    EditText managerIdEditText = (EditText) findViewById(R.id.managerId);
    EditText nameEditText = (EditText) findViewById(R.id.name);
    EditText yearEditText = (EditText) findViewById(R.id.year);
    EditText monthEditText = (EditText) findViewById(R.id.month);
    EditText dayEditText = (EditText) findViewById(R.id.day);
    EditText detailAddressEditText = (EditText) findViewById(R.id.address3);
    //텍스트뷰
    TextView IdCheck = (TextView) findViewById(R.id.IdCheck);
    TextView nameCheck = (TextView) findViewById(R.id.nameCheck);
    TextView birthCheck = (TextView) findViewById(R.id.birthCheck);
    TextView addressCheck = (TextView) findViewById(R.id.addressCheck);
    //문자열
    String managerId, name, birth, year, month, day, addressZipcode, fullAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_add_manager_info);


        //주소 찾기 버튼
        if (btn_search != null) {
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(StoreManager_Add_Manager_Info_Activity.this, WebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }





    }


    //날짜 합치기 함수
    public String combineDate(String year, String month, String day) {
        String combinedBirth;
        //날짜 한글자 처리
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        //날짜 합치기
        combinedBirth = year + month + day;
        return combinedBirth;
    }


    //결과 가져오기
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        addressZipcodeText.setText(data.substring(0, 5)); //우편번호
                        addressText.setText(data.substring(7)); //도로명 주소
                    }
                }
                break;
        }
    }
}
