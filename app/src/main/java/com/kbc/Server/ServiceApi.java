package com.kbc.Server;

import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.util.HashMap;
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

    @POST("/product/register")
    Call<ProductData> sendProductData(@Body ProductData productData);

    @POST("/pickUp/")
    Call<PickUpData> sendPickUpData(@Body PickUpData pickUpData);
}
