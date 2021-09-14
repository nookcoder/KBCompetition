package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.R;
import com.kbc.WebViewActivity;


public class MainActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private TextView et_order_zipcode;
    private TextView et_order_address1;
    TextView IdCheck,nameCheck,birthCheck,addressCheck;
    EditText managerIdEditText,nameEditText,yearEditText,monthEditText,dayEditText,detailAddressEditText;
    String managerId, name, birth, year, month, day, addressZipcode, fullAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_add_manager_info);

        et_order_zipcode = (TextView) findViewById(R.id.address1);
        et_order_address1 = (TextView) findViewById(R.id.address2);
        et_order_zipcode.setText("");
        et_order_address1.setText("");
        //텍스트뷰
         IdCheck = (TextView) findViewById(R.id.IdCheck);
         nameCheck = (TextView) findViewById(R.id.nameCheck);
         birthCheck = (TextView) findViewById(R.id.birthCheck);
         addressCheck = (TextView) findViewById(R.id.addressCheck);

        //텍스트
         managerIdEditText = (EditText) findViewById(R.id.managerId);
         nameEditText = (EditText) findViewById(R.id.name);
         yearEditText = (EditText) findViewById(R.id.year);
         monthEditText = (EditText) findViewById(R.id.month);
         dayEditText = (EditText) findViewById(R.id.day);
         detailAddressEditText = (EditText) findViewById(R.id.address3);

        //다음 버튼
        Button next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //문자열로 변환
                managerId = managerIdEditText.getText().toString();
                name = nameEditText.getText().toString();
                year = yearEditText.getText().toString();
                month = monthEditText.getText().toString();
                day = dayEditText.getText().toString();
                addressZipcode = et_order_zipcode.getText().toString(); //우편번호
                fullAddress = et_order_address1.getText().toString() + " " + detailAddressEditText.getText().toString(); //주소 전체
                //날짜 합치기
                birth = combineDate(year, month, day);

                //비어있는 정보 있는지 확인
                if (managerId.length() == 0 || name.length() == 0 || year.length() == 0 || month.length() == 0 || day.length() == 0 || addressZipcode.length() == 0 || detailAddressEditText.getText().toString().length() == 0) {
                    if (managerId.length() == 0)
                        IdCheck.setVisibility(view.VISIBLE);
                    if (name.length() == 0)
                        nameCheck.setVisibility(view.VISIBLE);
                    if (year.length() == 0 || month.length() == 0 || day.length() == 0)
                        birthCheck.setVisibility(view.VISIBLE);
                    if (addressZipcode.length() == 0 || detailAddressEditText.getText().toString().length() == 0)
                        addressCheck.setVisibility(view.VISIBLE);
                }
                //다 입력되어 있으면
                else if (managerId.length() != 0 || name.length() != 0 || year.length() != 0 || month.length() != 0 || day.length() != 0 || addressZipcode.length() != 0 || detailAddressEditText.getText().toString().length() != 0) {
                    //intent로 화면 전환 + [user : 사업자] 전달
                    Intent intent = new Intent(getApplicationContext(), Added_Done_Activity.class);
                    intent.putExtra("user", "store manager");
                    startActivity(intent);
                }
            }
        });
        //주소 검색 버튼
        Button btn_search = (Button) findViewById(R.id.findAddress);
        if (btn_search != null) {
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, WebViewActivity.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
        }

        //뒤로가기 버튼
        Button backBtn = (Button) findViewById(R.id.goBack);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), StoreManager_Add_Store_Info_Activity.class);
                startActivity(intent1);
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        et_order_zipcode.setText(data.substring(0, 5));
                        et_order_address1.setText(data.substring(7));

                    }
                }
                break;
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
}