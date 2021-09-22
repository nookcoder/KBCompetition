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
import com.kbc.Purchase.Personal_Purchase_Fragment;

public class Personal_MainActivity extends AppCompatActivity {

    public static Personal_MainActivity personal_mainActivity;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTransaction fragmentTransaction;

    private Personal_Purchase_Fragment personal_purchase_fragment = new Personal_Purchase_Fragment();
    private Chatting_List_Fragment personal_chatting_fragment = new Chatting_List_Fragment();
    private Personal_Information_Fragment personal_information_fragment = new Personal_Information_Fragment();

    private String userId, mode, town2, storeManager_location;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_activity_main);

       //유저 데이터 받기
        Intent intentForGet = getIntent();
        userId = intentForGet.getExtras().getString("userID");
        town2 = intentForGet.getExtras().getString("town2");

        mode = Chatting.PERSONAL;

        //fragment로 데이터 전달
        bundle = new Bundle(3);
        bundle.putString("userID", userId);
        bundle.putString("mode", mode);
        bundle.putString("town2",town2);


        personal_purchase_fragment.setArguments(bundle);
        //   storeManager_chatting_fragment.setArguments(bundle);
        //   storeManager_information_fragment.setArguments(bundle);


        personal_mainActivity = Personal_MainActivity.this;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.commit();
        personal_purchase_fragment.setArguments(bundle);
        fragmentTransaction.add(R.id.fragment_container, personal_purchase_fragment);
        fragmentTransaction.addToBackStack(null);
        BottomNavigationView bottomNavigationView = findViewById(R.id.personal_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new bottomMenuSelectListener());

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, personal_purchase_fragment).commitAllowingStateLoss();

        personal_mainActivity = Personal_MainActivity.this;
    }

    class bottomMenuSelectListener implements BottomNavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.nav_sale:
                    fragmentTransaction.replace(R.id.fragment_container, personal_purchase_fragment).commit();
                    break;

                case R.id.nav_chatting:
                    //점주 아이디 번들 전달
                    fragmentTransaction.replace(R.id.fragment_container, personal_chatting_fragment).commit();
                    break;

                case R.id.nav_information:
                    fragmentTransaction.replace(R.id.fragment_container, personal_information_fragment).commit();
                    break;
            }
            return true;
        }
    }

   /* public void Change_Activity(String userId){
        Intent intent = new Intent(Personal_MainActivity.this, StoreManager_Product_Register_Activity.class);
        intent.putExtra("id", userId);
        startActivity(intent);

    }*/
}

