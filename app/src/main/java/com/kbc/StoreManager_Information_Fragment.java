package com.kbc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

public class StoreManager_Information_Fragment extends Fragment {



    private StoreManager_MainActivity storeManager_mainActivity;

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        storeManager_mainActivity = (StoreManager_MainActivity) getActivity();
        if(storeManager_mainActivity != null){

        }
        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.storemanager_information, container, false);

        return rootViewGroup;
    }
}
