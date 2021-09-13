package com.kbc;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kbc.Chatting.Chatting_List_Fragment;

public class StoreManager_MainActivity extends AppCompatActivity {

    public static StoreManager_MainActivity storeManager_mainActivity;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Chatting_List_Fragment storeManager_chatting_fragment = new Chatting_List_Fragment();
    private StoreManager_Information_Fragment storeManager_information_fragment = new StoreManager_Information_Fragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_activity_main);

        //유저 데이터 받기
        Intent intentForGet = getIntent();
        String userId = intentForGet.getExtras().getString("userID");
        //테스트용
        TextView test = (TextView)findViewById(R.id.test);
        test.setText(userId);


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

                    break;

                case R.id.nav_chatting:
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
