package com.kbc;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreManager_MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storemanager_activity_main);


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

                    break;

                case R.id.nav_information:

                    break;
            }

            return true;
        }

    }
}
