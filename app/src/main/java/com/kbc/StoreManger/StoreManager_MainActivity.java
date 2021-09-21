package com.kbc.StoreManger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_List_Fragment;
import com.kbc.R;
import com.kbc.Sale.StoreManager_Product_Register_Activity;
import com.kbc.Sale.StoreManager_SalesList_Fragment;
import com.kbc.StoreManger.StoreManager_Information_Fragment;

public class StoreManager_MainActivity extends AppCompatActivity {

    public static StoreManager_MainActivity storeManager_mainActivity;
    public static String userId;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;
    private Chatting_List_Fragment storeManager_chatting_fragment = new Chatting_List_Fragment();
    private StoreManager_Information_Fragment storeManager_information_fragment = new StoreManager_Information_Fragment();
    private StoreManager_SalesList_Fragment storeManager_salesList_fragment =new StoreManager_SalesList_Fragment();
    private String  mode, storeManager_location;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_activity_main);

//        //유저 데이터 받기
//        Intent intentForGet = getIntent();
//        userId = intentForGet.getExtras().getString("userID");
//        Log.d("점주 메인 ", userId);

//
//
//        //서히 테스트용
//        storeManager_location = "광명동";
//        userId = "1915040308";

        mode = Chatting.STORE_MANAGER;
        //fragment로 데이터 전달
        bundle = new Bundle(3);
        bundle.putString("userID" , userId);
        bundle.putString("mode", mode);
        bundle.putString("location", storeManager_location);

        storeManager_salesList_fragment.setArguments(bundle);
        storeManager_chatting_fragment.setArguments(bundle);
        storeManager_information_fragment.setArguments(bundle);


        storeManager_mainActivity = StoreManager_MainActivity.this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
        storeManager_salesList_fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, storeManager_salesList_fragment);
        fragmentTransaction.addToBackStack(null);
        BottomNavigationView bottomNavigationView = findViewById(R.id.store_manger_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new bottomMenuSelectListener());

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, storeManager_salesList_fragment).commitAllowingStateLoss();

        storeManager_mainActivity = StoreManager_MainActivity.this;
    }

    class bottomMenuSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()){
                case R.id.nav_sale:

                    fragmentTransaction.replace(R.id.fragment_container, storeManager_salesList_fragment).commit();
                    break;

                case R.id.nav_chatting:
                    //점주 아이디 번들 전달
                    fragmentTransaction.replace(R.id.fragment_container, storeManager_chatting_fragment).commit();
                    break;

                case R.id.nav_information:
                    fragmentTransaction.replace(R.id.fragment_container, storeManager_information_fragment).commit();
                    break;
            }

            return true;
        }
    }

    public void Change_Activity(String userId){
        Intent intent = new Intent(StoreManager_MainActivity.this, StoreManager_Product_Register_Activity.class);
        intent.putExtra("userID", userId);
        startActivity(intent);

    }
}
