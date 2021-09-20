package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
import com.kbc.Sale.SaleAdapter;
import com.kbc.Sale.Sale_Item;
import com.kbc.Sale.StoreManager_Product_Inquiry_Activity;
import com.kbc.Saled.SaledAdapter;
import com.kbc.Saled.Saled_Item;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Personal_Purchase_Fragment extends Fragment implements View.OnClickListener {

    private ArrayList<Purchase_Item> purchaseItemArrayList = new ArrayList<Purchase_Item>();
    private ArrayList<Pickup_Item> pickupList = new ArrayList<Pickup_Item>();
    private ArrayList<Saled_Item> saledList = new ArrayList<Saled_Item>();

    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private PickupAdapter pickupAdapter;
    private SaledAdapter saledAdapter;

    Button pickupBtn;
    Button saledBtn;
    Button purchaseBtn;
    TextView toolbarText;
    Spinner town1,town2,category;
    Button search;
    SearchView searchView;

    private Bundle bundle;

    private String storeManager_id, storeManager_location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.personal_purchase_fragment, container, false);

        //컴포넌트 할당
        //Text
        toolbarText = (TextView) v.findViewById(R.id.toolbarText);
        //button
        purchaseBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);
        search = (Button) v.findViewById(R.id.search);
        //스피너
        town1=(Spinner) v.findViewById(R.id.town1);
        town2=(Spinner) v.findViewById(R.id.town2);
        category=(Spinner) v.findViewById(R.id.category);
        //검색창
        searchView = (SearchView) v.findViewById(R.id.search_view);



        //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //어댑터 할당
        purchaseAdapter = new PurchaseAdapter(purchaseItemArrayList);
        pickupAdapter = new PickupAdapter(pickupList);
        saledAdapter = new SaledAdapter(saledList);


        //리사이클러뷰 설정
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(purchaseAdapter);
        purchaseAdapter.notifyDataSetChanged();


        //판매중 버튼 눌렀을 때!
        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData();
                recyclerView.setAdapter(purchaseAdapter);
                purchaseBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                town1.setVisibility(View.VISIBLE);
                town2.setVisibility(View.VISIBLE);
                category.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.VISIBLE);
                toolbarText.setText("장보기");
            }
        });

        //픽업 대기중 버튼 눌렀을 떄!
        pickupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData2();
                recyclerView.setAdapter(pickupAdapter);
                purchaseBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                town1.setVisibility(View.GONE);
                town2.setVisibility(View.GONE);
                category.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                toolbarText.setText("픽업대기중");
            }
        });
        //판매 완료 버튼 눌렀을 때!
        saledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareData3();
                recyclerView.setAdapter(saledAdapter);
                purchaseBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                town1.setVisibility(View.GONE);
                town2.setVisibility(View.GONE);
                category.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                toolbarText.setText("구매내역");
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        purchaseAdapter.notifyDataSetChanged();
    }



    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData() {
        purchaseItemArrayList.clear();
        purchaseItemArrayList.add(new Purchase_Item("엔샵상점","지옥의 코딩볶음면","가공식품",10,500));
        purchaseItemArrayList.add(new Purchase_Item("교촌치킨","생 닭다리","냉동식품",10,10000));
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
        purchaseBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);
    }

    //아이템 눌렀을때!
    /*@Override
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
*/

}