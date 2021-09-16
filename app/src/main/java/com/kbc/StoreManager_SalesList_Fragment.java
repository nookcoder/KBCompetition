package com.kbc;

import android.os.Bundle;
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

import java.util.ArrayList;

public class StoreManager_SalesList_Fragment extends Fragment implements View.OnClickListener {

    private ArrayList<Sale_Item> salesList = new ArrayList<Sale_Item>();
    private ArrayList<Pickup_Item> favoritesList2 = new ArrayList<Pickup_Item>();
    private ArrayList<Saled_Item> favoritesList3 = new ArrayList<Saled_Item>();

    private RecyclerView recyclerView;
    private SaleAdapter saleAdapter;
    private PickupAdapter mAdapter2;
    private SaledAdapter mAdapter3;

    Button pickupBtn;
    Button saledBtn;
    Button salesBtn;

    TextView toolbarText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.storemanager_saleslist, container, false);
        prepareData();

        //Text
        toolbarText = (TextView) v.findViewById(R.id.toolbarText);
        //button
        salesBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);

        //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        saleAdapter = new SaleAdapter(salesList);
        mAdapter2 = new PickupAdapter(favoritesList2);
        mAdapter3 = new SaledAdapter(favoritesList3);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(saleAdapter);

        salesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData();
                recyclerView.setAdapter(saleAdapter);
                salesBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                toolbarText.setText("판매중");
            }
        });
        pickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData2();
                recyclerView.setAdapter(mAdapter2);
                salesBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                toolbarText.setText("픽업대기중");
            }
        });
        saledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData3();
                recyclerView.setAdapter(mAdapter3);
                salesBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                toolbarText.setText("판매완료");
            }
        });
        return v;
    }


    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData() {
        salesList.clear();
        salesList.add(new Sale_Item("동글동글 방울토마토","채소 / 과일",70,2000));
        salesList.add(new Sale_Item("신선한 상추","육류",30,1800));
        salesList.add(new Sale_Item("눈물 쏙 양파","채소 / 과일",10,4000));
        salesList.add(new Sale_Item("아삭아삭 콩나물","채소 / 과일",15,3300));
        salesList.add(new Sale_Item("을지로입구역","육류",12,8000));
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData2() {
        favoritesList2.clear();
        favoritesList2.add(new Pickup_Item("서울시청2",37.54892296550104,126.99089033876304));
        favoritesList2.add(new Pickup_Item("경복궁2",37.54892296550104,126.99089033876304));
        favoritesList2.add(new Pickup_Item("서울역2",37.54892296550104,126.99089033876304));
        favoritesList2.add(new Pickup_Item("남산2",37.54892296550104,126.99089033876304));
        favoritesList2.add(new Pickup_Item("을지로입구역2",37.54892296550104,126.99089033876304));
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData3() {
        favoritesList3.clear();
        favoritesList3.add(new Saled_Item("서울시청3",37.54892296550104,126.99089033876304));
        favoritesList3.add(new Saled_Item("경복궁3",37.54892296550104,126.99089033876304));
        favoritesList3.add(new Saled_Item("서울역3",37.54892296550104,126.99089033876304));
        favoritesList3.add(new Saled_Item("남산3",37.54892296550104,126.99089033876304));
        favoritesList3.add(new Saled_Item("을지로입구역3",37.54892296550104,126.99089033876304));
    }

    @Override
    public void onClick(View v) {
//button
        salesBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);

    }
}