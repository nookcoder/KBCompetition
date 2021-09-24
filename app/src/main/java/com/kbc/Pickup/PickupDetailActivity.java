package com.kbc.Pickup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_Send_Activity;
import com.kbc.Common.Popup_TwoButton_Activity;
import com.kbc.R;
import com.kbc.StoreManger.StoreManager_MainActivity;

public class PickupDetailActivity extends AppCompatActivity {
    public static PickupDetailActivity pickupDetailActivity;
    TextView buyerNameView, productNameInPickupListView, pickupDateView, pickupTimeView,pickupQuantityView;
    String buyerName, productNameInPickupList, pickupDate, pickupTime;

    //픽업 대기 중에서 넘어올때 점주 아이디 받아오기!!!
    String storeManagerId = StoreManager_MainActivity.userId;


    private StoreManager_MainActivity storeManager_mainActivity;
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

        //텍스트뷰 할당
        TextView buyerNameView = (TextView)findViewById(R.id.buyerNameInPickup);
        TextView productNameInPickupListView = (TextView)findViewById(R.id.productNameInPickupDetail);
        TextView pickupDateView = (TextView)findViewById(R.id.producDateInPickupDetail);
        TextView pickupTimeView = (TextView)findViewById(R.id.productTimeInPickupDetail);

        //텍스트뷰에 데이터 셋팅
        buyerNameView.setText(buyerName);
        productNameInPickupListView.setText(productNameInPickupList);
        pickupDateView.setText(pickupDate);
        pickupTimeView.setText(pickupTime);

        Log.d("점주 아이디 ->", storeManagerId);
        Log.d("사는 사람 아이디 -> ", buyerName);

        //채팅 버튼 할당
        Button chattingBtn = (Button)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //화면 전환 + 로그 전달
                //관리자 아이디 + 개인 아이디 전달 해야함
                Intent chatting_intent = new Intent(pickupDetailActivity, Chatting_Send_Activity.class);
                chatting_intent.putExtra("mode", Chatting.STORE_MANAGER);
                chatting_intent.putExtra("userID", storeManagerId);
                chatting_intent.putExtra("click_chatting_list_name", buyerName);
                startActivity(chatting_intent);

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
