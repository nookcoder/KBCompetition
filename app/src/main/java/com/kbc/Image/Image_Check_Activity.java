package com.kbc.Image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kbc.R;
import com.kbc.Sale.StoreManager_Product_Register_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Image_Check_Activity extends AppCompatActivity implements View.OnClickListener{

    ArrayList<Image_Item> image_items = new ArrayList<>();
    private Image_Item image_item= new Image_Item();
    private ImageView imageView;
    private ImageButton image_register_close;
    private Button register_image;

    private Bitmap bitmap_image;
    private Image_Check_Activity image_check_activity;

    ServiceApi serviceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRetrofitClient();
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
                uploadImage(image_item.getImage_file_path());
                Log.d("img",image_item.getImage_file_path());
                StoreManager_Product_Register_Activity storeManager_product_register_activity = (StoreManager_Product_Register_Activity)StoreManager_Product_Register_Activity.storeManager_product_register_activity;
                storeManager_product_register_activity.Insert_Adapter_item(image_item);
                finish();
                break;
        }

    }

    private void initRetrofitClient(){
        OkHttpClient client = new OkHttpClient.Builder().build();
        serviceApi = new Retrofit.Builder().baseUrl("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000").client(client).build().create(ServiceApi.class);
    }


    private void uploadImage(String path) {

        try {
            String imgPath = getApplicationContext() + path;
            String extensiono = imgPath.substring(imgPath.lastIndexOf("."));
            File fileDir = getApplicationContext().getFilesDir();
            File file = new File(fileDir,"img"+extensiono);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap_image.compress(Bitmap.CompressFormat.JPEG,0,bos);
            byte[] bitmapdata = bos.toByteArray();

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bitmapdata);
            fileOutputStream.flush();
            fileOutputStream.close();

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"),file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("uploads",file.getName(),reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"),"uploads");

            Call<ResponseBody> req = serviceApi.postImage(body,name);

            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.code() == 200){
                        Log.d("image","uploads Success");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Request failed", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {   
            e.printStackTrace();
        }

    }




}