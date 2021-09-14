package com.kbc.Chatting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kbc.FirebaseConnector;
import com.kbc.Popup_Activity;
import com.kbc.R;
import com.kbc.StoreManager_MainActivity;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Chatting_List_Fragment extends Fragment implements
Chatting_List_RecycleAdapter.OnItemClickEventListener{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Chatting_Item> chatting_items;
    private Chatting_List_RecycleAdapter chatting_list_recycleAdapter;
    private Chatting_Item chatting_item = new Chatting_Item();
    private Chatting_Item chatting_item2 = new Chatting_Item();


    private StoreManager_MainActivity storeManager_mainActivity;
    private FirebaseConnector dbconnector;

    private String storeManager_id;
    private Bundle bundle;

    //페이지 불러올떄마다 추가
    @Override
    public void onStart() {
        super.onStart();

        //테스트용
        chatting_item.setMessage("들어가쥬랑 ㅠㅡㅠ");
        chatting_item.setName("서히");
        chatting_item.setTime("01:12");



        chatting_items.add(chatting_item);

        chatting_item2.setMessage("제바류ㅠㅠㅠ");
        chatting_item2.setName("현욱");
        chatting_item2.setTime("05:16");

        chatting_items.add(chatting_item2);
        Log.d(TAG, "아이템 개수-- : "+  chatting_items.size());


        chatting_list_recycleAdapter.notifyDataSetChanged();
    }

    //뷰 그룹 반환 + 생성자 개념
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootViewGroup =(ViewGroup) inflater.inflate(R.layout.chatting_list, container, false);


        bundle = getArguments();
        if(bundle != null){
            storeManager_id = bundle.getString("id");
        }
        Log.d(TAG,"점주 아이디 : "+ storeManager_id);

        //액티비티 가져오고,
        storeManager_mainActivity = (StoreManager_MainActivity)getActivity();
        //아이디 + 데베 싱글톤 객체 생성
        dbconnector = FirebaseConnector.getInstance(storeManager_mainActivity, "StoreManager",storeManager_id);


        //RecycleView 연결
        recyclerView =rootViewGroup.findViewById(R.id.chatting_list_recycleView);
        linearLayoutManager = new LinearLayoutManager(storeManager_mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        //RecycleView에 사용 Adapter생성
        chatting_items = new ArrayList<>();
        //클릭 이벤트 연결
        chatting_list_recycleAdapter = new Chatting_List_RecycleAdapter(chatting_items, this);

        recyclerView.setAdapter(chatting_list_recycleAdapter);

        return rootViewGroup;
    }

    @Override
    public void onItemClick(View view, int position) {
        Chatting_List_RecycleAdapter.ViewHolder viewHolder = (Chatting_List_RecycleAdapter.ViewHolder)recyclerView
                .findViewHolderForAdapterPosition(position);

        String click_chatting_list_name = viewHolder.chatting_list_name.getText().toString();
       Log.d("유저 이름 -> ",click_chatting_list_name);

    }
}



