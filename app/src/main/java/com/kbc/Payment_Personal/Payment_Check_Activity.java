package com.kbc.Payment_Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.kbc.Chatting.Chatting_List_Fragment;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;
import com.kbc.Server.Personal;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;

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
import retrofit2.Call;

public class Payment_Check_Activity extends AppCompatActivity {

    private Payment_Check_Activity payment_check_activity;
    private Sale_Item purchase_item;

    private TextView buyerNameInPickup, productNameInPickupDetail;

    private Spinner pickup_date_year, pickup_date_month, pickup_date_day, pickup_am_pm, pickup_hour, pickup_minute;
    private ArrayAdapter year_adapter, month_adapter, day_adapter, am_pm_adapter, hour_adapter, minute_adapter;

    private Button payment_open;
    private ImageButton payment_close;

    String productName, paymentId, category,personal_id,personal_town2;
    int price, stuck;

    String merchantId,registerTime,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_check_activity);
        BootpayAnalytics.init(this, "614aec437b5ba4002352b814");
        payment_check_activity = this;

        Intent intent = getIntent();
        purchase_item = ((ArrayList<Sale_Item>) intent.getSerializableExtra("purchase_item_list")).get(0);
        personal_id = intent.getExtras().getString("userID",personal_id);
        personal_town2 = intent.getExtras().getString("town2",personal_town2);

        buyerNameInPickup = findViewById(R.id.buyerNameInPickup);
        productNameInPickupDetail = findViewById(R.id.productNameInPickupDetail);
//

        get_personal_NickName(purchase_item.getPersonal_Id());
//        buyerNameInPickup.setText(purchase_item.getUser_Id());
        productNameInPickupDetail.setText(purchase_item.getName());

        //?????? ?????? ???????????????
        pickup_date_year = findViewById(R.id.pickup_date_year);
        pickup_date_month = findViewById(R.id.pickup_date_month);
        pickup_date_day = findViewById(R.id.pickup_date_day);
        //?????? ?????? ????????? ??????
        pickup_am_pm = findViewById(R.id.pickup_am_pm);
        pickup_hour = findViewById(R.id.pickup_hour);
        pickup_minute = findViewById(R.id.pickup_minute);

        //????????? ????????? ?????????
        year_adapter = ArrayAdapter.createFromResource(this, R.array.year, R.layout.spinner_date);
        month_adapter = ArrayAdapter.createFromResource(this, R.array.month, R.layout.spinner_date);
        day_adapter = ArrayAdapter.createFromResource(this, R.array.day, R.layout.spinner_date);

        am_pm_adapter = ArrayAdapter.createFromResource(this, R.array.am_pm, R.layout.spinner_date);
        hour_adapter = ArrayAdapter.createFromResource(this, R.array.hour, R.layout.spinner_date);
        minute_adapter = ArrayAdapter.createFromResource(this, R.array.minute, R.layout.spinner_date);

        //????????? ??????
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

        //???????????? ??? !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        payment_open = findViewById(R.id.payment_open);
        payment_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????? ??????
                productName = purchase_item.getName();
                paymentId = "12345";
                price = Integer.valueOf(purchase_item.getPrice());
                category = purchase_item.getCategory();
                stuck = 10;

                BootUser bootUser = new BootUser().setPhone("010-3931-9679"); // !! ????????? ????????? ????????? ?????????
                BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});

                Bootpay.init(getFragmentManager())
                        .setApplicationId("614aec437b5ba4002352b814") // ?????? ????????????(???????????????)??? application id ???(?????? ??? ??????)
                        .setPG(PG.INICIS) // ????????? PG ???
                        .setMethod(Method.CARD) // ????????????
                        .setContext(Payment_Check_Activity.this)
                        .setBootUser(bootUser)
                        .setBootExtra(bootExtra)
                        .setUX(UX.PG_DIALOG)
                        .setName(productName) // ????????? ?????????
                        .setOrderId(paymentId) // ?????? ???????????? (expire_month)
                        .setPrice(price) // ????????? ??????
                        .addItem(productName, 1, category, price) // ??????????????? ?????? ????????????, ????????? ?????? ??????
                        .onConfirm(new ConfirmListener() { // ????????? ???????????? ?????? ?????? ???????????? ?????????, ?????? ???????????? ?????? ????????? ??????
                            @Override
                            public void onConfirm(@Nullable String message) {

                                if (0 < stuck) Bootpay.confirm(message); // ????????? ?????? ??????.
                                else Bootpay.removePaymentWindow(); // ????????? ?????? ????????? ???????????? ?????? ?????? ??????
                                Log.d("confirm", message);
                            }
                        })
                        //?????? ???????????? ???????????? ????????????!!!!!!!!!!!!!!! + ???????????? ??????????????? !!!
                        .onDone(new DoneListener() { // ??????????????? ??????, ????????? ?????? ??? ????????? ????????? ????????? ???????????????
                            @Override
                            public void onDone(@Nullable String message) {
                                Log.d("done", message);
                                merchantId = purchase_item.getUser_Id();
                                registerTime = purchase_item.getRegister_time();
                                location = purchase_item.getUser_location();
                                Intent intent = new Intent(Payment_Check_Activity.this,Payment_Finished.class);
                                intent.putExtra("userID",personal_id);
                                intent.putExtra("merchantId",merchantId);
                                intent.putExtra("registerTime",registerTime);
                                intent.putExtra("pickUpYear",pickup_date_year.getSelectedItem().toString());
                                intent.putExtra("pickUpMonth",pickup_date_month.getSelectedItem().toString());
                                intent.putExtra("pickUpDay",pickup_date_day.getSelectedItem().toString());
                                intent.putExtra("pickUpNoon",pickup_am_pm.getSelectedItem().toString());
                                intent.putExtra("pickUpHour",pickup_hour.getSelectedItem().toString());
                                intent.putExtra("pickUpMinute",pickup_minute.getSelectedItem().toString());
                                intent.putExtra("location",location);
                                intent.putExtra("productName",productName);
                                intent.putExtra("town2",personal_town2);
                                startActivity(intent);
                            }
                        })
                        .onReady(new ReadyListener() { // ???????????? ?????? ??????????????? ???????????? ???????????? ???????????????.
                            @Override
                            public void onReady(@Nullable String message) {
                                Log.d("ready", message);
                            }
                        })
                        .onCancel(new CancelListener() { // ?????? ????????? ??????
                            @Override
                            public void onCancel(@Nullable String message) {

                                Log.d("cancel", message);
                            }
                        })
                        .onClose(
                                new CloseListener() { //???????????? ????????? ???????????? ??????
                                    @Override
                                    public void onClose(String message) {
                                        Log.d("close", "close");
                                    }
                                })
                        .request();
            }
        });
    }

    //????????? ??????
    private void Create_Spinner(Spinner spinner, ArrayAdapter arrayAdapter, int layout_type) {

        arrayAdapter.setDropDownViewResource(layout_type);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

    }

    //???????????? ???????????????
    private void get_personal_NickName(String userId) {
        ServiceApi serviceApi=  new RetrofitBulider().initRetrofit();
        Call<Personal> call = serviceApi.getPersonalData(userId);
        new Insert_Personal_NickName().execute(call);
    }

    private class Insert_Personal_NickName extends AsyncTask<Call, Void, String> {
        @Override
        protected String doInBackground(Call... calls) {
            try{
                Call<Personal> call = calls[0];
                Personal personalData = call.execute().body();
                return  personalData.getNickName();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String nickName){
            buyerNameInPickup.setText(nickName);
        }
    }
}