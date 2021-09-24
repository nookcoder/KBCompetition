package com.kbc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import androidx.appcompat.widget.SearchView;
import android.widget.Spinner;
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
import com.kbc.Common.Creating;
import com.kbc.Pickup.Personal_PickupAdapter;
import com.kbc.Pickup.Personal_Pickup_Item;
import com.kbc.Purchase.Personal_Purchase_Inquiry_Activity;
import com.kbc.Purchase.PurchaseAdapter;
import com.kbc.Sale.Sale_Item;
import com.kbc.Saled.SaledAdapter;
import com.kbc.Saled.Saled_Item;
import com.kbc.Server.Personal;
import com.kbc.Server.PickUpData;
import com.kbc.Server.RetrofitBulider;
import com.kbc.Server.ServiceApi;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.LongFunction;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;

public class Personal_Purchase_Fragment extends Fragment implements View.OnClickListener ,PurchaseAdapter.OnItemClickEventListener
{

    private ArrayList<Sale_Item> purchaseList = new ArrayList<Sale_Item>();
    private ArrayList<Personal_Pickup_Item> pickupList = new ArrayList<Personal_Pickup_Item>();
    private ArrayList<Saled_Item> saledList = new ArrayList<Saled_Item>();

    private ArrayAdapter<String> arrayAdapter;
    private ArrayAdapter category_adapter;
    private RecyclerView recyclerView;
    private PurchaseAdapter purchaseAdapter;
    private SaledAdapter saledAdapter;
    private Personal_PickupAdapter personalPickupAdapter;
    private int townPosition1,townPosition2;

    ServiceApi serviceApi;

    Button pickupBtn;
    Button saledBtn;
    Button purchaseBtn;
    TextView toolbarText;
    Spinner town1,town2,category;
    Button search;
    SearchView searchView;

    ArrayList<Integer> townPosition = new ArrayList<>();
    private Bundle bundle;

    private String personal_id, personal_town2,mode;
    static View v;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceApi = new RetrofitBulider().initRetrofit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.personal_purchase_fragment, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            personal_id = bundle.getString("userID");
            personal_town2 = bundle.getString("town2");
            mode = bundle.getString("mode");
        }
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
        purchaseAdapter = new PurchaseAdapter(purchaseList,this);
        personalPickupAdapter = new Personal_PickupAdapter(pickupList);
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


        Call<Personal> call = serviceApi.getPersonalData(personal_id);
        new Insert_Position().execute(call);
//        //설정
//        getPersonDate(personal_id);

