package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Personal_Add_Information extends AppCompatActivity {

    private Spinner towm1View, town2View;
    private ArrayAdapter<String> arrayAdapter;
    public static final String EXTRA_ADDRESS = "address";
    public String name, nicName, num, town1, town2; //이름,닉네임,번호,시/도,시/군/구
    private TextView nameCheck, perconalNumCheck, nicNameCheck, townCheck;
    EditText nameView, nicNameView, numView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.persona_add_information);

        //컴포넌트 할당
        nameView = (EditText) findViewById(R.id.personalName);
        nicNameView = (EditText) findViewById(R.id.personalNicname);
        numView = (EditText) findViewById(R.id.personalNum);

        //텍스트뷰 할당
        nameCheck = (TextView) findViewById(R.id.nameCheck);
        nicNameCheck = (TextView) findViewById(R.id.nicNameCheck);
        perconalNumCheck = (TextView) findViewById(R.id.perconalNumCheck);
        townCheck = (TextView) findViewById(R.id.townCheck);

        //동네 구하기
        towm1View = (Spinner) findViewById(R.id.town1);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_region));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        towm1View.setAdapter(arrayAdapter);
        town2View = (Spinner) findViewById(R.id.town2);

        //스피너 설정
        initAddressSpinner();

        //버튼에 이벤트달기
        Button okBtn = findViewById(R.id.next);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //정보 받아오기
                name = nameView.getText().toString();
                nicName = nicNameView.getText().toString();
                num = numView.getText().toString();
                town1 = towm1View.getSelectedItem().toString();

                //비어 있는 정보 있는지 확인
                if (nameView.getText().toString().length() == 0 || nicNameView.getText().toString().length() == 0 || numView.getText().toString().length() == 0 || towm1View.getSelectedItemPosition() == 0) {
                    if (name.length() == 0) {
                        nameCheck.setVisibility(view.VISIBLE);
                    }
                    else {
                        nameCheck.setVisibility(view.INVISIBLE);
                    }
                    if (nicNameView.getText().toString().length() == 0)
                        nicNameCheck.setVisibility(view.VISIBLE);
                    else {
                        nicNameCheck.setVisibility(view.INVISIBLE);
                    }
                    if (numView.getText().toString().length() == 0)
                        perconalNumCheck.setVisibility(view.VISIBLE);
                    else {
                        perconalNumCheck.setVisibility(view.INVISIBLE);
                    }
                    if (towm1View.getSelectedItemPosition() == 0)
                        townCheck.setVisibility(view.VISIBLE);
                    else {
                        townCheck.setVisibility(view.INVISIBLE);
                    }
                } else {
                    nameCheck.setVisibility(view.INVISIBLE);
                    nicNameCheck.setVisibility(view.INVISIBLE);
                    perconalNumCheck.setVisibility(view.INVISIBLE);
                    townCheck.setVisibility(view.INVISIBLE);
                    town2 = town2View.getSelectedItem().toString();

                    Intent intent = new Intent(getApplicationContext(), Added_Done_Activity.class);
                    intent.putExtra("userId", "hyunjin");//테스트용
                    intent.putExtra("user", "person");
                    startActivity(intent);
                }
            }
        });
    }


    private void initAddressSpinner() {
        towm1View.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 시군구, 동의 스피너를 초기화한다.
                switch (position) {
                    case 0:
                        town2View.setAdapter(null);
                        break;
                    case 1:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_seoul);
                        break;
                    case 2:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_busan);
                        break;
                    case 3:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daegu);
                        break;
                    case 4:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_incheon);
                        break;
                    case 5:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gwangju);
                        break;
                    case 6:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daejeon);
                        break;
                    case 7:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_ulsan);
                        break;
                    case 8:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_sejong);
                        break;
                    case 9:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeonggi);
                        break;
                    case 10:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gangwon);
                        break;
                    case 11:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_buk);
                        break;
                    case 12:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_nam);

                        break;
                    case 13:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_buk);
                        break;
                    case 14:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_nam);
                        break;
                    case 15:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_buk);
                        break;
                    case 16:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_nam);
                        break;
                    case 17:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeju);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        town2View.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 서울특별시 선택시
                if (towm1View.getSelectedItemPosition() == 1 && town2View.getSelectedItemPosition() > -1) {
                    switch (position) {
                        //25
                    }
                } else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setSigunguSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            town2View.setAdapter(null);
            arrayAdapter = null;
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        town2View.setAdapter(arrayAdapter);
    }

    private void setDongSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            arrayAdapter = null;
        }

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
