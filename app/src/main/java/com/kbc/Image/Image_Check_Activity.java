package com.kbc.Image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kbc.R;
import com.kbc.Sale.StoreManager_Product_Register_Activity;

import java.io.File;
import java.util.ArrayList;

public class Image_Check_Activity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Image_Item> image_items = new ArrayList<>();
    private Image_Item image_item= new Image_Item();
    private ImageView imageView;
    private ImageButton image_register_close;
    private Button register_image;


    private Bitmap bitmap_image;
    private Image_Check_Activity image_check_activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_check_activity);
        image_check_activity = this;
        imageView = findViewById(R.id.get_image);

        Intent intent = getIntent();
        //이미지 아이템 객체에 카메라로 찍은 건지, 갤러리에서 가져온건지 유형 구분
        image_item.setImage_type( intent.getStringExtra("image_type"));
        //이미지 파일 경로 가져오기
        image_item.setImage_file_path(intent.getStringExtra("image_file_path"));
        //비트맵으로 변환하기
        bitmap_image = image_item.get_Bitmap();

        //띄우기
        imageView.setImageBitmap(bitmap_image);

        image_register_close = findViewById(R.id.image_register_close);
        register_image = findViewById(R.id.register_image);

        image_register_close.setOnClickListener(this);
        register_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.image_register_close:
               finish();
               break;

            case R.id.register_image:
                //상품 등록!!!!!!!!!!!!!!!!!!!!!!!
                StoreManager_Product_Register_Activity storeManager_product_register_activity = (StoreManager_Product_Register_Activity)StoreManager_Product_Register_Activity.storeManager_product_register_activity;
                storeManager_product_register_activity.Insert_Adapter_item(image_item);
                finish();
                break;
        }

    }
}