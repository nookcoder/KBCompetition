package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbc.Chatting.Chatting;
import com.kbc.Chatting.Chatting_List_Fragment;
import com.kbc.Sale.StoreManager_SalesList_Fragment;

public class StoreManager_MainActivity extends AppCompatActivity {

    public static StoreManager_MainActivity storeManager_mainActivity;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Chatting_List_Fragment storeManager_chatting_fragment = new Chatting_List_Fragment();
    private StoreManager_Information_Fragment storeManager_information_fragment = new StoreManager_Information_Fragment();
    private StoreManager_SalesList_Fragment storeManager_salesList_fragment =new StoreManager_SalesList_Fragment();
    private String storeManager_id, mode;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_activity_main);

        //유저 데이터 받기
        Intent intentForGet = getIntent();
        //String userId = intentForGet.getExtras().getString("userID");


        //서히 테스트용
        storeManager_id = "seohee";
        mode = Chatting.STORE_MANAGER_MODE;
        //fragment로 데이터 전달
        bundle = new Bundle(2);
        bundle.putString("id" , storeManager_id);
        bundle.putString("mode", mode);


        storeManager_mainActivity = StoreManager_MainActivity.this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();

        fragmentTransaction.addToBackStack(null);
        BottomNavigationView bottomNavigationView = findViewById(R.id.store_manger_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new bottomMenuSelectListener());

    }

    class bottomMenuSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener{

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()){
                case R.id.nav_sale:
                    fragmentTransaction.replace(R.id.fragment_container, storeManager_salesList_fragment).commit();
                    break;

                case R.id.nav_chatting:
                    //점주 아이디 번들 전달
                    storeManager_chatting_fragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.fragment_container, storeManager_chatting_fragment).commit();
                    break;

                case R.id.nav_information:
                    fragmentTransaction.replace(R.id.fragment_container, storeManager_information_fragment).commit();
                    break;
            }

            return true;
        }
    }
}
