package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PickupDetailActivity extends AppCompatActivity {
    public static PickupDetailActivity pickupDetailActivity;
    TextView buyerNameView, productNameInPickupListView, pickupDateView, pickupTimeView,pickupQuantityView;
    String buyerName, productNameInPickupList, pickupDate, pickupTime;
    private StoreManager_MainActivity storeManager_mainActivity;
    int pickupQuantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanage_pickup_detail);

        pickupDetailActivity = PickupDetailActivity.this;

        //정보 받아오기
        Intent intentForGet =getIntent();
        buyerName = intentForGet.getExtras().getString("buyerName");
        productNameInPickupList = intentForGet.getExtras().getString("productNameInPickupList");
        pickupDate = intentForGet.getExtras().getString("pickupDate");
        pickupTime = intentForGet.getExtras().getString("pickupTime");
        pickupQuantity = intentForGet.getIntExtra("pickupQuantity",1);

        //텍스트뷰 할당
        TextView buyerNameView = (TextView)findViewById(R.id.buyerNameInPickup);
        TextView productNameInPickupListView = (TextView)findViewById(R.id.productNameInPickupDetail);
        TextView pickupDateView = (TextView)findViewById(R.id.producDateInPickupDetail);
        TextView pickupTimeView = (TextView)findViewById(R.id.productTimeInPickupDetail);
        TextView pickupQuantityView = (TextView)findViewById(R.id.quantityInPickupDetail);

        //텍스트뷰에 데이터 셋팅
        buyerNameView.setText(buyerName);
        productNameInPickupListView.setText(productNameInPickupList);
        pickupDateView.setText(pickupDate);
        pickupTimeView.setText(pickupTime);
        pickupQuantityView.setText(String.valueOf(pickupQuantity));

        //채팅 버튼 할당
        Button chattingBtn = (Button)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환 + 로그 전달
                //Intent intent1 = new Intent(getApplicationContext(),Select_User_Activity.class);
                //intent1.putExtra("storemanager","sign up");
               // startActivity(intent1);
            }
        });
        //픽업 완료 버튼 할당
        Button pickupComplete = (Button)findViewById(R.id.pickupComplete);
        pickupComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Popup_TwoButton_Activity.class );
                intent.putExtra("button_name","Pickup complete");
                startActivity(intent);
            }
        });
    }

    public void click_back(View view){
        switch (view.getId()){
            //채팅방 나가기
            case R.id.pickupCloseBtn:
                //채팅하는곳 액티비티 닫아주고,
                finish();
                break;
        }
    }
}
