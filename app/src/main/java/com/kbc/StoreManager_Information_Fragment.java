package com.kbc;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class StoreManager_Information_Fragment extends Fragment implements View.OnClickListener{



    private StoreManager_MainActivity storeManager_mainActivity;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.storemanager_information, container, false);


        Button button = (Button)rootViewGroup.findViewById(R.id.logout);
        button.setOnClickListener(this);

        return rootViewGroup;
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.logout:
                Intent intent = new Intent((StoreManager_MainActivity)getActivity(), Popup_Activity.class );
                startActivity(intent);
                break;
        }
    }
}
