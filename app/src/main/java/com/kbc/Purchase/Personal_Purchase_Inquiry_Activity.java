package com.kbc.Purchase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_Send_Activity;
import com.kbc.Payment_Personal.Payment_Check_Activity;
import com.kbc.R;
import com.kbc.Sale.Sale_Item;
import com.kbc.Sale.StoreManager_Product_Inquiry_Activity;
import com.kbc.Server.Merchant;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;

public class Personal_Purchase_Inquiry_Activity extends AppCompatActivity {

    private Personal_Purchase_Inquiry_Activity personal_purchase_inquiry_activity;
    private Sale_Item purchase_item;

    private CircleImageView storemanager_image_profile;
    private ImageButton product_inquiry_close, chatting_imageButton;
    private TextView storemanager_id, storemanager_location, product_title, product_category, product_date_type, product_deadline_time, product_price, product_details;
    private ImageView imageView;
    private Button product_payment;

    String personal_id, personal_town2, storeManager_NickName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_purchase_inquiry_activity);
        personal_purchase_inquiry_activity = this;
        //선택한 장보기 아이템 가져오기 (사업자이름 , 상품이름 , 카테고리, 가격 만 있음!)
        Intent intent = getIntent();
        purchase_item = ((ArrayList<Sale_Item>) intent.getSerializableExtra("purchase_item_list")).get(0);
        personal_id = intent.getExtras().getString("userID", personal_id);
        personal_town2 = intent.getExtras().getString("town2", personal_town2);

        //상품 조회 창 닫기
        product_inquiry_close = findViewById(R.id.product_inquiry_close);

        product_inquiry_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView = findViewById(R.id.productImageSrc);
        new RetrofitBulider().loadImage(purchase_item.getUser_Id(), purchase_item.getName(),imageView);

        //차례대로 컴포넌트 가져오기 (텍스트뷰)
        storemanager_id = findViewById(R.id.storemanager_id);
        storemanager_location = findViewById(R.id.storemanager_location);
        product_title = findViewById(R.id.product_title);
        product_category = findViewById(R.id.product_category);
        product_date_type = findViewById(R.id.product_date_type);
        product_deadline_time = findViewById(R.id.product_deadline_time);
        product_price = findViewById(R.id.product_price);
        product_details = findViewById(R.id.product_details);

        //데이터 넣기
        get_storeManager_NickName(purchase_item.getUser_Id());
        storemanager_location.setText(purchase_item.getUser_location());
        product_title.setText(purchase_item.getName());
        product_category.setText(purchase_item.getCategory());

        if (purchase_item.getDate_type().equals("유통"))
            product_date_type.setText("유통기한");
        else
            product_date_type.setText("구입날짜");
        product_deadline_time.setText(purchase_item.getDate_year() + " " + purchase_item.getDate_month() + " " + purchase_item.getDate_day());
        product_price.setText("가격 " + purchase_item.getPrice() + "원");
        product_details.setText(purchase_item.getDetails());


        //채팅 버튼 누르면 -> 유저 아이디 + 사업자 아이디 전달해야함 + mode까지 !!!!!!!!
        chatting_imageButton = findViewById(R.id.chatting_imageButton);
        chatting_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatting_intent = new Intent(personal_purchase_inquiry_activity, Chatting_Send_Activity.class);
                chatting_intent.putExtra("mode", Chatting.PERSONAL);
                chatting_intent.putExtra("userID", purchase_item.getPersonal_Id());
                chatting_intent.putExtra("click_chatting_list_name", purchase_item.getUser_Id());
                startActivity(chatting_intent);
            }
        });

        //결제 창 누르면 -> 픽업 시간, 날짜, 선택하고, 밑에서 결제 누르고 -> 최종 결제 하면 -> -데이터 서버로 전달
        product_payment = findViewById(R.id.product_payment);
        product_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Sale_Item> sale_items = new ArrayList<>();
                sale_items.add(purchase_item);
                Intent payment_intent = new Intent(personal_purchase_inquiry_activity, Payment_Check_Activity.class);
                payment_intent.putExtra("purchase_item_list", sale_items);
                payment_intent.putExtra("userID", personal_id);
                payment_intent.putExtra("town2", personal_town2);
                startActivity(payment_intent);
            }
        });

    }
    //점주 닉네임
    private void get_storeManager_NickName(String userId) {
        ServiceApi serviceApi=  new RetrofitBulider().initRetrofit();
        Call<Merchant> call = serviceApi.getStoreName(userId);
        new Insert_storeManager_NickName().execute(call);
    }

    private class Insert_storeManager_NickName extends AsyncTask<Call, Void, String> {
        @Override
        protected String doInBackground(Call... calls) {
            try{
                Call<Merchant> call = calls[0];
                Merchant merchantData = call.execute().body();
                return  merchantData.getStoreName();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String nickName){
            storemanager_id.setText(nickName);

        }
    }

}