package com.kbc.Server;

import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitBulider {

    public RetrofitBulider(){

    }

    public ServiceApi initRetrofit(){
        ServiceApi serviceApi = new Retrofit.Builder().baseUrl("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceApi.class);


        return serviceApi;
    }

    public void loadImage(String merchantId,String productNameInPickupList, ImageView imageView){
        ServiceApi serviceApi = new Retrofit.Builder()
                .baseUrl("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(ServiceApi.class);

        Call<String> call = serviceApi.postPickUpImageData(merchantId,productNameInPickupList);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Picasso.get()
                        .load("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/img/"+merchantId+"/"+productNameInPickupList)
                        .into(imageView);
                Log.d("악","통신성공");
                Log.d("악","http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/img/"+merchantId+"/"+productNameInPickupList);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("악",t.getMessage());
            }
        });
    }
}