//        town1.setSelection(townPosition1);
//        town2.setSelection(townPosition2);


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
                getProductFromServer("김포시");
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
                prepareData2(personal_id);
                recyclerView.setAdapter(personalPickupAdapter);
                purchaseBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                pickupBtn.setBackgroundResource(R.drawable.layout_selected_sale_button);
                saledBtn.setBackgroundResource(R.drawable.layout_unselected_sale_button);
                town1.setVisibility(View.GONE);
                town2.setVisibility(View.GONE);
                category.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                searchView.setVisibility(View.GONE);
                toolbarText.setText("픽업대기중");
                personalPickupAdapter.notifyDataSetChanged();
            }
        });
        //판매 완료 버튼 눌렀을 때!
        saledBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPersonDate(personal_id);
                prepareData3(personal_id);
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

    //장보기 물품 담아주는 함수 !!!!!!!!!
    private void setPurchaseList(JSONObject jsonObject){
        try {
            purchaseList.add(new Sale_Item("",jsonObject.getString("name"),jsonObject.getString("category"),jsonObject.getString("price"),jsonObject.getString("dateYear"),jsonObject.getString("dateMonth"),jsonObject.getString("dateDay"),jsonObject.getString("dateType"),jsonObject.getString("origin"),jsonObject.getString("details"),jsonObject.getString("registerTime"),personal_id,jsonObject.getString("userId"),jsonObject.getString("location")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        purchaseAdapter.notifyDataSetChanged();
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData2(String userId) {
        pickupList.clear();
        Call<List<PickUpData>> call = serviceApi.getPickUpDate(userId);
        call.enqueue(new Callback<List<PickUpData>>() {
            @Override
            public void onResponse(Call<List<PickUpData>> call, retrofit2.Response<List<PickUpData>> response) {
                List<PickUpData> pickUpDataList = response.body();
                for(int index=0; index < pickUpDataList.size(); index++){
                    PickUpData pickUpData = pickUpDataList.get(index);
                    if(pickUpData.getPickUp()==0){
                        pickupList.add(new Personal_Pickup_Item(pickUpData.getMerchantName(),pickUpData.getProductName(),new Creating().pickUpDate(pickUpData.getPickUpYear(),pickUpData.getPickUpMonth(),pickUpData.getPickUpDay()),new Creating().pickUpTime(pickUpData.getPickUpNoon(),pickUpData.getPickUpHour(),pickUpData.getPickUpMinute())));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PickUpData>> call, Throwable t) {
            }
        });
    }

    //데이터 준비(최종적으로는 동적으로 추가하거나 삭제할 수 있어야 한다. 이 데이터를 어디에 저장할지 정해야 한다.)
    private void prepareData3(String userId) {
        saledList.clear();
        Call<List<PickUpData>> call = serviceApi.getPickUpDate(userId);
        call.enqueue(new Callback<List<PickUpData>>() {
            @Override
            public void onResponse(Call<List<PickUpData>> call, retrofit2.Response<List<PickUpData>> response) {
                List<PickUpData> pickUpDataList = response.body();
                for(int index=0; index < pickUpDataList.size(); index++){
                    PickUpData pickUpData = pickUpDataList.get(index);
                    if(pickUpData.getPickUp()==1){
                        saledList.add(new Saled_Item(pickUpData.getMerchantName(),pickUpData.getProductName(),new Creating().pickUpDate(pickUpData.getPickUpYear(),pickUpData.getPickUpMonth(),pickUpData.getPickUpDay()),new Creating().pickUpTime(pickUpData.getPickUpNoon(),pickUpData.getPickUpHour(),pickUpData.getPickUpMinute())));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PickUpData>> call, Throwable t) {
            }
        });
    }

    //버튼 할당
    @Override
    public void onClick(@NotNull View v) {
        purchaseBtn = (Button) v.findViewById(R.id.button1);
        pickupBtn = (Button) v.findViewById(R.id.button2);
        saledBtn = (Button) v.findViewById(R.id.button3);
    }

    @Override
    public void onItemClick(View view, int position) {
        PurchaseAdapter.MyViewHolder myViewHolder =(PurchaseAdapter.MyViewHolder)recyclerView.findViewHolderForAdapterPosition(position);
        ArrayList<Sale_Item>sale_items = new ArrayList<>();
        purchaseAdapter.getPositionItem(position).setPersonal_Id(personal_id);
        sale_items.add(purchaseAdapter.getPositionItem(position));

        Intent intent = new Intent(getActivity(), Personal_Purchase_Inquiry_Activity.class);
        intent.putExtra("purchase_item_list", sale_items);
        intent.putExtra("userID",personal_id);
        intent.putExtra("town2",personal_town2);


        startActivity(intent);
    }
    ///스피너 형성
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

    private void getProductFromServer(String town){
        String URL = "http://ec2-52-79-237-141.ap-northeast-2.compute.amazonaws.com:3000/personal/product";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("town",town);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("products");
                    for(int index = 0 ; index<jsonArray.length(); index++)
                    {
                        setPurchaseList(jsonArray.getJSONObject(index));
                        Log.d("purchaseList",purchaseList.get(index).getName());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },null);

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    private void getPersonDate(String userId){
        Call<Personal> call = serviceApi.getPersonalData(userId);

        call.enqueue(new Callback<Personal>() {
            @Override
            public void onResponse(Call<Personal> call, retrofit2.Response<Personal> response) {
                Personal personalData = response.body();
                townPosition1 = personalData.getTownPosition1();
                townPosition2 = personalData.getTownPosition2();
                Log.d("개인 데이터", personalData.getTownPosition2() +"/"+ personalData.getNickName());
                Log.d("town", String.valueOf(townPosition1)+String.valueOf(townPosition2));
            }

            @Override
            public void onFailure(Call<Personal> call, Throwable t) {
                Log.d("town",t.getMessage());
            }
        });

    }

    private class Insert_Position extends AsyncTask<Call, Void, ArrayList<Integer>>{

        ArrayList<Integer> townPosition = new ArrayList<>();
        @Override
        protected ArrayList<Integer> doInBackground(Call... calls) {
            try{
                Call<Personal> call = calls[0];
                Personal personalData = call.execute().body();
                townPosition.add(personalData.getTownPosition1());
                townPosition.add(personalData.getTownPosition2());
                return  townPosition;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Integer> townPosition){
            town1 = (Spinner)v.findViewById(R.id.town1);
            town2 = (Spinner)v.findViewById(R.id.town2);

            town1.setSelection(townPosition.get(0));
            town2.setSelection(townPosition.get(1));
            Log.d("town-2", townPosition.get(0) + "/"+ townPosition.get(1)
            + "/ " + town1.getSelectedItemPosition() + "/" + town2.getSelectedItemPosition());
        }

    }


}



