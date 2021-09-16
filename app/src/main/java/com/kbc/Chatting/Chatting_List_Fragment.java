package com.kbc.Chatting;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kbc.FirebaseConnector;
import com.kbc.R;
import com.kbc.StoreManager_MainActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class Chatting_List_Fragment extends Fragment implements
Chatting_List_RecycleAdapter.OnItemClickEventListener{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Chatting_List_RecycleAdapter chatting_list_recycleAdapter;

    private StoreManager_MainActivity storeManager_mainActivity;


    private String login_id;
    private Bundle bundle;


    //데베 관련 변수
    private ArrayList<Chatting_Item> chatting_items = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String chat_mode;

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
            login_id = bundle.getString("id");
        }
        Log.d(TAG,"점주 아이디 : "+ login_id);

        //액티비티 가져오고,
        storeManager_mainActivity = (StoreManager_MainActivity)getActivity();
        FirebaseConnector();
        Get_Database();

        //RecycleView 연결
        recyclerView =rootViewGroup.findViewById(R.id.chatting_list_recycleView);
        linearLayoutManager = new LinearLayoutManager(storeManager_mainActivity);
        recyclerView.setLayoutManager(linearLayoutManager);

        //RecycleView에 사용 Adapter생성
        //클릭 이벤트 연결
        chatting_list_recycleAdapter = new Chatting_List_RecycleAdapter(chatting_items, this);
        recyclerView.setAdapter(chatting_list_recycleAdapter);

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

    public void FirebaseConnector(){
        //테스트 데이터
        chat_mode = Chatting.STORE_MANAGER_MODE;

        FirebaseApp.initializeApp(storeManager_mainActivity);
        firebaseDatabase = FirebaseDatabase.getInstance();

        switch (chat_mode){
            case "Person":
                databaseReference = firebaseDatabase.getReference("Person");
                break;

            case "StoreManager":
                databaseReference = firebaseDatabase.getReference("StoreManager");
                break;
        }
    }

    //전체 트리 가져오고, 모드 나눠서 세부 id로 들어가기
    private Map<String, Object> map;
    private Map<String, Object> id_map;
    private Map<String, Object> id_inside_map;

    private Map<String, Object> chatrooms_map;
    //해당 id의 채팅방 + 채팅 내용 불러오기!
    private ArrayList<HashMap<String, String>> chatrooms_arraylist, chatting_arraylist;
    private ArrayList<HashMap<String, String>> chatting_me_arraylist, chatting_other_arraylist;
    private Object[] chatting_me_last_message ,chatting_other_last_message;

    private HashMap<String, String> input_map;
    private Object[] chatting_map;

    private int name, profileUrl, message, time,date;
    private  int me_message_count = 1 , other_message_count = 1;

    private Chatting_Send_Activity chatting_send_activity = new Chatting_Send_Activity();
    private int chatrooms_count ;
    private Chatting_Item chatting_last_item = new Chatting_Item();


    public void Get_Database(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                map = (Map<String, Object>)dataSnapshot.getValue();

                if(map != null){
                    for(String id: map.keySet())
                        id_map = (Map<String, Object>)map.get(id);

                    int check_id_count = 0;
                    for(String id_inside : id_map.keySet()){

                        if(id_inside.equals(login_id)){
                            id_inside_map = (Map<String, Object>)id_map.get(id_inside);
                            break;
                        }
                        check_id_count++;
                    }

                    if(check_id_count == id_map.size()){
                        Initialize_Id();
                    }
                    else {
                        for(String in_inside_key : id_inside_map.keySet()){
                            switch (in_inside_key){
                                //아이디 -> 채팅 내역 리스트 담기
                                case "chatrooms":
                                    chatrooms_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);

                                    break;

                                case "chatting":
                                    chatting_arraylist = (ArrayList<HashMap<String, String>>)id_inside_map.get(in_inside_key);
                                    break;
                            }
                        }
                        Get_Last_Chatting_Information();
                    }
                }
                chatting_list_recycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //처음 채팅 아이디 넣기
    private void Initialize_Id(){

        databaseReference.child("id").child(login_id).child("chatrooms").child("0").setValue("채팅방없음");
        databaseReference.child("id").child(login_id).child("chatting").child("0").setValue("채팅방없음");

    }

    private void Get_Last_Chatting_Information(){
        //채팅방 개수
        chatrooms_count = chatting_arraylist.size()-1;

        input_map = chatting_arraylist.get(1);
        chatting_map = input_map.values().toArray();
        chatrooms_map = (Map<String, Object>) chatting_map[0];



        Log.d(TAG, "채팅 방 갯수 -> " +  chatting_arraylist.size());
        Log.d(TAG, "채팅 방 갯수 -> " +  chatting_arraylist.toString());
        Log.d(TAG, "채팅 값들 -> " + chatting_map[0]);

        chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
        chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");
        //키 인덱스 찾기
        Object[] send_key = chatting_me_arraylist.get(1).keySet().toArray();
        for (int key_index = 0; key_index < send_key.length; key_index++) {
            if (send_key[key_index].toString().equals(Chatting.NAME))
                name = key_index;

            if (send_key[key_index].toString().equals(Chatting.PROFILEUTL))
                profileUrl = key_index;

            if (send_key[key_index].toString().equals(Chatting.MESSAGE))
                message = key_index;

            if (send_key[key_index].toString().equals(Chatting.TIME))
                time = key_index;

            if (send_key[key_index].toString().equals(Chatting.DATE))
                date = key_index;
        }

        for(int position=0; position< chatrooms_count ; position++)
            Insert_Chatroom_Ui(position);

        for(int chatroom_number = 1 ; chatroom_number <= chatrooms_count ; chatroom_number++)
            Insert_Chatroom_DB(chatroom_number);
    }

    private void Insert_Chatroom_Ui(int position){ input_map = chatting_arraylist.get(1);
        chatting_map = input_map.values().toArray();

        chatrooms_map = (Map<String, Object>) chatting_map[position+1];


        chatting_me_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("me");
        chatting_other_arraylist = (ArrayList<HashMap<String, String>>) chatrooms_map.get("other");

        //마지막 채팅 정보 나, 상대방 각각 가져오기
        chatting_me_last_message = chatting_me_arraylist.get(chatting_me_arraylist.size()-1).values().toArray();
        chatting_other_last_message = chatting_other_arraylist.get(chatting_other_arraylist.size()-1).values().toArray();

        int last_message_mode = chatting_send_activity.Compare_Date(
                chatting_me_last_message[date].toString().split(" "), chatting_other_last_message[date].toString().split(" "),
                chatting_me_last_message[time].toString(), chatting_other_last_message[time].toString());

        chatting_last_item.setProfileUrl(chatting_other_last_message[profileUrl].toString());

        switch (last_message_mode){
            //내 시간이 더 빠르면 상대방이 마지막
            case Chatting.ME:
                chatting_last_item.setName(chatting_other_last_message[name].toString());
                chatting_last_item.setMessage(chatting_other_last_message[message].toString());
                chatting_last_item.setTime(chatting_other_last_message[time].toString());
                break;

            //상대방이 더빠르면 내가 마지막
            case Chatting.OTHER:
                chatting_last_item.setName(chatting_me_last_message[name].toString());
                chatting_last_item.setMessage(chatting_me_last_message[message].toString());
                chatting_last_item.setTime(chatting_me_last_message[time].toString());
                break;
        }

        chatting_list_recycleAdapter.addItem(chatting_last_item);
    }

    //데베에 넣기
    private void Insert_Chatroom_DB(int chatrooms_count){



    }
}



