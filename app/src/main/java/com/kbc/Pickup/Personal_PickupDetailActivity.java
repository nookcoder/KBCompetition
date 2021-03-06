package com.kbc.Pickup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_Send_Activity;
import com.kbc.Personal_MainActivity;
import com.kbc.Purchase.Personal_Purchase_Inquiry_Activity;
import com.kbc.R;
import com.kbc.Server.RetrofitBulider;

public class Personal_PickupDetailActivity extends AppCompatActivity {
    public static Personal_PickupDetailActivity personal_pickupDetailActivity;
    String storeName, productNameInPickupList, pickupDate, pickupTime,merchantId;

    private Personal_MainActivity personal_mainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_pickup_detail);

        personal_pickupDetailActivity = Personal_PickupDetailActivity.this;

        //정보 받아오기
        Intent intentForGet =getIntent();
        storeName = intentForGet.getExtras().getString("storeName");
        productNameInPickupList = intentForGet.getExtras().getString("productNameInPickupList");
        pickupDate = intentForGet.getExtras().getString("pickupDate");
        pickupTime = intentForGet.getExtras().getString("pickupTime");
        merchantId = intentForGet.getExtras().getString("merchantId");

        //텍스트뷰 할당
        TextView storeNameView = (TextView)findViewById(R.id.storeName3);
        TextView productNameInPickupListView = (TextView)findViewById(R.id.productNameInPickupDetail3);
        TextView pickupDateView = (TextView)findViewById(R.id.producDateInPickupDetail3);
        TextView pickupTimeView = (TextView)findViewById(R.id.productTimeInPickupDetail3);
        ImageView imageView = findViewById(R.id.imageView_PickUpDetail);

        new RetrofitBulider().loadImage(merchantId,productNameInPickupList,imageView);

        //텍스트뷰에 데이터 셋팅
        storeNameView.setText(storeName);
        productNameInPickupListView.setText(productNameInPickupList);
        pickupDateView.setText(pickupDate);
        pickupTimeView.setText(pickupTime);

        //채팅 버튼 할당
        Button chattingBtn = (Button)findViewById(R.id.chattingBtn);
        chattingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent chatting_intent = new Intent(personal_pickupDetailActivity, Chatting_Send_Activity.class);
                chatting_intent.putExtra("mode", Chatting.PERSONAL);
                chatting_intent.putExtra("userID", Personal_MainActivity.userId);
                chatting_intent.putExtra("click_chatting_list_name", merchantId);
                startActivity(chatting_intent);
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
