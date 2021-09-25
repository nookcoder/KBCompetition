package com.kbc.Sale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kbc.Chatting.Chatting_List_Fragment;
import com.kbc.Image.ImageAdapter;
import com.kbc.Image.Image_Item;
import com.kbc.R;
import com.kbc.Server.Merchant;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.util.ArrayList;

import retrofit2.Call;

public class StoreManager_Product_Inquiry_Activity extends AppCompatActivity {

    private StoreManager_Product_Inquiry_Activity storeManager_product_inquiry_activity;

    //인텐트에서 넘어오는 정보들
    private ArrayList<Sale_Item> sale_items ;
    private Sale_Item sale_item;
    private String storeManager_id , storeManager_location,productName, storeManger_nickName;

    //상품 정보들
    private TextView storemanager_id, storemanager_location;
    private TextView product_title, product_category,product_register_time, product_price, product_details;

    //창 닫기, 수정하기 버튼
    private ImageButton product_modify_imageButton, product_inquiry_close;


    //이미지 업로드를 위한 리사이클뷰
    private RecyclerView recyclerView;
    private ArrayList<Image_Item> image_items = new ArrayList<>();
    private ImageAdapter imageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_product_inquiry_activity);
        storeManager_product_inquiry_activity = this;
        //상품 정보 가져오기
        Intent intent = getIntent();
        sale_items = (ArrayList<Sale_Item>)intent.getSerializableExtra("sale_item_list");
        sale_item = sale_items.get(0);

        //로그인 정보 가져오기
        storeManager_id = intent.getExtras().getString("userID");
        storeManager_location = intent.getExtras().getString("location");
        productName = intent.getStringExtra("productName");

        Log.d( "조회 액티비티 아이디 ->",storeManager_id);

        //컴포넌트 가져오기
        storemanager_id = findViewById(R.id.storemanager_id);
        storemanager_location  = findViewById(R.id.storemanager_location);
        product_title = findViewById(R.id.product_title);
        product_category = findViewById(R.id.product_category);
        product_register_time = findViewById(R.id.product_register_time);
        product_price = findViewById(R.id.product_price);
        product_details = findViewById(R.id.product_details);

        product_modify_imageButton = findViewById(R.id.product_modify_imageButton);
        product_inquiry_close = findViewById(R.id.product_inquiry_close);

        ImageView imageView = findViewById(R.id.productImageSrc);
        new RetrofitBulider().loadImage(storeManager_id,productName,imageView);
        //상품 사진들 넣어야함!!!!!!!!!!!!!!!!!!!!!!!!!!
//        recyclerView = findViewById(R.id.image_recyclerview);
//        //이미지아이템에 사진 경로를 넣어야합니다아~
//        imageAdapter = new ImageAdapter(image_items);
//        recyclerView.setAdapter(imageAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
//


        //가게 이름, 주소 넣기
        get_storeManager_NickName(storeManager_id);
        storemanager_location.setText(sale_item.getUser_location());

        //제목, 카테고리 넣기
        product_title.setText(sale_item.getName());
        product_category.setText(sale_item.getCategory());
        product_register_time.setText(sale_item.getRegister_time());

        //판매가격, 재고 넣기
        product_price.setText("가격 "+ sale_item.getPrice()+"원");

        //상세설명
        product_details.setText(sale_item.getDetails());


        //수정하기 버튼 이벤트
        product_modify_imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Sale_Item> sale_items = new ArrayList<Sale_Item>();
                sale_items.add(sale_item);

                //상품 수정 액티비티로 들어가기
                Intent intent = new Intent(StoreManager_Product_Inquiry_Activity.this, StoreManager_Product_Modify_Activity.class);
                intent.putExtra("sale_item_list", sale_items);
                intent.putExtra("userID", storeManager_id);
                intent.putExtra("location",storeManager_location);
                startActivity(intent);
                finish();
            }
        });

        //창 닫기
        product_inquiry_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(storeManager_product_inquiry_activity, StoreManager_MainActivity.class);
                intent.putExtra("userID",storeManager_id);
                startActivity(intent);
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