package com.kbc.Image;

import androidx.lifecycle.MutableLiveData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface ServiceApi {
    @Multipart
    @POST("/product/img")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("uploads") RequestBody name);

    @GET("/product/loadImg")
    @Streaming
    Call<ResponseBody> downloadImage(@Path("apiName") String apiName);

}