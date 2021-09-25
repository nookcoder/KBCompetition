package com.kbc.Server;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface ServiceApi {
    @Multipart
    @POST("/product/img")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("uploads") RequestBody name);

    @POST("/pickUp/")
    Call<PickUpData> sendPickUpData(@Body PickUpData pickUpData);

    @GET("/personal/{userId}")
    Call<Personal> getPersonalData(@Path("userId") String userId);

    @GET("/pickUp/{userId}")
    Call<List<PickUpData>> getPickUpDate(@Path("userId") String userId);

    @POST("/pickUp/done/{userId}")
    Call<PickUpData> donePickUp(@Path("userId") String userId,@Body PickUpData pickUpData);

    @Multipart
    @POST("/product/register")
    Call<ProductData> sendProduct(@PartMap Map<String,RequestBody> map, @Part ArrayList<MultipartBody.Part> itempImg);

    @POST("/img/{merchantId}/{productName}")
    Call<String> postPickUpImageData(@Path("merchantId") String merchantId,@Path("productName") String productName);
}
