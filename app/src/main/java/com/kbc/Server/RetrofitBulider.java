package com.kbc.Server;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBulider {

    public RetrofitBulider(){

    }

    public ServiceApi initRetrofit(){
        ServiceApi serviceApi = new Retrofit.Builder().baseUrl("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceApi.class);


        return serviceApi;
    }

    public Call<PickUpData> getPickUpData(String userId){
        ServiceApi serviceApi = new Retrofit.Builder().baseUrl("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000").addConverterFactory(GsonConverterFactory.create()).build().create(ServiceApi.class);

        Call<PickUpData> call = serviceApi.getPickUpDate(userId);

        return call;
    }
}


