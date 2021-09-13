package com.kbc.Chatting;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kbc.FirebaseConnector;
import com.kbc.R;
import com.kbc.StoreManager_MainActivity;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class Chatting_List_Fragment extends Fragment {

    private StoreManager_MainActivity storeManager_mainActivity;
    private FirebaseConnector dbconnector;

    @Override
    public void onStart() {
        super.onStart();
    }

    //뷰 그룹 반환
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.chatting_list, container, false);

        //액티비티 가져오고,
        storeManager_mainActivity = (StoreManager_MainActivity)getActivity();
        //데베 싱글톤 객체 생성
        dbconnector = FirebaseConnector.getInstance(storeManager_mainActivity);

        dbconnector.Read_StoreManager();



        return rootViewGroup;
    }



}
