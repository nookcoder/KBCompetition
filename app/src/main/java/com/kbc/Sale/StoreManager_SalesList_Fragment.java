package com.kbc.Sale;

import android.content.Context;
import android.content.Intent;
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
import com.kbc.Pickup.PickupAdapter;
import com.kbc.Pickup.Pickup_Item;
import com.kbc.R;
import com.kbc.Saled.SaledAdapter;
import com.kbc.Saled.Saled_Item;
import com.kbc.StoreManager_MainActivity;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    private String storeManager_id, storeManager_location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        // 여기도========================================================================================================================
        salesList.clear();
        try {
            getProductsDataFromServer(storeManager_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(saleAdapter);
        saleAdapter.notifyDataSetChanged();


        //판매중 버튼 눌렀을 때!
        salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 여기랑 ========================================================================================================================
                salesList.clear();
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
                saleAdapter.notifyDataSetChanged();
            }
        });

        //픽업 대기중 버튼 눌렀을 떄!
        pickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData2();
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
                prepareData3();
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

    private void setSalesList(JSONObject jsonObject){
        try {
            salesList.add(new Sale_Item("",jsonObject.getString("name"),jsonObject.getString("category"),jsonObject.getString("stock"),jsonObject.getString("price"),jsonObject.getString("dateYear"),jsonObject.getString("dateMonth"),jsonObject.getString("dateDay"),jsonObject.getString("dateType"),jsonObject.getString("origin"),jsonObject.getString("details"),jsonObject.getString("registerTime")));
            Log.d("salesList",jsonObject.getString("category"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData(JSONObject jsonObject) {
        salesList.clear();
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("products");
            int jsonArrayCount = jsonArray.length();
            for(int index=0; index<jsonArrayCount; index++){
                setSalesList(jsonArray.getJSONObject(index));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData2() {
        pickupList.clear();
        pickupList.add(new Pickup_Item("직거래좋아요","동글동글 방울토마토 100g","21/09/08","오후 6시30분",1));
        pickupList.add(new Pickup_Item("떠리처리","눈물 쏙 양파","21/09/08","오후 9시30분",3));
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData3() {
        saledList.clear();
        saledList.add(new Saled_Item("직거래좋아요","동글동글 방울토마토 100g","21/09/08","오후 6시30분",1));
        saledList.add(new Saled_Item("떠리처리","눈물 쏙 양파","21/09/08","오후 9시30분",3));
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
                myViewHolder.stock.getText().toString(),
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