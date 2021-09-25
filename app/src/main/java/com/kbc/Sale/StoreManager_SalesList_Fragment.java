package com.kbc.Sale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kbc.Common.Creating;
import com.kbc.Personal_Purchase_Fragment;
import com.kbc.Pickup.Personal_Pickup_Item;
import com.kbc.Pickup.PickupAdapter;
import com.kbc.Pickup.Pickup_Item;
import com.kbc.R;
import com.kbc.Saled.SaledAdapter;
import com.kbc.Saled.Saled_Item;
import com.kbc.Server.PickUpData;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class StoreManager_SalesList_Fragment extends Fragment implements View.OnClickListener, SaleAdapter.OnItemClickEventListener {

    private ArrayList<Sale_Item> salesList = new ArrayList<Sale_Item>();
    private ArrayList<Pickup_Item> pickupList = new ArrayList<Pickup_Item>();
    private ArrayList<Saled_Item> saledList = new ArrayList<Saled_Item>();

    private RecyclerView recyclerView;
    private SaleAdapter saleAdapter;
    private PickupAdapter pickupAdapter;
    private SaledAdapter saledAdapter;

    Button pickupBtn;
    Button saledBtn;
    Button salesBtn;
    FloatingActionButton addProductBtn;
    TextView toolbarText;

    private Bundle bundle;
    private ServiceApi serviceApi;
    private String storeManager_id, storeManager_location;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceApi = new RetrofitBulider().initRetrofit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.storemanager_saleslist, container, false);

        bundle = getArguments();
        if(bundle != null){
            storeManager_id = bundle.getString("userID");
            storeManager_location = bundle.getString("location");

        }
        Log.d( "리스트 프래그먼트 아이디 ->", storeManager_id);

        //컴포넌트 할당
            //Text
        toolbarText = (TextView) v.findViewById(R.id.toolbarText);
            //button
        salesBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);
        addProductBtn =(FloatingActionButton)v.findViewById(R.id.addProductBtn);



        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StoreManager_MainActivity)getActivity()).Change_Activity(storeManager_id);
            }
        });
             //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //어댑터 할당
        saleAdapter = new SaleAdapter(salesList, this);
        pickupAdapter = new PickupAdapter(pickupList);
        saledAdapter = new SaledAdapter(saledList);


        //리사이클러뷰 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        try {
            getProductsDataFromServer(storeManager_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(saleAdapter);


        //판매중 버튼 눌렀을 때!
        salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setAdapter(saleAdapter);
                salesBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                toolbarText.setText("판매중");
                addProductBtn.setVisibility(View.VISIBLE);
                try {
                    getProductsDataFromServer(storeManager_id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        //픽업 대기중 버튼 눌렀을 떄!
        pickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData2(storeManager_id);
                recyclerView.setAdapter(pickupAdapter);
                salesBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                toolbarText.setText("픽업대기중");
                addProductBtn.setVisibility(View.INVISIBLE);
            }
        });
        //판매 완료 버튼 눌렀을 때!
        saledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData3(storeManager_id);
                recyclerView.setAdapter(saledAdapter);
                salesBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                toolbarText.setText("판매완료");
                addProductBtn.setVisibility(View.INVISIBLE);
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        saleAdapter.notifyDataSetChanged();
    }

    //판매중~
    private void setSalesList(JSONObject jsonObject){
        try {
            salesList.add(new Sale_Item("http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/img/request",jsonObject.getString("name"),jsonObject.getString("category"),jsonObject.getString("price"),jsonObject.getString("dateYear"),jsonObject.getString("dateMonth"),jsonObject.getString("dateDay"),jsonObject.getString("dateType"),jsonObject.getString("origin"),jsonObject.getString("details"),jsonObject.getString("registerTime")));
            Log.d("salesList",jsonObject.getString("category"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        saleAdapter.notifyDataSetChanged();
    }
    //서버 데이터 전달
    private void getProductsDataFromServer(String id) throws JSONException {
        String URL = "http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/merchant/"+id+"/products/";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id",id);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    salesList.clear();
                    Log.d("JSON", response.getJSONArray("products").toString());
                    JSONArray jsonArray = response.getJSONArray("products");
                    for(int index=0; index<jsonArray.length();index++)
                    {
                        setSalesList(jsonArray.getJSONObject(index));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },null);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    //픽업대기중
    private void prepareData2(String userId) {
        Call<List<PickUpData>> call = serviceApi.getPickUpDate(userId);
       new Insert_PickUp().execute(call);
    }
    private class Insert_PickUp extends AsyncTask<Call<List<PickUpData>>,Void, List<PickUpData>>{
        @Override
        protected List<PickUpData> doInBackground(Call<List<PickUpData>>... calls) {
            try{
                Call<List<PickUpData>>call = calls[0];
                return call.execute().body();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<PickUpData> pickUpDataList){
            pickupList.clear();
            for(int index=0; index < pickUpDataList.size(); index++){
                PickUpData pickUpData = pickUpDataList.get(index);
                if(pickUpData.getPickUp()==0){
                    pickupList.add(new Pickup_Item(pickUpData.getPersonalName(),pickUpData.getProductName(),new Creating().pickUpDate(pickUpData.getPickUpYear(),pickUpData.getPickUpMonth(),pickUpData.getPickUpDay()),new Creating().pickUpTime(pickUpData.getPickUpNoon(),pickUpData.getPickUpHour(),pickUpData.getPickUpMinute()),pickUpData.getPickregisterTime(),pickUpData.getMerchantId()));
                }
            }
            pickupAdapter.notifyDataSetChanged();

        }

    }

    //판매완료 함수
    private void prepareData3(String userId) {
        Call<List<PickUpData>> call = serviceApi.getPickUpDate(userId);
        new Insert_Saled().execute(call);
    }
    private class Insert_Saled extends AsyncTask<Call<List<PickUpData>>, Void, List<PickUpData>>{

        @Override
        protected List<PickUpData> doInBackground(Call<List<PickUpData>>... calls) {
            try{
                Call<List<PickUpData>> call = calls[0];
                return  call.execute().body();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<PickUpData> pickUpDataList){
            saledList.clear();
            for(int index=0; index < pickUpDataList.size(); index++){
                PickUpData pickUpData = pickUpDataList.get(index);
                if(pickUpData.getPickUp()==1){
                    saledList.add(new Saled_Item(pickUpData.getMerchantName(),pickUpData.getProductName(),new Creating().pickUpDate(pickUpData.getPickUpYear(),pickUpData.getPickUpMonth(),pickUpData.getPickUpDay()),new Creating().pickUpTime(pickUpData.getPickUpNoon(),pickUpData.getPickUpHour(),pickUpData.getPickUpMinute())));
                }
            }
            saledAdapter.notifyDataSetChanged();
        }
    }
    //버튼 할당
    @Override
    public void onClick(View v) {
        salesBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);
    }

    //아이템 눌렀을때!
    @Override
    public void onItemClick(View view, int position) {
        SaleAdapter.MyViewHolder myViewHolder = (SaleAdapter.MyViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
        ArrayList<Sale_Item> list = new ArrayList<Sale_Item>();
        Sale_Item sale_item = new Sale_Item(
                saleAdapter.getItem_productImageSrc(position),
                myViewHolder.name.getText().toString() ,
                myViewHolder.category.getText().toString(),
                myViewHolder.price.getText().toString(),
                saleAdapter.getItem_date_year(position),
                saleAdapter.getItem_date_month(position),
                saleAdapter.getItem_date_day(position),
                saleAdapter.getItem_date_type(position),
                saleAdapter.getItem_origin(position),
                saleAdapter.getItem_Details(position),
                saleAdapter.getItem_Register_Time(position));

        list.add(sale_item);


        //상품 조회 액티비티로 들어가기
        Intent intent = new Intent(getActivity(), StoreManager_Product_Inquiry_Activity.class);
        intent.putExtra("sale_item_list", list);
        intent.putExtra("userID", storeManager_id);
        intent.putExtra("location",storeManager_location);

        startActivity(intent);

    }


}