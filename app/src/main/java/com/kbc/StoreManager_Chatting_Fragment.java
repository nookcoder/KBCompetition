package com.kbc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class StoreManager_Chatting_Fragment extends Fragment {

    private StoreManager_MainActivity storeManager_mainActivity;

    @Override
    public void onStart() {
        super.onStart();
    }

    //뷰 그룹 반환
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.storemanager_chatting, container, false);

        return rootViewGroup;
    }



}
