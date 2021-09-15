package com.kbc.Chatting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.FirebaseConnector;
import com.kbc.R;
import com.kbc.StoreManager_MainActivity;

import java.util.ArrayList;

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

        //RecycleView 연결
        recyclerView =rootViewGroup.findViewById(R.id.chatting_list_recycleView);
        linearLayoutManager = new LinearLayoutManager(storeManager_mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        //RecycleView에 사용 Adapter생성
        chatting_items = new ArrayList<>();
        //클릭 이벤트 연결
        chatting_list_recycleAdapter = new Chatting_List_RecycleAdapter(chatting_items, this);
        recyclerView.setAdapter(chatting_list_recycleAdapter);


        //아이디 + 데베 +채팅방 불러오기 싱글톤 객체 생성
        dbconnector = FirebaseConnector.getInstance(storeManager_mainActivity, "StoreManager",storeManager_id,
                Chatting.GET_CHATROOMS);

        return rootViewGroup;
    }

    @Override
    public void onItemClick(View view, int position) {
        Chatting_List_RecycleAdapter.ViewHolder viewHolder = (Chatting_List_RecycleAdapter.ViewHolder)recyclerView
                .findViewHolderForAdapterPosition(position);

        //선택한 채팅방 이름 (상대방)
        String click_chatting_list_name = viewHolder.chatting_list_name.getText().toString();
       Log.d("유저 이름 -> ",click_chatting_list_name);


        //채팅방들어가기
        Intent intent = new Intent(getActivity(), Chatting_Send_Activity.class );
        intent.putExtra("click_chatting_list_name",click_chatting_list_name);
        intent.putExtra("mode", Chatting.STORE_MANAGER_MODE);

        startActivity(intent);



    }
}



