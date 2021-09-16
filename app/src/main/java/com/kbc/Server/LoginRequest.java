package com.kbc.Server;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final private static String URL = "http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/merchant/";
    private Map<String,String> map;

    public LoginRequest(String userId,String userName,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userId",userId);
        map.put("userName",userName);
    }

    protected Map<String,String> getParams(){return map;}
}
