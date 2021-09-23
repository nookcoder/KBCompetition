package com.kbc.Payment_Personal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kbc.Personal_MainActivity;
import com.kbc.Pickup.Personal_PickupDetailActivity;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;
import com.kbc.Server.PickUpData;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_Finished extends AppCompatActivity {
    Button next;
    String personal_id,personal_town2;
    String merchantId, registerTime;
    String pickUpYear,pickUpMonth,pickUpDay,pickUpNoon,pickUpHour,pickUpMinute,location,productName;

    ServiceApi serviceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_finished);
        Intent intent = getIntent();
        
        // 서버 전송 데이터
        personal_id = intent.getExtras().getString("userID");
        personal_town2 = intent.getExtras().getString("town2");
        merchantId = intent.getExtras().getString("merchantId");
        registerTime = intent.getExtras().getString("registerTime");
        pickUpYear = intent.getExtras().getString("pickUpYear");
        pickUpMonth = intent.getExtras().getString("pickUpMonth");
        pickUpDay = intent.getExtras().getString("pickUpDay");
        pickUpNoon = intent.getExtras().getString("pickUpNoon");
        pickUpHour = intent.getExtras().getString("pickUpHour");
        pickUpMinute = intent.getExtras().getString("pickUpMinute");
        location =intent.getExtras().getString("location");
        productName = intent.getExtras().getString("productName");

        PickUpData pickUpData = new PickUpData(merchantId,personal_id,pickUpYear,pickUpMonth,pickUpDay,pickUpNoon,pickUpMinute,pickUpHour,location,productName,registerTime);

        next=(Button)findViewById(R.id.finished_payment);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("아이디","사업자 : " + merchantId+ "/ 개인 : "+personal_id+"/ 등록시간 : " +registerTime) ;
                //서버로 전송
                serviceApi = new RetrofitBulider().initRetrofit();
                Call<PickUpData> call = serviceApi.sendPickUpData(pickUpData);
                call.enqueue(new Callback<PickUpData>() {
                    @Override
                    public void onResponse(Call<PickUpData> call, Response<PickUpData> response) {

                    }

                    @Override
                    public void onFailure(Call<PickUpData> call, Throwable t) {
                        Log.d("연결","실패" + t.getMessage());
                    }
                });

                Intent back = new Intent(Payment_Finished.this,Personal_MainActivity.class);
                back.putExtra("userID",personal_id);
                back.putExtra("town2",personal_town2);
                startActivity(back);
            }
        });
    }
}
