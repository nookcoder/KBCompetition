package com.kbc.StoreManger;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.Image.ImageAdapter;
import com.kbc.Image.Image_Item;
import com.kbc.Popup_TwoButton_Activity;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.util.ArrayList;
import com.kbc.R;

public class StoreManager_Information_Fragment extends Fragment implements View.OnClickListener{


    private Bundle bundle;
    private String storeManager_id, storeManager_location;

    private StoreManager_MainActivity storeManager_mainActivity;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.storemanager_information, container, false);

        bundle = getArguments();
        if(bundle != null){
            storeManager_id = bundle.getString("userID");
            storeManager_location = bundle.getString("location");

        }
        Log.d( "내정보 프래그먼트 아이디 ->",storeManager_id);

        Button logout_button = (Button)rootViewGroup.findViewById(R.id.logout);
        Button withdrawal_button = (Button)rootViewGroup.findViewById(R.id.withdrawal);
        withdrawal_button.setOnClickListener(this);
        logout_button.setOnClickListener(this);

        return rootViewGroup;
    }

    public void onClick(View view){
        Intent intent = new Intent((StoreManager_MainActivity)getActivity(), Popup_TwoButton_Activity.class );

        switch (view.getId()){
                //로그아웃
            case R.id.logout:
                intent.putExtra("button_name","logout");
                startActivity(intent);
                break;

                //탈퇴하기
            case R.id.withdrawal:
                intent.putExtra("button_name","withdrawal");
                startActivity(intent);
                break;

        }
    }
}
