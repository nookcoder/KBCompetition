package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class Personal_Purchase_Fragment extends Fragment implements View.OnClickListener, TextWatcher {

    private ArrayList<Purchase_Item> purchaseItemArrayList = new ArrayList<Purchase_Item>();
    private ArrayList<Pickup_Item> pickupList = new ArrayList<Pickup_Item>();
    private ArrayList<Saled_Item> saledList = new ArrayList<Saled_Item>();
    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter category_adapter;
    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private PickupAdapter pickupAdapter;
    private SaledAdapter saledAdapter;
    private int townPosition1,townPosition2;
    ArrayList<Purchase_Item> filteredList;


    EditText search;
    Button pickupBtn;
    Button saledBtn;
    Button purchaseBtn;
    TextView toolbarText;
    Spinner town1,town2,category;
    Button searchBtn;
    SearchView searchView;

    private Bundle bundle;

    private String storeManager_id, storeManager_location;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        filteredList=new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.personal_purchase_fragment, container, false);

        //컴포넌트 할당
        //Text
        search = (EditText) v.findViewById(R.id.search_view);
        toolbarText = (TextView) v.findViewById(R.id.toolbarText);
        //button
        purchaseBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);
        searchBtn = (Button) v.findViewById(R.id.search);
        //스피너
        town1=(Spinner) v.findViewById(R.id.town1);
        town2=(Spinner) v.findViewById(R.id.town2);
        category=(Spinner) v.findViewById(R.id.category);
        //recyclerview
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        //어댑터 할당
        purchaseAdapter = new PurchaseAdapter(purchaseItemArrayList);
        pickupAdapter = new PickupAdapter(pickupList);
        saledAdapter = new SaledAdapter(saledList);

        //스피너
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(R.array.spinner_region));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        town1.setAdapter(arrayAdapter);
        category_adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category, R.layout.spinner_item);
        Create_Spinner(category, category_adapter, R.layout.spinner_item);

        //카테고리 넣기
        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select_category = category_adapter.getItem(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        //스피너 초기 설정!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        initAddressSpinner();
        //임의로 설정
        townPosition1=3;townPosition2=0;
        //
        town1.setSelection(townPosition1);
        town2.setSelection(townPosition2);

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
                searchBtn.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
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
                searchBtn.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
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
                searchBtn.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
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
        purchaseItemArrayList.add(new Purchase_Item("엔샵상점","지옥의 코딩볶음면","가공식품",10));
        purchaseItemArrayList.add(new Purchase_Item("교촌치킨","생 닭다리","냉동식품",10));
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData2() {
        pickupList.clear();
        pickupList.add(new Pickup_Item("직거래좋아요","동글동글 방울토마토 100g","21/09/08","오후 6시30분"));
        pickupList.add(new Pickup_Item("떠리처리","눈물 쏙 양파","21/09/08","오후 9시30분"));
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData3() {
        saledList.clear();
        saledList.add(new Saled_Item("직거래좋아요","동글동글 방울토마토 100g","21/09/08","오후 6시30분"));
        saledList.add(new Saled_Item("떠리처리","눈물 쏙 양파","21/09/08","오후 9시30분"));
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
*///스피너 형성
    private void Create_Spinner(Spinner spinner, ArrayAdapter arrayAdapter,  int layout_type){

        arrayAdapter.setDropDownViewResource(layout_type);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

    }

    private void initAddressSpinner() {
        town1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 시군구, 동의 스피너를 초기화한다.
                switch (position) {
                    case 0:
                        town2.setAdapter(null);
                        break;
                    case 1:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_seoul);
                        break;
                    case 2:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_busan);
                        break;
                    case 3:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daegu);
                        break;
                    case 4:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_incheon);
                        break;
                    case 5:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gwangju);
                        break;
                    case 6:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_daejeon);
                        break;
                    case 7:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_ulsan);
                        break;
                    case 8:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_sejong);
                        break;
                    case 9:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeonggi);
                        break;
                    case 10:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gangwon);
                        break;
                    case 11:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_buk);
                        break;
                    case 12:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_chung_nam);

                        break;
                    case 13:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_buk);
                        break;
                    case 14:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeon_nam);
                        break;
                    case 15:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_buk);
                        break;
                    case 16:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_gyeong_nam);
                        break;
                    case 17:
                        setSigunguSpinnerAdapterItem(R.array.spinner_region_jeju);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setSigunguSpinnerAdapterItem(int array_resource) {
        if (arrayAdapter != null) {
            town2.setAdapter(null);
            arrayAdapter = null;
        }

        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, (String[]) getResources().getStringArray(array_resource));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        town2.setAdapter(arrayAdapter);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String searchText = search.getText().toString();
        searchFilter(searchText);
    }

    public void searchFilter(String searchText) {
        filteredList.clear();

        for (int i = 0; i < purchaseItemArrayList.size(); i++) {
            if (purchaseItemArrayList.get(i).getProductName().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(purchaseItemArrayList.get(i));
            }
        }

        //PurchaseAdapter.filterList(filteredList);
    }

}