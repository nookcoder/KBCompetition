package com.kbc.Payment_Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.kbc.Payment;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;

import java.util.ArrayList;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;

public class Payment_Check_Activity extends AppCompatActivity {

    private Payment_Check_Activity payment_check_activity;
    private Sale_Item purchase_item;

    private TextView buyerNameInPickup, productNameInPickupDetail;

    private Spinner pickup_date_year, pickup_date_month, pickup_date_day, pickup_am_pm, pickup_hour, pickup_minute;
    private ArrayAdapter year_adapter, month_adapter, day_adapter, am_pm_adapter, hour_adapter, minute_adapter;

    private Button payment_open;
    private ImageButton payment_close;

    String productName, paymentId, category;
    int price, stuck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_check_activity);
        BootpayAnalytics.init(this, "614aec437b5ba4002352b814");
        payment_check_activity = this;

        Intent intent = getIntent();
        purchase_item = ((ArrayList<Sale_Item>) intent.getSerializableExtra("purchase_item_list")).get(0);

        buyerNameInPickup = findViewById(R.id.buyerNameInPickup);
        productNameInPickupDetail = findViewById(R.id.productNameInPickupDetail);

        buyerNameInPickup.setText(purchase_item.getUser_Id());
        productNameInPickupDetail.setText(purchase_item.getName());

        //픽업 날짜 스피너연결
        pickup_date_year = findViewById(R.id.pickup_date_year);
        pickup_date_month = findViewById(R.id.pickup_date_month);
        pickup_date_day = findViewById(R.id.pickup_date_day);
        //픽업 시간 스피너 연결
        pickup_am_pm = findViewById(R.id.pickup_am_pm);
        pickup_hour = findViewById(R.id.pickup_hour);
        pickup_minute = findViewById(R.id.pickup_minute);

        //스피너 어뎁터 초기화
        year_adapter = ArrayAdapter.createFromResource(this, R.array.year, R.layout.spinner_date);
        month_adapter = ArrayAdapter.createFromResource(this, R.array.month, R.layout.spinner_date);
        day_adapter = ArrayAdapter.createFromResource(this, R.array.day, R.layout.spinner_date);

        am_pm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, R.layout.spinner_date);
        hour_adapter = ArrayAdapter.createFromResource(this, R.array.hour, R.layout.spinner_date);
        minute_adapter = ArrayAdapter.createFromResource(this, R.array.minute, R.layout.spinner_date);

        //스피너 형성
        Create_Spinner(pickup_date_year, year_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_date_month, month_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_date_day, day_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_am_pm, am_pm_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_hour, hour_adapter, R.layout.spinner_date);
        Create_Spinner(pickup_minute, minute_adapter, R.layout.spinner_date);


        payment_close = findViewById(R.id.payment_close);
        payment_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //결제하기 창 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        payment_open = findViewById(R.id.payment_open);
        payment_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //변수 빼기
                productName = purchase_item.getName();
                paymentId = "12345";
                price = Integer.valueOf(purchase_item.getPrice());
                category = purchase_item.getCategory();
                stuck = 10;

                BootUser bootUser = new BootUser().setPhone("010-3931-9679"); // !! 자신의 핸드폰 번호로 바꾸기
                BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});

                Bootpay.init(getFragmentManager())
                        .setApplicationId("614aec437b5ba4002352b814") // 해당 프로젝트(안드로이드)의 application id 값(위의 값 복붙)
                        .setPG(PG.INICIS) // 결제할 PG 사
                        .setMethod(Method.CARD) // 결제수단
                        .setContext(Payment_Check_Activity.this)
                        .setBootUser(bootUser)
                        .setBootExtra(bootExtra)
                        .setUX(UX.PG_DIALOG)
                        .setName(productName) // 결제할 상품명
                        .setOrderId(paymentId) // 결제 고유번호 (expire_month)
                        .setPrice(price) // 결제할 금액
                        .addItem(productName, 1, category, price) // 주문정보에 담길 상품정보, 통계를 위해 사용
                        .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                            @Override
                            public void onConfirm(@Nullable String message) {

                                if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                                else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                Log.d("confirm", message);
                            }
                        })
                        //결제 완료되면 장보기로 돌아가기!!!!!!!!!!!!!!! + 서버쪽에 전달해야함 !!!
                        .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                            @Override
                            public void onDone(@Nullable String message) {
                                Log.d("done", message);
                                Intent intent = new Intent(Payment_Check_Activity.this,Payment_Finished.class);
                                startActivity(intent);
                            }
                        })
                        .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                            @Override
                            public void onReady(@Nullable String message) {
                                Log.d("ready", message);
                            }
                        })
                        .onCancel(new CancelListener() { // 결제 취소시 호출
                            @Override
                            public void onCancel(@Nullable String message) {

                                Log.d("cancel", message);
                            }
                        })
                        .onClose(
                                new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                    @Override
                                    public void onClose(String message) {
                                        Log.d("close", "close");
                                    }
                                })
                        .request();
            }
        });
    }

    //스피너 형성
    private void Create_Spinner(Spinner spinner, ArrayAdapter arrayAdapter, int layout_type) {

        arrayAdapter.setDropDownViewResource(layout_type);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

    }

}